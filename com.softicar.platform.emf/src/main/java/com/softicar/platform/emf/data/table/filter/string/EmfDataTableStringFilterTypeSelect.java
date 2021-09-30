package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;

class EmfDataTableStringFilterTypeSelect extends AbstractDomValueSelect<EmfDataTableStringFilterType>
		implements IEmfDataTableFilterTypeSelect<EmfDataTableStringFilterType>, IDomEventHandler {

	private final IRefreshable refreshable;

	public EmfDataTableStringFilterTypeSelect(IRefreshable refreshable) {

		this.refreshable = refreshable;

		addValues(EmfDataTableStringFilterType.values());
		setSelectedValue(EmfDataTableStringFilterType.CONTAINS_WORDS);

		listenToEvent(DomEventType.CHANGE);
	}

	@Override
	public void handleDOMEvent(IDomEvent event) {

		refreshable.refresh();
	}

	@Override
	protected Integer getValueId(EmfDataTableStringFilterType value) {

		return value.ordinal();
	}

	@Override
	protected IDisplayString getValueDisplayString(EmfDataTableStringFilterType value) {

		return value.toDisplay();
	}

	@Override
	public EmfDataTableStringFilterType getSelectedType() {

		return getSelectedValue();
	}

	@Override
	public void setSelectedType(EmfDataTableStringFilterType type) {

		setSelectedValue(type);
	}
}
