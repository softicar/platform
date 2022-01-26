package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.uuid.UuidBytes;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionLog;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.time.Instant;
import java.util.UUID;
import org.junit.Test;

public class ProgramExecutionsDeleterTest extends AbstractCoreTest {

	private static final DayTime CURRENT_DAYTIME = DayTime.fromYMD_HMS(2022, 1, 24, 0, 0, 0);
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
	public void testDeleteWithProgramWithThreeRetentionDaysOfExecutions() {

		insertProgramWithThreeRetentionDaysOfExecutionsAndInsertItsExecutions();
		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithSeveralExecutionsOnEachDay() {

		AGProgram program = insertProgram(0);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		insertExecutionOfProgram(program, ExecutionDay.YESTERDAY);
		insertExecutionOfProgram(program, ExecutionDay.YESTERDAY);
		insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithNotTerminatedExecution() {

		AGProgram program = insertProgram(0);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertProgramExecutionWithLog(program, null));

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithSeveralProgramsAndExecutions() {

		insertProgramWithZeroRetentionDaysOfExecutionsAndInsertItsExecutions();
		insertProgramWithOneRetentionDaysOfExecutionsAndInsertItsExecutions();
		insertProgramWithTwoRetentionDaysOfExecutionsAndInsertItsExecutions();
		insertProgramWithThreeRetentionDaysOfExecutionsAndInsertItsExecutions();

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithExecutionTerminatedJustBeforeMidnight() {

		DayTime yesterdayJustBeforeMidnight = ExecutionDay.TODAY.dayTime.minusSeconds(1.0);

		AGProgram program = insertProgram(0);
		insertProgramExecutionWithLog(program, yesterdayJustBeforeMidnight);

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
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		insertExecutionOfProgram(program, ExecutionDay.YESTERDAY);
		insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);
	}

	private void insertProgramWithOneRetentionDaysOfExecutionsAndInsertItsExecutions() {
	
		AGProgram program = insertProgram(1);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.YESTERDAY));
		insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);
	}

	private void insertProgramWithTwoRetentionDaysOfExecutionsAndInsertItsExecutions() {
	
		AGProgram program = insertProgram(2);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.YESTERDAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO));
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);
	}

	private void insertProgramWithThreeRetentionDaysOfExecutionsAndInsertItsExecutions() {
	
		AGProgram program = insertProgram(3);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.YESTERDAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO));
	}

	private AGProgram insertProgram(int retentionDaysOfExecutions) {

		AGUuid uuid = insertRandomUuid();
		return new AGProgram()//
			.setProgramUuid(uuid)
			.setRetentionDaysOfExecutions(retentionDaysOfExecutions)
			.save();
	}

	private AGUuid insertRandomUuid() {

		UUID uuid = UUID.randomUUID();
		byte[] uuidBytes = UuidBytes.asBytes(uuid);
		return new AGUuid().setUuidString(uuid.toString()).setUuidBytes(uuidBytes).save();
	}

	private AGProgramExecution insertExecutionOfProgram(AGProgram program, ExecutionDay executionDay) {

		return insertProgramExecutionWithLog(program, executionDay.dayTime);
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

	private enum ExecutionDay {

		TODAY(CURRENT_DAYTIME.getDay()),
		YESTERDAY(CURRENT_DAYTIME.getDay().getRelative(-1)),
		TWO_DAYS_AGO(CURRENT_DAYTIME.getDay().getRelative(-2)),
		THREE_DAYS_AGO(CURRENT_DAYTIME.getDay().getRelative(-3)),;

		private DayTime dayTime;

		ExecutionDay(Day day) {

			this.dayTime = day.getBegin();
		}
	}
}
