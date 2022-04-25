package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTBody;
import com.softicar.platform.dom.elements.DomTHead;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import java.util.stream.Collectors;

public class DomTableTester extends AbstractDomNodeTester<DomTable> {

	public DomTableTester(IDomTestExecutionEngine engine, DomTable node) {

		super(engine, node);
	}

	public IDomNodeIterable<DomCell> findCells(IStaticObject marker) {

		return findNodes(marker).withType(DomCell.class);
	}

	/**
	 * @deprecated use {@link #getTextInCells(IStaticObject)}
	 */
	@Deprecated
	public String getTextInColumns(IStaticObject marker) {

		return getTextInCells(marker);
	}

	public String getTextInCells(IStaticObject marker) {

		return findCells(marker)//
			.stream()
			.map(cell -> new DomNodeTester(getEngine(), cell))
			.map(cell -> cell.getAllTextInDocument("|"))
			.collect(Collectors.joining("|"));
	}

	public DomNodeTester findHeaderCell(IStaticObject marker) {

		return findHeaderCells(marker).assertOne();
	}

	public IDomNodeIterable<DomHeaderCell> findHeaderCells(IStaticObject marker) {

		return findNodes(marker).withType(DomHeaderCell.class);
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
