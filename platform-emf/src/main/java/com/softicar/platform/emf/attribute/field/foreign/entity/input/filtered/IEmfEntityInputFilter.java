package com.softicar.platform.emf.attribute.field.foreign.entity.input.filtered;

import com.softicar.platform.common.core.i18n.IDisplayString;

public interface IEmfEntityInputFilter {

	boolean isActive();

	IDisplayString getTitle();

	IDisplayString getValue();
}
