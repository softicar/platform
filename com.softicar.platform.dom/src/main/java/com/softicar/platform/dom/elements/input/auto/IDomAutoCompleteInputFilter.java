package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;

public interface IDomAutoCompleteInputFilter {

	boolean isActive();

	IDisplayString getFilterTitle();

	IDisplayString getValueTitle();

	void refresh();
}
