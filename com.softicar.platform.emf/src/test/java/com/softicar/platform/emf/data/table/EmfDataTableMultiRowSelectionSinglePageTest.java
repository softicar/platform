package com.softicar.platform.emf.data.table;

import com.softicar.platform.emf.data.table.row.selection.EmfTableRowSelectionMarker;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for row selection in single-page data tables. Uses a non-database
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
public class EmfDataTableMultiRowSelectionSinglePageTest extends AbstractEmfDataTableRowSelectionTest {

	private final IEmfDataTableDiv<TestTableRow> dataTableDiv;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setStringValue("foo"),
			new TestTableRow().setStringValue("bar"),
			new TestTableRow().setStringValue("baz"),
			new TestTableRow().setStringValue(null),
			new TestTableRow().setStringValue("zzz"));

	public EmfDataTableMultiRowSelectionSinglePageTest() {

		dataTableDiv = setNode(createDataTableDivBuilder(() -> rows).setRowSelectionMode(EmfDataTableRowSelectionMode.MULTI).build());
	}

	@Test
	public void testWithInitialization() {

		assertNumberOfTableBodyRows(rows.size());
		assertNoRowsSelected(dataTableDiv);
		assertStatusTextWithRowsOnCurrentPage(0);
	}

	@Test
	public void testWithSomeSelectedRows() {

		clickRowSelectionCells(0, 3, 4);

		assertSelectedRowsWithValues(dataTableDiv, "foo", "zzz", null);
		assertStatusTextWithRowsOnCurrentPage(3);
	}

	@Test
	public void testWithSelectAllOnCurrentPageButton() {

		clickSelectAllOnCurrentPageButton();

		assertSelectedRowsWithValues(dataTableDiv, "foo", "bar", "baz", "zzz", null);
		assertStatusTextWithRowsOnCurrentPage(5);
	}

	@Test
	public void testWithSelectInvertCurrentPageButton() {

		clickRowSelectionCells(0, 3, 4);
		clickSelectInvertCurrentPageButton();

		assertSelectedRowsWithValues(dataTableDiv, "bar", "baz");
		assertStatusTextWithRowsOnCurrentPage(2);
	}

	@Test
	public void testWithUnselectAllOnCurrentPageButton() {

		clickRowSelectionCells(0, 3, 4);
		clickUnselectAllOnCurrentPageButton();

		assertNoRowsSelected(dataTableDiv);
		assertStatusTextWithRowsOnCurrentPage(0);
	}

	@Test
	public void testNoUnselectAllPagesButton() {

		findNodes(EmfTableRowSelectionMarker.BUTTON_UNSELECT_ALL_PAGES).assertNone();
	}
}
