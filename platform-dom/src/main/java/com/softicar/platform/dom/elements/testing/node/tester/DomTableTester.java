package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTBody;
import com.softicar.platform.dom.elements.DomTHead;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;

public class DomTableTester extends AbstractDomNodeTester<DomTable> {

	public DomTableTester(IDomTestEngine engine, DomTable node) {

		super(engine, node);
	}

	public IDomNodeIterable<DomCell> findCells(IStaticObject marker) {

		return findNodes(marker).withType(DomCell.class);
	}

	public IDomNodeIterable<DomRow> getHeadRows() {

		return findNodes(DomTHead.class)//
			.assertOne()
			.findNodes(DomRow.class);
	}

	public IDomNodeIterable<DomRow> getBodyRows() {

		return findNodes(DomTBody.class)//
			.assertOne()
			.findNodes(DomRow.class);
	}
}
