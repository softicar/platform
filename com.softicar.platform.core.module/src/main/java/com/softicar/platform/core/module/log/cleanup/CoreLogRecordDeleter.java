package com.softicar.platform.core.module.log.cleanup;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.date.DateItemRange;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.sql.Sql;

public class CoreLogRecordDeleter {

	private final Day minimalRetainedDay;
	private int throttlingMilliseconds;

	public CoreLogRecordDeleter(Day minimalRetainedDay) {

		this.minimalRetainedDay = minimalRetainedDay;
		this.throttlingMilliseconds = 0;
	}

	public CoreLogRecordDeleter setThrottlingMilliseconds(int throttlingMilliseconds) {

		this.throttlingMilliseconds = throttlingMilliseconds;
		return this;
	}

	public <R> CoreLogRecordDeleter delete(IDbDayTimeField<R> dayTimeField) {

		Sql//
			.from(dayTimeField.getTable())
			.select(dayTimeField)
			.orderBy(dayTimeField)
			.getFirstAsOptional()
			.map(DayTime::getDay)
			.ifPresent(minimalDayInDb -> deleteOldRecords(dayTimeField, minimalDayInDb));
		return this;
	}

	private <R> void deleteOldRecords(IDbDayTimeField<R> dayTimeField, Day minimalDayInDb) {

		if (minimalDayInDb.isBefore(minimalRetainedDay)) {
			int distance = minimalDayInDb.getDistance(minimalRetainedDay);
			Log
				.finfo(

					"Deleting %s records in %s day-based batches, from %s to %s...",
					dayTimeField.getTable().getFullName(),
					distance,
					minimalDayInDb,
					minimalRetainedDay);

			for (Day thresholdDay: new DateItemRange<>(minimalDayInDb.getNext(), minimalRetainedDay)) {
				Log.finfo("Deleting %s records older than %s...", dayTimeField.getTable().getFullName(), thresholdDay);

				executeDelete(dayTimeField, thresholdDay);
				sleepIfNecessary(thresholdDay);
			}
		} else {
			Log.finfo("No %s records to delete.", dayTimeField.getTable().getFullName());
		}
	}

	private <R> void executeDelete(IDbDayTimeField<R> dayTimeField, Day thresholdDay) {

		dayTimeField//
			.getTable()
			.createDelete()
			.where(dayTimeField.less(thresholdDay.toDayTime()))
			.execute();
	}

	private void sleepIfNecessary(Day thresholdDay) {

		if (thresholdDay.isBefore(minimalRetainedDay)) {
			Sleep.sleep(throttlingMilliseconds);
		}
	}
}
