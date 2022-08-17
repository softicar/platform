package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.emf.EmfTestMarker;

abstract class AbstractEmfDataTableOrderTest extends AbstractEmfDataTableTest {

	protected void clickOrderByButton(IDataTableColumn<TestTableRow, ?> column) {

		assertHeaderCell(column).findNode(EmfTestMarker.DATA_TABLE_ORDER_BY_BUTTON).click();
	}
}
