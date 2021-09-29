package com.softicar.platform.emf.data.table.filter.list;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import java.util.Objects;

class OperatorButton extends DomButton implements IRefreshable {

	private final FilterElementListModel<?> model;

	public OperatorButton(FilterElementListModel<?> model, INullaryVoidFunction operatorToggler) {

		this.model = Objects.requireNonNull(model);
		Objects.requireNonNull(operatorToggler);

		setTitle(EmfDataTableI18n.CLICK_TO_TOGGLE_OPERATION);
		setClickCallback(() -> operatorToggler.apply());

		refresh();
	}

	@Override
	public void refresh() {

		setLabel(model.getOperator().toDisplay());
	}
}
