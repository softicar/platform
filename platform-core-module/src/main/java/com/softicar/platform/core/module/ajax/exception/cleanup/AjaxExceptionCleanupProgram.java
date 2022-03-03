package com.softicar.platform.core.module.ajax.exception.cleanup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.exception.AGAjaxException;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Optional;

/**
 * TODO add javadoc
 * <p>
 * TODO PLAT-395 add to a standard configuration
 */
@EmfSourceCodeReferencePointUuid("f32a65ea-c410-47d2-b752-f7d5961eab40")
public class AjaxExceptionCleanupProgram implements IProgram {

	private static final int DAYS_TO_KEEP = 14;

	@Override
	public void executeProgram() {

		DayTime minDayTime = Day.today().getRelative(-DAYS_TO_KEEP).toDayTime();

		int remove = AGAjaxException.createSelect().where(AGAjaxException.EXCEPTION_DATE.less(minDayTime)).count();
		int keep = AGAjaxException.createSelect().where(AGAjaxException.EXCEPTION_DATE.greaterEqual(minDayTime)).count();

		Log.finfo("Will remove %d entries and keep %d entries\n", remove, keep);

		AGAjaxException.TABLE.createDelete().where(AGAjaxException.EXCEPTION_DATE.less(minDayTime)).execute();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional
			.of(//
				CoreI18n.DELETES_AJAX_EXCEPTIONS_OLDER_THAN_ARGS_DAYS.toDisplay(DAYS_TO_KEEP));
	}
}
