package com.softicar.platform.core.module.event.severity;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.style.ICssClass;

public class SystemEventSeverityDisplay extends DomDiv {

	public SystemEventSeverityDisplay(AGSystemEventSeverity severity) {

		addCssClass(CoreCssClasses.SYSTEM_EVENT_SEVERITY_DISPLAY);
		addCssClass(getCssPseudoClass(severity));

		appendText(severity.toDisplay());
	}

	private ICssClass getCssPseudoClass(AGSystemEventSeverity severity) {

		var severityEnum = severity.getEnum();
		switch (severityEnum) {
		case ERROR:
			return DomCssPseudoClasses.ERROR;
		case WARNING:
			return DomCssPseudoClasses.WARNING;
		case INFORMATION:
			return DomCssPseudoClasses.INFO;
		}
		throw new SofticarUnknownEnumConstantException(severityEnum);
	}
}
