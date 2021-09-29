package com.softicar.platform.core.module.log.process;

import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.core.module.log.level.AGLogLevel;

public class AGLogProcess extends AGLogProcessGenerated {

	public void updateAndSaveWorstLogLevel(LogLevel logLevel) {

		AGLogLevel worstLogLevel = AGLogLevel.fromCommonLogLevel(logLevel);

		if (worstLogLevel.getEnum().ordinal() < getWorstLevel().getEnum().ordinal()) {
			setWorstLevel(worstLogLevel);
			save();
		}
	}
}
