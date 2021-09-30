package com.softicar.platform.core.module.log.level;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.logging.LogLevel;

public class AGLogLevel extends AGLogLevelGenerated {

	public static AGLogLevel fromCommonLogLevel(LogLevel logLevel) {

		switch (logLevel) {
		case PANIC:
			return AGLogLevelEnum.PANIC.getRecord();
		case ERROR:
			return AGLogLevelEnum.ERROR.getRecord();
		case WARNING:
			return AGLogLevelEnum.WARNING.getRecord();
		case INFO:
			return AGLogLevelEnum.INFO.getRecord();
		case VERBOSE:
			return AGLogLevelEnum.VERBOSE.getRecord();
		}
		throw new SofticarUnknownEnumConstantException(logLevel);
	}
}
