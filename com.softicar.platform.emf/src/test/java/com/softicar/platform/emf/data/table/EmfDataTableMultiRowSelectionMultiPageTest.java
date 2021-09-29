package com.softicar.platform.emf.data.table;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for row selection in multi-page data tables. Uses a non-database
 * back-end.
 * <p>
 * TODO: as soon as comparator based row comparison was introduced, add
 * respective tests here
 * <p>
 * TODO: test row selection for redundant rows
 * <p>
 * FIXME: The status div still bugs out when redundant rows are involved. The
 * same possibly goes for the logic part.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableMultiRowSelectionMultiPageTest extends AbstractEmfDataTableRowSelectionTest {

	private static final int PAGE_SIZE = 3;
	private final IEmfDataTableDiv<TestTableRow> dataTableDiv;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setStringValue("foo"),
			new TestTableRow().setStringValue("bar"),
			new TestTableRow().setStringValue("baz"),
			new TestTableRow().setStringValue(null),
			new TestTableRow().setStringValue("zzz"));

	public EmfDataTableMultiRowSelectionMultiPageTest() {

		dataTableDiv = setNode(createDataTableDivBuilder(() -> rows).setPageSize(PAGE_SIZE).setRowSelectionMode(EmfDataTableRowSelectionMode.MULTI).build());
	}

	@Test
	public void testWithSomeSelectedRowsOnCurrentPage() {

		clickRowSelectionCells(1, 2);

		assertSelectedRowsWithValues(dataTableDiv, "bar", "baz");
		assertStatusTextWithRowsOnCurrentPage(2);
	}

	@Test
	public void testWithSomeSelectedRowsOnOtherPage() {

		clickRowSelectionCells(1, 2);
		dataTableDiv.setCurrentPage(1);

		assertSelectedRowsWithValues(dataTableDiv, "bar", "baz");
		assertStatusTextWithRowsOnCurrentAndOtherPages(0, 2);
	}

	@Test
	public void testWithSomeSelectedRowsOnCurrentAndOtherPage() {

		clickRowSelectionCells(1, 2);
		dataTableDiv.setCurrentPage(1);
		clickRowSelectionCells(0);

		assertSelectedRowsWithValues(dataTableDiv, "bar", "baz", null);
		assertStatusTextWithRowsOnCurrentAndOtherPages(1, 3);
	}

	@Test
	public void testWithSelectAllOnCurrentPageButton() {

		dataTableDiv.setCurrentPage(1);
		clickSelectAllOnCurrentPageButton();

		assertSelectedRowsWithValues(dataTableDiv, null, "zzz");
		assertStatusTextWithRowsOnCurrentPage(2);
	}

	@Test
	public void testWithSelectInvertCurrentPageButton() {

		clickRowSelectionCells(0);
		dataTableDiv.setCurrentPage(1);
		clickRowSelectionCells(0);
		clickSelectInvertCurrentPageButton();

		assertSelectedRowsWithValues(dataTableDiv, "foo", "zzz");
		assertStatusTextWithRowsOnCurrentAndOtherPages(1, 2);
	}

	@Test
	public void testWithUnselectAllOnCurrentPageButton() {

		clickRowSelectionCells(0, 2);
		dataTableDiv.setCurrentPage(1);
		clickRowSelectionCells(0);
		clickUnselectAllOnCurrentPageButton();

		assertSelectedRowsWithValues(dataTableDiv, "foo", "baz");
		assertStatusTextWithRowsOnCurrentAndOtherPages(0, 2);
	}

	@Test
	public void testWithUnselectAllPagesButton() {

		clickRowSelectionCells(0);
		dataTableDiv.setCurrentPage(1);
		clickRowSelectionCells(0);
		clickUnselectAllPagesButton();

		assertNoRowsSelected(dataTableDiv);
		assertStatusTextWithRowsOnCurrentPage(0);
	}
}
