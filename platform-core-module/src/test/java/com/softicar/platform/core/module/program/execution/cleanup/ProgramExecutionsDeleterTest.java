package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.logging.LogLevel;
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
import com.softicar.platform.db.runtime.utils.DbAssertUtils;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.Test;

public class ProgramExecutionsDeleterTest extends AbstractCoreTest {

	private static final DayTime CURRENT_DAYTIME = Day.fromYMD(2022, 1, 24).getBegin();

	private static final DayTime TODAY_AT_MIDNIGHT = CURRENT_DAYTIME;
	private static final DayTime YESTERDAY_AT_MIDNIGHT = TODAY_AT_MIDNIGHT.getYesterday();
	private static final DayTime TWO_DAYS_AGO_AT_MIDNIGHT = YESTERDAY_AT_MIDNIGHT.getYesterday();

	public ProgramExecutionsDeleterTest() {

		// Suppress lower-level log output under test
		LogLevel.ERROR.set();

		// Use of special clock for JUnit tests because ProgramExecutionDeleter uses the current day to load the to be deleted program executions
		testClock.setInstant(Instant.ofEpochMilli(CURRENT_DAYTIME.toMillis()));
	}

	@Test
	public void testDeleteWithProgramWithZeroExecutionRetentionDays() {

		AGProgram program = insertProgram(0);
		AGProgramExecution executionToBeRetained = insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);

		DbAssertUtils.assertCount(3, AGProgramExecution.TABLE);

		new ProgramExecutionsDeleter(0).delete();

		AGProgramExecution loadedExecution = DbAssertUtils.assertOne(AGProgramExecution.TABLE);
		assertSame(executionToBeRetained, loadedExecution);
	}

	@Test
	public void testDeleteWithProgramWithOneExecutionRetentionDays() {

		AGProgram program = insertProgram(1);
		AGProgramExecution executionToBeRetained1 = insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT);
		AGProgramExecution executionToBeRetained2 = insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);

		DbAssertUtils.assertCount(3, AGProgramExecution.TABLE);

		new ProgramExecutionsDeleter(0).delete();

		List<AGProgramExecution> loadedExecutions = DbAssertUtils.assertCount(2, AGProgramExecution.TABLE);
		assertTrue(loadedExecutions.contains(executionToBeRetained1));
		assertTrue(loadedExecutions.contains(executionToBeRetained2));
	}

	@Test
	public void testDeleteWithProgramWithTwoExecutionRetentionDays() {

		AGProgram program = insertProgram(2);
		AGProgramExecution executionToBeRetained1 = insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT);
		AGProgramExecution executionToBeRetained2 = insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		AGProgramExecution executionToBeRetained3 = insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);

		DbAssertUtils.assertCount(3, AGProgramExecution.TABLE);

		new ProgramExecutionsDeleter(0).delete();

		List<AGProgramExecution> loadedExecutions = DbAssertUtils.assertCount(3, AGProgramExecution.TABLE);
		assertTrue(loadedExecutions.contains(executionToBeRetained1));
		assertTrue(loadedExecutions.contains(executionToBeRetained2));
		assertTrue(loadedExecutions.contains(executionToBeRetained3));
	}

	@Test
	public void testDeleteWithSeveralExecutionsOnEachDay() {

		AGProgram program = insertProgram(0);
		AGProgramExecution executionToBeRetained1 = insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT);
		AGProgramExecution executionToBeRetained2 = insertExecutionOfProgram(program, TODAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);
		insertExecutionOfProgram(program, TWO_DAYS_AGO_AT_MIDNIGHT);

		DbAssertUtils.assertCount(6, AGProgramExecution.TABLE);

		new ProgramExecutionsDeleter(0).delete();

		List<AGProgramExecution> loadedExecutions = DbAssertUtils.assertCount(2, AGProgramExecution.TABLE);
		assertTrue(loadedExecutions.contains(executionToBeRetained1));
		assertTrue(loadedExecutions.contains(executionToBeRetained2));
	}

	@Test
	public void testDeleteWithNotTerminatedExecution() {

		AGProgram program = insertProgram(0);
		AGProgramExecution notTerminatedExecution = insertExecutionOfProgram(program, null);

		DbAssertUtils.assertOne(AGProgramExecution.TABLE);

		new ProgramExecutionsDeleter(0).delete();

		AGProgramExecution loadedExecution = DbAssertUtils.assertOne(AGProgramExecution.TABLE);
		assertSame(notTerminatedExecution, loadedExecution);
	}

	@Test
	public void testDeleteWithSeveralProgramsAndExecutions() {

		AGProgram program0 = insertProgram(0);
		AGProgramExecution executionToBeRetained01 = insertExecutionOfProgram(program0, TODAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program0, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program0, TWO_DAYS_AGO_AT_MIDNIGHT);

		AGProgram program1 = insertProgram(1);
		AGProgramExecution executionToBeRetained11 = insertExecutionOfProgram(program1, TODAY_AT_MIDNIGHT);
		AGProgramExecution executionToBeRetained12 = insertExecutionOfProgram(program1, YESTERDAY_AT_MIDNIGHT);
		insertExecutionOfProgram(program1, TWO_DAYS_AGO_AT_MIDNIGHT);

		AGProgram program2 = insertProgram(2);
		AGProgramExecution executionToBeRetained21 = insertExecutionOfProgram(program2, TODAY_AT_MIDNIGHT);
		AGProgramExecution executionToBeRetained22 = insertExecutionOfProgram(program2, YESTERDAY_AT_MIDNIGHT);
		AGProgramExecution executionToBeRetained23 = insertExecutionOfProgram(program2, TWO_DAYS_AGO_AT_MIDNIGHT);

		DbAssertUtils.assertCount(9, AGProgramExecution.TABLE);

		new ProgramExecutionsDeleter(0).delete();

		List<AGProgramExecution> loadedExecutions = DbAssertUtils.assertCount(6, AGProgramExecution.TABLE);
		assertTrue(loadedExecutions.contains(executionToBeRetained01));
		assertTrue(loadedExecutions.contains(executionToBeRetained11));
		assertTrue(loadedExecutions.contains(executionToBeRetained12));
		assertTrue(loadedExecutions.contains(executionToBeRetained21));
		assertTrue(loadedExecutions.contains(executionToBeRetained22));
		assertTrue(loadedExecutions.contains(executionToBeRetained23));
	}

	@Test
	public void testDeleteWithExecutionAtTheLastSecondOfAday() {

		DayTime atTheLastSecondOfYesterday = TODAY_AT_MIDNIGHT.minusSeconds(1.0);

		AGProgram program = insertProgram(0);
		insertExecutionOfProgram(program, atTheLastSecondOfYesterday);

		DbAssertUtils.assertOne(AGProgramExecution.TABLE);

		new ProgramExecutionsDeleter(0).delete();

		DbAssertUtils.assertNone(AGProgramExecution.TABLE);
	}

	private AGProgram insertProgram(int executionRetentionDays) {

		Class<?> programClass = fetchProgramClass(executionRetentionDays);
		AGUuid uuid = insertUuid(programClass);

		return new AGProgram()//
			.setProgramUuid(uuid)
			.setExecutionRetentionDays(executionRetentionDays)
			.save();
	}

	private Class<?> fetchProgramClass(int executionRetentionDays) {

		switch (executionRetentionDays) {
		case 0:
			return TestProgram1.class;
		case 1:
			return TestProgram2.class;
		case 2:
			return TestProgram3.class;
		default:
			throw new IllegalArgumentException("Unexpected value: " + executionRetentionDays);
		}
	}

	private AGUuid insertUuid(Class<?> referencePointClass) {

		UUID uuid = SourceCodeReferencePoints.getUuidOrThrow(referencePointClass);

		return new AGUuid()//
			.setUuidString(uuid.toString())
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
	@SourceCodeReferencePointUuid("c977d6ed-ad3d-4748-a7fc-1a64be5c4728")
	public static class TestProgram1 extends AbstractTestProgram {
		// Exists only to mock a real program with a sourceCodeReferencePoint
	}

	@TestingOnly
	@SourceCodeReferencePointUuid("fc4033f8-3130-4542-bb03-0b3948c87ff1")
	public static class TestProgram2 extends AbstractTestProgram {
		// Exists only to mock a real program with a sourceCodeReferencePoint
	}

	@TestingOnly
	@SourceCodeReferencePointUuid("73b18606-65b0-4cb0-8e7b-26b3530d403c")
	public static class TestProgram3 extends AbstractTestProgram {
		// Exists only to mock a real program with a sourceCodeReferencePoint
	}
}
