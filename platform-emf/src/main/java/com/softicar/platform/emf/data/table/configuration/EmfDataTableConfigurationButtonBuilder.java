package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.tables.pageable.IDomPageableTableNavigationButtonBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableController;

public class EmfDataTableConfigurationButtonBuilder<R> implements IDomPageableTableNavigationButtonBuilder {

	private final IEmfDataTableController<R> controller;

	public EmfDataTableConfigurationButtonBuilder(IEmfDataTableController<R> controller) {

		this.controller = controller;
	}

	@Override
	public DomButton build() {

		return new EmfDataTableConfigurationButton<>(controller);
	}
}
