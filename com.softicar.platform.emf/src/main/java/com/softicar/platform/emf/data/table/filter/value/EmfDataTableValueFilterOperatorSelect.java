package com.softicar.platform.emf.data.table.filter.value;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;
import java.util.Optional;

public class EmfDataTableValueFilterOperatorSelect extends AbstractDomValueSelect<DataTableValueFilterOperator>
		implements IEmfDataTableFilterTypeSelect<DataTableValueFilterOperator>, IDomEventHandler {

	private final Optional<IRefreshable> refreshable;

	public EmfDataTableValueFilterOperatorSelect() {

		this(Optional.empty());
	}

	public EmfDataTableValueFilterOperatorSelect(IRefreshable refreshable) {

		this(Optional.of(refreshable));
	}

	public EmfDataTableValueFilterOperatorSelect(Optional<IRefreshable> refreshable) {

		this.refreshable = refreshable;

		addValues(DataTableValueFilterOperator.values());
		setSelectedValue(DataTableValueFilterOperator.EQUAL);

		listenToEvent(DomEventType.CHANGE);
	}

	@Override
	protected Integer getValueId(DataTableValueFilterOperator value) {

		return value.ordinal();
	}

	@Override
	protected IDisplayString getValueDisplayString(DataTableValueFilterOperator value) {

		return IDisplayString.create(value.getOperatorSymbol());
	}

	@Override
	public void handleDOMEvent(IDomEvent event) {

		refreshable.ifPresent(IRefreshable::refresh);
	}

	@Override
	public DataTableValueFilterOperator getSelectedType() {

		return getSelectedValue();
	}

	@Override
	public void setSelectedType(DataTableValueFilterOperator type) {

		setSelectedValue(type);
	}
}
