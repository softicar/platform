package com.softicar.platform.emf.data.table.testing;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomPopupTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomTableTester;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;

public class EmfDataTableTester extends DomTableTester {

	public EmfDataTableTester(IDomTestEngine engine, DomTable node) {

		super(engine, node);
	}

	public DomPopupTester openFilterPopup(IStaticObject columnMarker) {

		findHeaderCell(columnMarker).clickNode(EmfDataTableDivMarker.FILTER_POPUP_BUTTON);
		return findFilterPopup(columnMarker);
	}

	public DomPopupTester findFilterPopup(IStaticObject columnMarker) {

		return findBody().findPopup(EmfDataTableDivMarker.FILTER_POPUP, columnMarker);
	}
}
