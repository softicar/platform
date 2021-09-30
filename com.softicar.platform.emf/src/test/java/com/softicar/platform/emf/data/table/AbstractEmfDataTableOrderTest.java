package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;

abstract class AbstractEmfDataTableOrderTest extends AbstractEmfDataTableTest {

	protected void clickOrderByButton(IDataTableColumn<TestTableRow, ?> column) {

		assertHeaderCell(column).findNode(EmfDataTableDivMarker.ORDER_BY_BUTTON).click();
	}
}
