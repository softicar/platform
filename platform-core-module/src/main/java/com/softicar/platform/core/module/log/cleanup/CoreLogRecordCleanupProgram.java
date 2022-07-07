package com.softicar.platform.core.module.log.cleanup;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.file.stored.AGStoredFileLog;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
import com.softicar.platform.core.module.user.login.failure.AGUserLoginFailureLog;
import java.util.Optional;

/**
 * TODO add javadoc
 */
@SourceCodeReferencePointUuid("1b4ca844-39b6-41a1-9afa-0863c92d1e94")
public class CoreLogRecordCleanupProgram implements IProgram {

	private static final int LOG_RETENTION_DAYS = 180;
	private static final int THROTTLING_MILLISECONDS = 3000;

	@Override
	public void executeProgram() {

		Day minimalRetainedDay = Day.today().getRelative(-LOG_RETENTION_DAYS);

		new CoreLogRecordDeleter(minimalRetainedDay)//
			.setThrottlingMilliseconds(THROTTLING_MILLISECONDS)
			.delete(AGAjaxSession.ACCESS_DATE)
			.delete(AGLogProcess.START_TIME)
			.delete(AGUserLoginFailureLog.LOGIN_AT)
			.delete(AGUserLoginLog.LOGIN_AT)
			.delete(AGStoredFileLog.LOGGED_AT);
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.DELETES_RECORDS_OF_CORE_LOG_TABLES_OLDER_THAN_ARG1_DAYS.toDisplay(LOG_RETENTION_DAYS));
	}
}
