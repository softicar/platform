package com.softicar.platform.core.module.event.severity;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;

public class AGSystemEventSeverity extends AGSystemEventSeverityGenerated implements IDisplayable {

	@Override
	public IDisplayString toDisplay() {

		switch (getEnum()) {
		case ERROR:
			return CoreI18n.ERROR;
		case WARNING:
			return CoreI18n.WARNING;
		case INFORMATION:
			return CoreI18n.INFORMATION;
		}
		throw new SofticarUnknownEnumConstantException(getEnum());
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
