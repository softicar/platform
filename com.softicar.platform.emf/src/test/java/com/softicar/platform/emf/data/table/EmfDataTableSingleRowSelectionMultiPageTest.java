package com.softicar.platform.emf.data.table;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class EmfDataTableSingleRowSelectionMultiPageTest extends AbstractEmfDataTableRowSelectionTest {

	private static final int PAGE_SIZE = 3;
	private final IEmfDataTableDiv<TestTableRow> dataTableDiv;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setStringValue("foo"),
			new TestTableRow().setStringValue("bar"),
			new TestTableRow().setStringValue("baz"),
			new TestTableRow().setStringValue(null),
			new TestTableRow().setStringValue("zzz"));

	public EmfDataTableSingleRowSelectionMultiPageTest() {

		dataTableDiv =
				setNode(createDataTableDivBuilder(() -> rows).setPageSize(PAGE_SIZE).setRowSelectionMode(EmfDataTableRowSelectionMode.SINGLE).build());
	}

	@Test
	public void testWithSelectedRowOnCurrentPage() {

		clickRowSelectionCells(1);

		assertSelectedRowsWithValues(dataTableDiv, "bar");
		assertStatusTextWithRowsOnCurrentPage(1);
	}

	@Test
	public void testWithSelectedRowOnOtherPage() {

		clickRowSelectionCells(1);
		dataTableDiv.setCurrentPage(1);

		assertSelectedRowsWithValues(dataTableDiv, "bar");
		assertStatusTextWithRowsOnCurrentAndOtherPages(0, 1);
	}

	@Test
	public void testWithSelectedRowOnCurrentPageAfterPagingAndClickOnRow() {

		clickRowSelectionCells(1);
		dataTableDiv.setCurrentPage(1);
		clickRowSelectionCells(1);

		assertSelectedRowsWithValues(dataTableDiv, "zzz");
		assertStatusTextWithRowsOnCurrentPage(1);
	}

	@Test
	public void testWithUnselectedRowOnCurrentPageAfterPagingAndTwoClicksOnRow() {

		clickRowSelectionCells(1);
		dataTableDiv.setCurrentPage(1);
		clickRowSelectionCells(1, 1);

		assertNoRowsSelected(dataTableDiv);
		assertStatusTextWithRowsOnCurrentPage(0);
	}
}
