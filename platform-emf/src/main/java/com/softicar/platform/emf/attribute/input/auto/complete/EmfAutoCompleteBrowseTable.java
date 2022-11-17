package com.softicar.platform.emf.attribute.input.auto.complete;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.input.auto.matching.DomAutoCompleteMatch;
import com.softicar.platform.emf.EmfI18n;
import java.util.Objects;
import java.util.stream.Collectors;

class EmfAutoCompleteBrowseTable<T> extends AbstractInMemoryDataTable<T> {

	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final IDataTableColumn<T, String> nameColumn;

	public EmfAutoCompleteBrowseTable(IDomAutoCompleteInputEngine<T> inputEngine) {

		this.inputEngine = Objects.requireNonNull(inputEngine);
		this.nameColumn = newColumn(String.class)//
			.setGetter(entity -> inputEngine.getDisplayString(entity).toString())
			.setTitle(EmfI18n.ENTRY)
			.addColumn();
	}

	public IDataTableColumn<T, String> getNameColumn() {

		return nameColumn;
	}

	@Override
	protected Iterable<T> getTableRows() {

		return inputEngine//
			.findMatches("", Integer.MAX_VALUE)
			.getAll()
			.stream()
			.map(DomAutoCompleteMatch::getValue)
			.collect(Collectors.toList());
	}
}
