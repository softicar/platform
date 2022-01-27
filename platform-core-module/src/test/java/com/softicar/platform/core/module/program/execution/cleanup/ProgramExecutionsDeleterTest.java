package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.uuid.UuidBytes;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionLog;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.time.Instant;
import java.util.UUID;
import org.junit.Test;

public class ProgramExecutionsDeleterTest extends AbstractCoreTest {

	private static final DayTime CURRENT_DAYTIME = Day.fromYMD(2022, 1, 24).getBegin();

	private static final DayTime TODAY_AT_MIDNIGHT = CURRENT_DAYTIME;
	private static final DayTime YESTERDAY_AT_MIDNIGHT = TODAY_AT_MIDNIGHT.getYesterday();
	private static final DayTime TWO_DAYS_AGO_AT_MIDNIGHT = YESTERDAY_AT_MIDNIGHT.getYesterday();

	private final SetMap<AGProgram, AGProgramExecution> expectedRetainedProgramExecutionsMap = new SetMap<>();

	public ProgramExecutionsDeleterTest() {

		// Suppress lower-level log output under test
		LogLevel.ERROR.set();

		// Use of special clock for JUnit tests because ProgramExecutionDeleter uses the current day to load the to be deleted program executions
		testClock.setInstant(Instant.ofEpochMilli(CURRENT_DAYTIME.toMillis()));
	}

	@Test
	public void testDeleteWithProgramWithZeroRetentionDaysOfExecutions() {

		insertProgramWithZeroRetentionDaysOfExecutionsAndInsertItsExecutions();
		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithProgramWithOneRetentionDaysOfExecutions() {

		insertProgramWithOneRetentionDaysOfExecutionsAndInsertItsExecutions();
		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithProgramWithTwoRetentionDaysOfExecutions() {

		insertProgramWithTwoRetentionDaysOfExecutionsAndInsertItsExecutions();
		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithSeveralExecutionsOnEachDay() {

		AGProgram program = insertProgram(0);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT));
		insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithNotTerminatedExecution() {

		AGProgram program = insertProgram(0);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, null));

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithSeveralProgramsAndExecutions() {

		insertProgramWithZeroRetentionDaysOfExecutionsAndInsertItsExecutions();
		insertProgramWithOneRetentionDaysOfExecutionsAndInsertItsExecutions();
		insertProgramWithTwoRetentionDaysOfExecutionsAndInsertItsExecutions();

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithExecutionAtTheLastSecondOfAday() {

		DayTime atTheLastSecondOfYesterday = TODAY_AT_MIDNIGHT.minusSeconds(1.0);

		AGProgram program = insertProgram(0);
		insertExecutionOfProgram(program, atTheLastSecondOfYesterday);

		runProgramExecutionsDeleterAndValidate();
	}

	private void runProgramExecutionsDeleterAndValidate() {

		new ProgramExecutionsDeleter(0).delete();
		assertEquals(expectedRetainedProgramExecutionsMap, loadProgramExecutions());
	}

	private SetMap<AGProgram, AGProgramExecution> loadProgramExecutions() {

		SetMap<AGProgram, AGProgramExecution> programExecutions = new SetMap<>();
		AGProgramExecution.createSelect().forEach(record -> programExecutions.addToSet(AGProgram.loadOrInsert(record.getProgramUuid()), record));
		return programExecutions;
	}

	private void insertProgramWithZeroRetentionDaysOfExecutionsAndInsertItsExecutions() {

		AGProgram program = insertProgram(0);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT));
		insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);
	}

	private void insertProgramWithOneRetentionDaysOfExecutionsAndInsertItsExecutions() {

		AGProgram program = insertProgram(1);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT));
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);
	}

	private void insertProgramWithTwoRetentionDaysOfExecutionsAndInsertItsExecutions() {

		AGProgram program = insertProgram(2);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT));
	}

	private AGProgram insertProgram(int retentionDaysOfExecutions) {

		Class<?> programClass = fetchProgramClass(retentionDaysOfExecutions);
		AGUuid uuid = insertUuid(programClass);

		return new AGProgram()//
			.setProgramUuid(uuid)
			.setRetentionDaysOfExecutions(retentionDaysOfExecutions)
			.save();
	}

	private Class<?> fetchProgramClass(int retentionDaysOfExecutions) {

		switch (retentionDaysOfExecutions) {
		case 0:
			return TestProgram1.class;
		case 1:
			return TestProgram2.class;
		case 2:
			return TestProgram3.class;
		default:
			throw new IllegalArgumentException("Unexpected value: " + retentionDaysOfExecutions);
		}
	}

	private AGUuid insertUuid(Class<?> referencePointClass) {

		UUID uuid = EmfSourceCodeReferencePoints.getUuidOrThrow(referencePointClass);
		byte[] uuidBytes = UuidBytes.asBytes(uuid);

		return new AGUuid()//
			.setUuidString(uuid.toString())
			.setUuidBytes(uuidBytes)
			.save();
	}

	private AGProgramExecution insertExecutionOfProgram(AGProgram program, DayTime executionDayTime) {

		return insertProgramExecutionWithLog(program, executionDayTime);
	}

	private AGProgramExecution insertProgramExecutionWithLog(AGProgram program, DayTime terminatedAt) {

		AGProgramExecution programExecution = new AGProgramExecution()//
			.setProgramUuid(program.getProgramUuid())
			.setTerminatedAt(terminatedAt)
			.setQueuedBy(CurrentUser.get())
			.save();
		insertLogRecord(programExecution);
		return programExecution;
	}

	private void insertLogRecord(AGProgramExecution programExecution) {

		AGTransaction transaction = new AGTransaction().setAt(DayTime.now()).setByToCurrentUser().save();
		AGProgramExecutionLog.TABLE.getOrCreate(new Tuple2<>(programExecution, transaction));
	}

	private static abstract class AbstractTestProgram implements IProgram {

		@Override
		public void executeProgram() {

			// Nothing to do
		}
	}

	@TestingOnly
	@EmfSourceCodeReferencePointUuid("c977d6ed-ad3d-4748-a7fc-1a64be5c4728")
	public static class TestProgram1 extends AbstractTestProgram {
		// Exists only to mock a real program with a sourceCodeReferencePoint
	}

	@TestingOnly
	@EmfSourceCodeReferencePointUuid("fc4033f8-3130-4542-bb03-0b3948c87ff1")
	public static class TestProgram2 extends AbstractTestProgram {
		// Exists only to mock a real program with a sourceCodeReferencePoint
	}

	@TestingOnly
	@EmfSourceCodeReferencePointUuid("73b18606-65b0-4cb0-8e7b-26b3530d403c")
	public static class TestProgram3 extends AbstractTestProgram {
		// Exists only to mock a real program with a sourceCodeReferencePoint
	}
}
