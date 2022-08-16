package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.List;
import org.junit.Test;

public class EmfDataTableFooterVisualizationTest extends AbstractEmfDataTableExtraRowVisualizationTest {

	@Test
	public void testWithoutConfiguration() {

		setup(builder);

		tableDivNode.findNodes(EmfTestMarker.DATA_TABLE_EXTRA_ROW).assertNone();
	}

	@Test
	public void testWithSingleFooterRowAndEmptyConfiguration() {

		builder.addFooterRow();

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfTestMarker.DATA_TABLE_EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
	}

	@Test
	public void testWithMultipleFooterRowsStacked() {

		builder//
			.addFooterRow()
			.addCell(UPPER)
			.setColumns(getDataTable().getIntegerColumn());

		builder//
			.addFooterRow()
			.addCell(LOWER)
			.setColumns(getDataTable().getIntegerColumn());

		setup(builder);

		List<DomNodeTester> footerRows = tableDivNode.findNodes(EmfTestMarker.DATA_TABLE_EXTRA_ROW).assertSize(2);

		assertTotalColspan(footerRows.get(0), getDataTable().getTableColumns().size());
		footerRows.get(0).assertContainsText(UPPER);

		assertTotalColspan(footerRows.get(1), getDataTable().getTableColumns().size());
		footerRows.get(1).assertContainsText(LOWER);
	}
}
