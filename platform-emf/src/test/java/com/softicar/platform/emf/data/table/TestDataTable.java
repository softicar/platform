package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
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

	@Override
	public DataTableIdentifier getIdentifier() {

		return new DataTableIdentifier("484f32ad-3733-4033-8a0d-b740bb196429");
	}

	@Override
	protected Iterable<TestDataTableRow> getTableRows() {

		return rows;
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
}
