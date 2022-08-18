package com.softicar.platform.emf.data.table.testing;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomPopupTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomTableTester;
import com.softicar.platform.emf.EmfTestMarker;

public class EmfDataTableTester extends DomTableTester {

	public EmfDataTableTester(IDomTestExecutionEngine engine, DomTable node) {

		super(engine, node);
	}

	public DomPopupTester openFilterPopup(IStaticObject columnMarker) {

		findHeaderCell(columnMarker).clickNode(EmfTestMarker.DATA_TABLE_FILTER_POPUP_BUTTON);
		return findFilterPopup(columnMarker);
	}

	public DomPopupTester findFilterPopup(IStaticObject columnMarker) {

		return findBody().findPopup(EmfTestMarker.DATA_TABLE_FILTER_POPUP, columnMarker);
	}

	public void clickOrderByButton(IStaticObject column) {

		findHeaderCell(column)//
			.findButton(EmfTestMarker.DATA_TABLE_ORDER_BY_BUTTON)
			.click();
	}
}
