package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DateItemRange;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionLog;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.util.List;

/**
 * Deletes program execution records ({@link AGProgramExecution}) and their log
 * records records ({@link AGProgramExecutionLog}) if they are older than the
 * return value of related method
 * {@link com.softicar.platform.core.module.program.AGProgram#getRetentionDaysOfExecutions()}
 *
 * @author Thees Koester
 */
public class ProgramExecutionDeleter {

	private final int throttlingMilliseconds;
	private final Day referenceDay;

	public ProgramExecutionDeleter(int throttlingMilliseconds) {

		this.throttlingMilliseconds = throttlingMilliseconds;
		this.referenceDay = Day.today();
	}

	public void delete() {

		Log.finfo("%s started for %s", ProgramExecutionDeleter.class.getSimpleName(), referenceDay);

		for (AGProgram program: AGProgram.TABLE.createSelect().orderBy(AGProgram.ID)) {
			deleteExecutionsOfProgram(program);
		}
	}

	private void deleteExecutionsOfProgram(AGProgram program) {

		Log.finfo("Cleanup executions of program %s", fetchProgramNameIfPossible(program));

		AGProgramExecution
			.createSelect()
			.where(AGProgramExecution.PROGRAM_UUID.equal(program.getProgramUuid()))
			.where(AGProgramExecution.TERMINATED_AT.isNotNull())
			.orderBy(AGProgramExecution.TERMINATED_AT)
			.getFirstAsOptional()
			.map(AGProgramExecution::getTerminatedAt)
			.map(DayTime::getDay)
			.ifPresentOrElse(minimalDayInDb -> deleteBatchwise(program, minimalDayInDb), this::logInfoNothingToDelete);
	}

	private String fetchProgramNameIfPossible(AGProgram program) {

		try {
			// Will fail in JUnit tests
			return program.toDisplayWithoutId() + "";
		} catch (Exception exception) {
			DevNull.swallow(exception);
		}
		return program + "";
	}

	private void deleteBatchwise(AGProgram program, Day minimalDayInDb) {

		Day minimalRetainedDay = referenceDay.getRelative(-program.getRetentionDaysOfExecutions());

		if (minimalDayInDb.isBefore(minimalRetainedDay)) {
			int distance = minimalDayInDb.getDistance(minimalRetainedDay);
			Day firstThresholdDay = minimalDayInDb.getNext();
			Log.finfo("Deleting records in %s day-based batches, from %s to %s...", distance, firstThresholdDay, minimalRetainedDay);

			for (Day thresholdDay: new DateItemRange<>(firstThresholdDay, minimalRetainedDay)) {
				deleteExecutionsOlderThan(program, thresholdDay);
				sleepIfNecessary(thresholdDay, minimalRetainedDay);
			}
		} else {
			logInfoNothingToDelete();
		}
	}

	private void deleteExecutionsOlderThan(AGProgram program, Day thresholdDay) {

		List<AGProgramExecution> programExecutions = loadToBeDeletedProgramExecutions(program, thresholdDay);
		Log.finfo("Deleting %d program execution(s) older than %s...", programExecutions.size(), thresholdDay);
		try (DbTransaction transaction = new DbTransaction()) {
			deleteProgramExecutionLogsAndProgramExecutions(programExecutions);
			transaction.commit();
		}
	}

	private List<AGProgramExecution> loadToBeDeletedProgramExecutions(AGProgram program, Day thresholdDay) {

		return AGProgramExecution.TABLE
			.createSelect()
			.where(AGProgramExecution.PROGRAM_UUID.equal(program.getProgramUuid()))
			.where(AGProgramExecution.TERMINATED_AT.isNotNull())
			.where(AGProgramExecution.TERMINATED_AT.less(thresholdDay.toDayTime()))
			.list();
	}

	private void deleteProgramExecutionLogsAndProgramExecutions(List<AGProgramExecution> programExecutions) {

		AGProgramExecutionLog.TABLE.createDelete().where(AGProgramExecutionLog.PROGRAM_EXECUTION.in(programExecutions)).execute();
		AGProgramExecution.TABLE.createDelete().where(AGProgramExecution.ID.isIn(programExecutions)).execute();
	}

	private void sleepIfNecessary(Day thresholdDay, Day minimalRetainedDay) {

		if (thresholdDay.isBefore(minimalRetainedDay)) {
			Sleep.sleep(throttlingMilliseconds);
		}
	}

	private void logInfoNothingToDelete() {

		Log.finfo("Nothing to delete.");
	}
}
