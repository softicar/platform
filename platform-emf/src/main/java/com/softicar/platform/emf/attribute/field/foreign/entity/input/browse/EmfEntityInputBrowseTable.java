package com.softicar.platform.emf.attribute.field.foreign.entity.input.browse;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.entity.IEmfEntity;

class EmfEntityInputBrowseTable<E extends IEmfEntity<E, ?>> extends AbstractInMemoryDataTable<E> {

	private final IDomAutoCompleteInputEngine<E> inputEngine;
	private final IDataTableColumn<E, String> nameColumn;

	public EmfEntityInputBrowseTable(IDomAutoCompleteInputEngine<E> inputEngine) {

		this.inputEngine = inputEngine;
		this.nameColumn = newColumn(String.class)//
			.setGetter(it -> it.toDisplayWithoutId().toString())
			.setTitle(EmfI18n.NAME)
			.addColumn();
	}

	public IDataTableColumn<E, String> getNameColumn() {

		return nameColumn;
	}

	@Override
	protected Iterable<E> getTableRows() {

		// FIXME We might need a method that is not limited
		return inputEngine.findMatches("", 5000);
	}
}
