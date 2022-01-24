package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.uuid.UuidBytes;
import com.softicar.platform.common.date.DateItemRange;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;

public class ProgramExecutionDeleterTest extends AbstractCoreTest {

	private static final DayTime CURRENT_DAYTIME = DayTime.fromYMD_HMS(2022, 1, 24, 0, 0, 0);
	private final Day referenceDay = CURRENT_DAYTIME.getDay();
	private final SetMap<AGProgram, AGProgramExecution> expectedRetainedProgramExecutionsMap = new SetMap<>();

	public ProgramExecutionDeleterTest() {

		// Suppress lower-level log output under test
		LogLevel.ERROR.set();

		// Use of special clock for JUnit tests because ProgramExecutionDeleter uses the current day to load the to be deleted program executions
		testClock.setInstant(Instant.ofEpochMilli(CURRENT_DAYTIME.toMillis()));

		for (AGProgram program: insertPrograms()) {
			insertExecutionsOfProgram(program);
		}
	}

	private void insertExecutionsOfProgram(AGProgram program) {

		for (Day terminatedAtDay: new DateItemRange<>(referenceDay.getRelative(-4), referenceDay)) {
			AGProgramExecution execution = insertProgramExecutionWithLog(program, terminatedAtDay);
			putExecutionOnExpectedRetainedProgramExecutionsMapIfNecessary(program, terminatedAtDay, execution);
		}
	}

	private void putExecutionOnExpectedRetainedProgramExecutionsMapIfNecessary(AGProgram program, Day terminatedAtDay, AGProgramExecution execution) {

		Day minimalRetainedDay = referenceDay.getRelative(-program.getRetentionDaysOfExecutions());
		if (terminatedAtDay.compareTo(minimalRetainedDay) >= 0) {
			expectedRetainedProgramExecutionsMap.addToSet(program, execution);
		}
	}

	private List<AGProgram> insertPrograms() {

		List<AGProgram> programs = new ArrayList<>();
		for (int retentionDaysOfExecutions = 0; retentionDaysOfExecutions < 3; retentionDaysOfExecutions++) {
			programs.add(insertProgram(retentionDaysOfExecutions));
		}
		return programs;
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

	private AGProgramExecution insertProgramExecutionWithLog(AGProgram program, Day terminatedAtDay) {

		AGProgramExecution programExecution = new AGProgramExecution()//
			.setProgramUuid(program.getProgramUuid())
			.setTerminatedAt(DayTime.fromDayAndSeconds(terminatedAtDay, 0))
			.setQueuedBy(CurrentUser.get())
			.save();
		insertLogRecord(programExecution);
		return programExecution;
	}

	private void insertLogRecord(AGProgramExecution programExecution) {

		AGTransaction transaction = new AGTransaction().setAt(DayTime.now()).setByToCurrentUser().save();
		AGProgramExecutionLog.TABLE.getOrCreate(new Tuple2<>(programExecution, transaction));
	}

	@Test
	public void testDelete() {

		new ProgramExecutionDeleter(0).delete();
		assertEquals(expectedRetainedProgramExecutionsMap, loadProgramExecutions());
	}

	private SetMap<AGProgram, AGProgramExecution> loadProgramExecutions() {

		SetMap<AGProgram, AGProgramExecution> programExecutions = new SetMap<>();
		AGProgramExecution.createSelect().forEach(record -> programExecutions.addToSet(AGProgram.loadOrInsert(record.getProgramUuid()), record));
		return programExecutions;
	}
}
