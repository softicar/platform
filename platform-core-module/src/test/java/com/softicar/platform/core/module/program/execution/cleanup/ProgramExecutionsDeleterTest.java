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
	public void testDeleteWithProgramWithRetentionDaysOfExecutionsOfZero() {

		AGProgram program = insertProgram(0);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		insertExecutionOfProgram(program, ExecutionDay.YESTERDAY);
		insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithProgramWithRetentionDaysOfExecutionsOfOne() {

		AGProgram program = insertProgram(1);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.YESTERDAY));
		insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithProgramWithRetentionDaysOfExecutionsOfTwo() {

		AGProgram program = insertProgram(2);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.YESTERDAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO));
		insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO);

		runProgramExecutionsDeleterAndValidate();
	}

	@Test
	public void testDeleteWithProgramWithRetentionDaysOfExecutionsOfThree() {

		AGProgram program = insertProgram(3);
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.YESTERDAY));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.TWO_DAYS_AGO));
		expectedRetainedProgramExecutionsMap.addToSet(program, insertExecutionOfProgram(program, ExecutionDay.THREE_DAYS_AGO));

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

		AGProgram program0 = insertProgram(0);
		expectedRetainedProgramExecutionsMap.addToSet(program0, insertExecutionOfProgram(program0, ExecutionDay.TODAY));
		insertExecutionOfProgram(program0, ExecutionDay.YESTERDAY);
		insertExecutionOfProgram(program0, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program0, ExecutionDay.THREE_DAYS_AGO);

		AGProgram program1 = insertProgram(1);
		expectedRetainedProgramExecutionsMap.addToSet(program1, insertExecutionOfProgram(program1, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program1, insertExecutionOfProgram(program1, ExecutionDay.YESTERDAY));
		insertExecutionOfProgram(program1, ExecutionDay.TWO_DAYS_AGO);
		insertExecutionOfProgram(program1, ExecutionDay.THREE_DAYS_AGO);

		AGProgram program2 = insertProgram(2);
		expectedRetainedProgramExecutionsMap.addToSet(program2, insertExecutionOfProgram(program2, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program2, insertExecutionOfProgram(program2, ExecutionDay.YESTERDAY));
		expectedRetainedProgramExecutionsMap.addToSet(program2, insertExecutionOfProgram(program2, ExecutionDay.TWO_DAYS_AGO));
		insertExecutionOfProgram(program2, ExecutionDay.THREE_DAYS_AGO);

		AGProgram program3 = insertProgram(3);
		expectedRetainedProgramExecutionsMap.addToSet(program3, insertExecutionOfProgram(program3, ExecutionDay.TODAY));
		expectedRetainedProgramExecutionsMap.addToSet(program3, insertExecutionOfProgram(program3, ExecutionDay.YESTERDAY));
		expectedRetainedProgramExecutionsMap.addToSet(program3, insertExecutionOfProgram(program3, ExecutionDay.TWO_DAYS_AGO));
		expectedRetainedProgramExecutionsMap.addToSet(program3, insertExecutionOfProgram(program3, ExecutionDay.THREE_DAYS_AGO));

		runProgramExecutionsDeleterAndValidate();
	}

	private void runProgramExecutionsDeleterAndValidate() {

		new ProgramExecutionsDeleter(0).delete();
		assertEquals(expectedRetainedProgramExecutionsMap, loadProgramExecutions());
	}

	@Test
	public void testDeleteWithExecutionTerminatedJustBeforeMidnight() {

		DayTime yesterdayJustBeforeMidnight = ExecutionDay.TODAY.dayTime.minusSeconds(1.0);

		AGProgram program = insertProgram(0);
		insertProgramExecutionWithLog(program, yesterdayJustBeforeMidnight);

		runProgramExecutionsDeleterAndValidate();
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

	private SetMap<AGProgram, AGProgramExecution> loadProgramExecutions() {

		SetMap<AGProgram, AGProgramExecution> programExecutions = new SetMap<>();
		AGProgramExecution.createSelect().forEach(record -> programExecutions.addToSet(AGProgram.loadOrInsert(record.getProgramUuid()), record));
		return programExecutions;
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
