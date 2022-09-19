package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;

/**
 * Removes all sent e-mail from {@link AGBufferedEmail} that are older than a
 * specific age.
 *
 * @author Oliver Richers
 */
public class BufferedEmailCleaner {

	static final int MAXIMUM_DAYS_TO_KEEP = 3;
	private final DayTime minimumSentAt;

	public BufferedEmailCleaner() {

		this.minimumSentAt = Day.today().getRelative(-MAXIMUM_DAYS_TO_KEEP).toDayTime();
	}

	public void cleanAll() {

		AGBufferedEmail.TABLE//
			.createSelect()
			.where(AGBufferedEmail.SENT_AT.isNotNull())
			.where(AGBufferedEmail.SENT_AT.isLess(minimumSentAt))
			.orderBy(AGBufferedEmail.ID)
			.forEach(this::deleteWithLogs);
	}

	// TODO PLAT-1134 Rely on on-delete-cascade instead of explicitly deleting the log record.
	private AGBufferedEmail deleteWithLogs(AGBufferedEmail email) {

		deleteLogs(email);
		return email.delete();
	}

	private void deleteLogs(AGBufferedEmail email) {

		AGBufferedEmailLog.TABLE//
			.createDelete()
			.where(AGBufferedEmailLog.BUFFERED_EMAIL.isEqual(email))
			.execute();
	}
}
