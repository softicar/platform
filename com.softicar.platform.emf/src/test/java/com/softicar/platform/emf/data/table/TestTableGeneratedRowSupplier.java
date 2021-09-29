package com.softicar.platform.emf.data.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class TestTableGeneratedRowSupplier implements Supplier<List<TestTableRow>> {

	private final List<TestTableRow> rows;

	public TestTableGeneratedRowSupplier(int numRows) {

		rows = new ArrayList<>();
		for (int i = 0; i < numRows; i++) {
			rows.add(new TestTableRow().setIntegerValue(i));
		}
	}

	@Override
	public List<TestTableRow> get() {

		return rows;
	}
}
