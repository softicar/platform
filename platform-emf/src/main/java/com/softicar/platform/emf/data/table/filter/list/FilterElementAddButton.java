package com.softicar.platform.emf.data.table.filter.list;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import java.util.Objects;

class FilterElementAddButton extends DomButton {

	public FilterElementAddButton(INullaryVoidFunction filterElementAdder) {

		Objects.requireNonNull(filterElementAdder);

		setIcon(DomImages.FILTER_ADD.getResource());
		setLabel(EmfDataTableI18n.ADD_FILTER);
		setClickCallback(() -> filterElementAdder.apply());
	}
}
