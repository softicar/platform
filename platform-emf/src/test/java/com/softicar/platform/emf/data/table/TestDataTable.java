package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.ArrayList;
import java.util.Collection;

public class TestDataTable extends AbstractInMemoryDataTable<TestDataTableRow> {

	private final Collection<TestDataTableRow> rows;
	private final IDataTableColumn<TestDataTableRow, Integer> integerColumn;
	private final IDataTableColumn<TestDataTableRow, String> stringColumn;

	public TestDataTable() {

		this.rows = new ArrayList<>();

		this.integerColumn = newColumn(Integer.class)//
			.setGetter(TestDataTableRow::getInteger)
			.setTitle(IDisplayString.create("Integer"))
			.addColumn();

		this.stringColumn = newColumn(String.class)//
			.setGetter(TestDataTableRow::getString)
			.setTitle(IDisplayString.create("String"))
			.addColumn();
	}

	public IDataTableColumn<TestDataTableRow, Integer> getIntegerColumn() {

		return integerColumn;
	}

	public IDataTableColumn<TestDataTableRow, String> getStringColumn() {

		return stringColumn;
	}

	public TestDataTable addRow(TestDataTableRow row) {

		rows.add(row);
		return this;
	}

	@Override
	public Iterable<TestDataTableRow> getTableRows() {

		return rows;
	}
}
