package com.softicar.platform.core.module.event.severity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;

public class AGSystemEventSeverity extends AGSystemEventSeverityGenerated implements IDisplayable {

	@Override
	public IDisplayString toDisplay() {

		return getEnum().toDisplay();
	}

	public boolean isError() {

		return getEnum() == AGSystemEventSeverityEnum.ERROR;
	}

	public boolean isWarning() {

		return getEnum() == AGSystemEventSeverityEnum.WARNING;
	}

	public boolean isInformation() {

		return getEnum() == AGSystemEventSeverityEnum.INFORMATION;
	}
}
