package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;

class EmfDataTableEntityFilterTypeSelect extends AbstractDomValueSelect<EmfDataTableEntityFilterType>
		implements IEmfDataTableFilterTypeSelect<EmfDataTableEntityFilterType>, IDomEventHandler {

	private final IRefreshable refreshable;

	public EmfDataTableEntityFilterTypeSelect(IRefreshable refreshable) {

		this.refreshable = refreshable;

		addValues(EmfDataTableEntityFilterType.values());
		setSelectedValue(EmfDataTableEntityFilterType.CONTAINS_TEXT);

		listenToEvent(DomEventType.CHANGE);
	}

	@Override
	public void handleDOMEvent(IDomEvent event) {

		refreshable.refresh();
	}

	@Override
	public EmfDataTableEntityFilterType getSelectedType() {

		return getSelectedValue();
	}

	@Override
	public void setSelectedType(EmfDataTableEntityFilterType type) {

		setSelectedValue(type);
	}

	@Override
	protected Integer getValueId(EmfDataTableEntityFilterType value) {

		return value.ordinal();
	}

	@Override
	protected IDisplayString getValueDisplayString(EmfDataTableEntityFilterType value) {

		return value.toDisplay();
	}
}
