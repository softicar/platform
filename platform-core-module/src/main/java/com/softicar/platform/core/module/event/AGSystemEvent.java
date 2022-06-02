package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGSystemEvent extends AGSystemEventGenerated implements IEmfObject<AGSystemEvent> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.format("%s: %s", getSeverity().toDisplay(), getMessage());
	}
}
