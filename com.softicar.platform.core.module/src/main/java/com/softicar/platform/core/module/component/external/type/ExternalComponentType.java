package com.softicar.platform.core.module.component.external.type;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;

/**
 * Enumerates all known types of external components.
 *
 * @author Alexander Schmidt
 */
public enum ExternalComponentType implements IDisplayable {

	FONT(CoreI18n.FONT),
	LIBRARY(CoreI18n.LIBRARY);

	private final IDisplayString displayString;

	private ExternalComponentType(IDisplayString displayString) {

		this.displayString = displayString;
	}

	@Override
	public IDisplayString toDisplay() {

		return displayString;
	}
}
