package com.softicar.platform.core.module.log.message;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.core.module.log.level.AGLogLevel;
import com.softicar.platform.emf.object.IEmfObject;

public class AGLogMessage extends AGLogMessageGenerated implements IEmfObject<AGLogMessage> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getLogText() + " " + getLevel() + " " + getLogTime());
	}

	public void setLogLevel(LogLevel logLevel) {

		setLevel(AGLogLevel.fromCommonLogLevel(logLevel));
	}
}
