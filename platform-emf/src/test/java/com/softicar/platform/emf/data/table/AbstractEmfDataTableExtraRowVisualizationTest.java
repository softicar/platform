package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.ArrayList;

public abstract class AbstractEmfDataTableExtraRowVisualizationTest extends AbstractEmfDataTableTest {

	protected static final IDisplayString LOWER = IDisplayString.create("lower");
	protected static final IDisplayString UPPER = IDisplayString.create("upper");
	protected EmfDataTableDivBuilder<TestTableRow> builder;
	protected IEmfDataTableDiv<TestTableRow> tableDiv;
	protected DomNodeTester tableDivNode;

	public AbstractEmfDataTableExtraRowVisualizationTest() {

		this.builder = createDataTableDivBuilder(ArrayList::new);
		this.tableDiv = null;
		this.tableDivNode = null;
	}

	protected void setup(EmfDataTableDivBuilder<TestTableRow> builder) {

		tableDiv = builder.build();
		setNodeSupplier(() -> tableDiv);
		tableDivNode = findNodes(EmfTestMarker.DATA_TABLE_TABLE_DIV).assertOne();
	}

	protected void assertCell(DomNodeTester cell, IDisplayString expectedText, int expectedColspan) {

		if (expectedText != null) {
			cell.assertContainsText(expectedText);
		} else {
			cell.assertContainsNoText();
		}
		assertColspan(cell, expectedColspan);
	}

	protected void assertTotalColspan(DomNodeTester headerRow, int expected) {

		assertEquals(expected, getTotalColspan(headerRow));
	}

	protected void assertColspan(DomNodeTester cell, int expected) {

		assertEquals(expected, getColspanFromCell(cell.getNode()));
	}

	protected int getTotalColspan(DomNodeTester headerRow) {

		return headerRow//
			.findNodes(EmfTestMarker.DATA_TABLE_EXTRA_ROW_CELL)
			.assertSome()
			.stream()
			.mapToInt(it -> getColspanFromCell(it.getNode()))
			.sum();
	}

	protected int getColspanFromCell(IDomNode cell) {

		IDomAttribute colspan = cell.getAttribute("colspan");
		if (colspan != null) {
			return Integer.parseInt(colspan.getValue());
		} else {
			return 1;
		}
	}
}
