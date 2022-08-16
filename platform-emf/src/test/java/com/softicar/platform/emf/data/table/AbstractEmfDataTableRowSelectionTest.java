package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

abstract class AbstractEmfDataTableRowSelectionTest extends AbstractEmfDataTableTest {

	protected void assertNoRowsSelected(IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		assertEquals(0, dataTableDiv.getSelectedRows().size());
	}

	protected void assertSelectedRowsWithValues(IEmfDataTableDiv<TestTableRow> dataTableDiv, String...values) {

		Collection<TestTableRow> selectedRows = dataTableDiv.getSelectedRows();
		assertEquals("Unexpected number of selected rows.", values.length, selectedRows.size());
		List<String> valueList = new ArrayList<>(Arrays.asList(values));

		for (TestTableRow selectedRow: selectedRows) {
			String value = selectedRow.getStringValue();
			assertTrue(String.format("Failed to find value \"%s\" among selected rows or encountered it several times.", value), valueList.contains(value));
			valueList.removeAll(Collections.singleton(value));
		}

		assertTrue(
			String.format("Failed to find the selected rows with the following value(s): \"%s\"", Imploder.implode(valueList, "\", \"")),
			valueList.isEmpty());
	}

	protected void assertStatusTextWithRowsOnCurrentPage(int numSelectedRowsOnCurrentPage) {

		IDisplayString label;
		if (numSelectedRowsOnCurrentPage == 1) {
			label = EmfDataTableI18n._1_ROW_SELECTED;
		} else {
			label = EmfDataTableI18n.ARG1_ROWS_SELECTED.toDisplay(numSelectedRowsOnCurrentPage);
		}

		getStatusTextElement().assertContainsText(label);
	}

	protected void assertStatusTextWithRowsOnCurrentAndOtherPages(int numSelectedRowsOnCurrentPage, int numSelectedRowsTotal) {

		int numSelectedRowsOnOtherPages = numSelectedRowsTotal - numSelectedRowsOnCurrentPage;

		IDisplayString label;
		if (numSelectedRowsTotal == 1) {
			label = EmfDataTableI18n._1_ROW_SELECTED_ON_ANOTHER_PAGE;
		} else {
			label = EmfDataTableI18n.ARG1_OF_ARG2_ROWS_SELECTED_ON_OTHER_PAGES.toDisplay(numSelectedRowsOnOtherPages, numSelectedRowsTotal);
		}

		getStatusTextElement().assertContainsText(label);
	}

	protected void clickSelectAllOnCurrentPageButton() {

		findButton(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_SELECT_ALL_ON_CURRENT_PAGE).click();
	}

	protected void clickSelectInvertCurrentPageButton() {

		findButton(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_SELECT_INVERT_CURRENT_PAGE).click();
	}

	protected void clickUnselectAllOnCurrentPageButton() {

		findButton(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_UNSELECT_ALL_ON_CURRENT_PAGE).click();
	}

	protected void clickUnselectAllPagesButton() {

		findButton(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_UNSELECT_ALL_PAGES).click();
	}

	protected void clickRowSelectionCells(int...rowIndexes) {

		List<DomNodeTester> rowSelectionCells = findNodes(EmfTestMarker.DATA_TABLE_ROW_SELECTION_CHECKBOX).toList();
		for (int rowIndex: rowIndexes) {
			rowSelectionCells.get(rowIndex).click();
		}
	}

	private DomNodeTester getStatusTextElement() {

		return findNodes(EmfTestMarker.DATA_TABLE_ROW_SELECTION_CONTROL_ELEMENT)//
			.assertOne()
			.findNodes(EmfTestMarker.DATA_TABLE_ROW_SELECTION_STATUS_TEXT_ELEMENT)
			.assertOne();
	}
}
