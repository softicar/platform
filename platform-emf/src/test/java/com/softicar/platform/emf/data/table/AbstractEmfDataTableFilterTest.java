package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;

abstract class AbstractEmfDataTableFilterTest extends AbstractEmfDataTableTest {

	protected void openFilterPopup(IDataTableColumn<TestTableRow, ?> column) {

		clickColumnFilterButton(column);
		findNodes(EmfDataTableDivMarker.FILTER_POPUP).assertOne();
	}

	protected void confirmFilterPopup() {

		findButton(EmfDataTableDivMarker.FILTER_EXECUTE_BUTTON).click();
		findNodes(EmfDataTableDivMarker.FILTER_POPUP).assertNone();
	}

	protected void assertNoColumnFilterButton(IDataTableColumn<TestTableRow, ?> column) {

		assertHeaderCell(column)//
			.findNodes(EmfDataTableDivMarker.FILTER_POPUP_BUTTON)
			.assertNone();
	}

	private void clickColumnFilterButton(IDataTableColumn<TestTableRow, ?> column) {

		assertHeaderCell(column)//
			.findNode(EmfDataTableDivMarker.FILTER_POPUP_BUTTON)
			.click();
	}
}
