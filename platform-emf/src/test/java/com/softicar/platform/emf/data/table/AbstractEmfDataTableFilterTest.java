package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.EmfTestMarker;

abstract class AbstractEmfDataTableFilterTest extends AbstractEmfDataTableTest {

	protected DomNodeTester openFilterPopup(IDataTableColumn<TestTableRow, ?> column) {

		clickColumnFilterButton(column);
		return findNodes(EmfTestMarker.DATA_TABLE_FILTER_POPUP).assertOne();
	}

	protected void confirmFilterPopup() {

		findButton(EmfTestMarker.DATA_TABLE_FILTER_EXECUTE_BUTTON).click();
		findNodes(EmfTestMarker.DATA_TABLE_FILTER_POPUP).assertNone();
	}

	protected void assertNoColumnFilterButton(IDataTableColumn<TestTableRow, ?> column) {

		assertHeaderCell(column)//
			.findNodes(EmfTestMarker.DATA_TABLE_FILTER_POPUP_BUTTON)
			.assertNone();
	}

	private void clickColumnFilterButton(IDataTableColumn<TestTableRow, ?> column) {

		assertHeaderCell(column)//
			.findNode(EmfTestMarker.DATA_TABLE_FILTER_POPUP_BUTTON)
			.click();
	}
}
