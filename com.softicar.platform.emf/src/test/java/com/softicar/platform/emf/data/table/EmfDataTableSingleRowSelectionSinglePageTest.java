package com.softicar.platform.emf.data.table;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class EmfDataTableSingleRowSelectionSinglePageTest extends AbstractEmfDataTableRowSelectionTest {

	private final IEmfDataTableDiv<TestTableRow> dataTableDiv;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setStringValue("foo"),
			new TestTableRow().setStringValue("bar"),
			new TestTableRow().setStringValue("baz"),
			new TestTableRow().setStringValue(null),
			new TestTableRow().setStringValue("zzz"));

	public EmfDataTableSingleRowSelectionSinglePageTest() {

		dataTableDiv = setNode(createDataTableDivBuilder(() -> rows).setRowSelectionMode(EmfDataTableRowSelectionMode.SINGLE).build());
	}

	@Test
	public void testWithInitialization() {

		assertNumberOfTableBodyRows(rows.size());
		assertNoRowsSelected(dataTableDiv);
		assertStatusTextWithRowsOnCurrentPage(0);
	}

	@Test
	public void testWithSelectedRow() {

		clickRowSelectionCells(4);

		assertSelectedRowsWithValues(dataTableDiv, "zzz");
		assertStatusTextWithRowsOnCurrentPage(1);
	}

	@Test
	public void testWithUnselectedRowAfterTwoClicks() {

		clickRowSelectionCells(4, 4);

		assertNoRowsSelected(dataTableDiv);
		assertStatusTextWithRowsOnCurrentPage(0);
	}

	@Test
	public void testWithSelectedRowAfterClicksOnSeveralRows() {

		clickRowSelectionCells(3, 4, 0);

		assertSelectedRowsWithValues(dataTableDiv, "foo");
		assertStatusTextWithRowsOnCurrentPage(1);
	}

	@Test
	public void testWithUnselectedRowAfterClicksOnSeveralRows() {

		clickRowSelectionCells(3, 4, 0, 0);

		assertNoRowsSelected(dataTableDiv);
		assertStatusTextWithRowsOnCurrentPage(0);
	}
}
