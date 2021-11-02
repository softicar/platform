package com.softicar.platform.emf.data.table.testing;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomTableTester;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.data.table.EmfDataTableHeaderCell;

public class EmfDataTableTester extends DomTableTester {

	public EmfDataTableTester(IDomTestEngine engine, DomTable node) {

		super(engine, node);
	}

	public IDomNodeIterable<DomCell> getCells(IDataTableColumn<?, ?> column) {

		IDomNode headerCell = getHeaderCell(column).getNode();
		int childIndex = headerCell.getParent().getChildIndex(headerCell);
		return getBodyRows().map(row -> row.getChild(childIndex)).withType(DomCell.class);
	}

	public DomNodeTester getHeaderCell(IDataTableColumn<?, ?> column) {

		return getHeadRows()//
			.last()
			.findNodes(EmfDataTableHeaderCell.class)
			.filter(headerCell -> headerCell.getDataColumn() == column)
			.assertOne();
	}
}
