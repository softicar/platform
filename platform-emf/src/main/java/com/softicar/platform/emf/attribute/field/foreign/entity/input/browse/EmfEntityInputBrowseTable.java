package com.softicar.platform.emf.attribute.field.foreign.entity.input.browse;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Objects;

class EmfEntityInputBrowseTable<E extends IEmfEntity<E, ?>> extends AbstractInMemoryDataTable<E> {

	private final IDomAutoCompleteInputEngine<E> inputEngine;
	private final IDataTableColumn<E, String> nameColumn;

	public EmfEntityInputBrowseTable(IDomAutoCompleteInputEngine<E> inputEngine) {

		this.inputEngine = Objects.requireNonNull(inputEngine);
		this.nameColumn = newColumn(String.class)//
			.setGetter(entity -> entity.toDisplay().toString())
			.setTitle(EmfI18n.ENTRY)
			.addColumn();
	}

	public IDataTableColumn<E, String> getNameColumn() {

		return nameColumn;
	}

	@Override
	protected Iterable<E> getTableRows() {

		return inputEngine.findMatches("", Integer.MAX_VALUE);
	}
}
