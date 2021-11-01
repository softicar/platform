package com.softicar.platform.emf.management;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

public class EmfManagementButton<R extends IEmfTableRow<R, P>, P, S extends IEmfTableRow<?, ?>> extends DomPopupButton {

	private final IEmfTable<R, P, S> entityTable;
	private final S scopeEntity;
	private Optional<IRefreshable> refreshable;

	public EmfManagementButton(IEmfTable<R, P, S> entityTable, S scopeEntity) {

		this.entityTable = entityTable;
		this.scopeEntity = scopeEntity;
		this.refreshable = Optional.empty();

		setIcon(entityTable.getIcon());
		setTitle(EmfI18n.MANAGE_ARG1.toDisplay(entityTable.getPluralTitle()));
		setPopupFactory(this::createPopup);
	}

	public EmfManagementButton<R, P, S> setRefreshable(IRefreshable refreshable) {

		this.refreshable = Optional.of(refreshable);
		return this;
	}

	private DomPopup createPopup() {

		EmfManagementPopup<R, P, S> popup = new EmfManagementPopup<>(entityTable, scopeEntity);
		refreshable.ifPresent(popup::setRefreshable);
		return popup;
	}
}
