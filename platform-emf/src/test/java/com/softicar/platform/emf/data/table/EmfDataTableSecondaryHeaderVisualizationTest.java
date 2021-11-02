package com.softicar.platform.emf.data.table;


import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class EmfDataTableSecondaryHeaderVisualizationTest extends AbstractEmfDataTableExtraRowVisualizationTest {

	private static final IDisplayString BAR = IDisplayString.create("bar");
	private static final IDisplayString BAZ = IDisplayString.create("baz");
	private static final IDisplayString FOO = IDisplayString.create("foo");

	@Test
	public void testWithoutConfiguration() {

		setup(builder);
		tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertNone();
	}

	@Test
	public void testWithSingleSecondaryHeaderAndEmptyConfiguration() {

		builder.addSecondaryHeaderRow();

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
	}

	@Test
	public void testWithSingleSecondaryHeaderAndEmptyConfigurationAndRowSelectionEnabled() {

		builder.addSecondaryHeaderRow();
		builder.setRowSelectionMode(EmfDataTableRowSelectionMode.SINGLE);

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size() + 1);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndEmptyConfigurationAndActionColumnEnabled() {

		builder.addSecondaryHeaderRow();
		builder.setActionColumnHandler((cell, row) -> {
			// nothing
		});

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size() + 1);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndEmptyConfigurationAndRowSelectionAndActionColumnEnabled() {

		builder.addSecondaryHeaderRow();
		builder.setRowSelectionMode(EmfDataTableRowSelectionMode.SINGLE);
		builder.setActionColumnHandler((cell, row) -> {
			// nothing
		});

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size() + 1);
	}

	@Test
	public void testWithMultipleSecondaryHeadersStacked() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(UPPER)
			.setColumns(getDataTable().getIntegerColumn());

		builder//
			.addSecondaryHeaderRow()
			.addCell(LOWER)
			.setColumns(getDataTable().getIntegerColumn());

		setup(builder);

		List<DomNodeTester> headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertSize(2);

		assertTotalColspan(headerRow.get(0), getDataTable().getTableColumns().size());
		headerRow.get(0).assertContainsText("upper");

		assertTotalColspan(headerRow.get(1), getDataTable().getTableColumns().size());
		headerRow.get(1).assertContainsText("lower");
	}

	@Test
	public void testWithSingleSecondaryHeaderAndNonBlankFirstCell() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getIntegerColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(2);
		assertCell(headerCells.get(0), FOO, 1);
		assertCell(headerCells.get(1), null, 9);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndNonBlankFirstCellWithColspanTwo() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getIntegerColumn(),
				getDataTable().getLongColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(2);
		assertCell(headerCells.get(0), FOO, 2);
		assertCell(headerCells.get(1), null, 8);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndBlankFirstCell() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getLongColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(3);
		assertCell(headerCells.get(0), null, 1);
		assertCell(headerCells.get(1), FOO, 1);
		assertCell(headerCells.get(2), null, 8);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndBlankFirstCellWithColspanTwo() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getDoubleColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(3);
		assertCell(headerCells.get(0), null, 2);
		assertCell(headerCells.get(1), FOO, 1);
		assertCell(headerCells.get(2), null, 7);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndNonBlankLastCell() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getEntityColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(2);
		assertCell(headerCells.get(0), null, 9);
		assertCell(headerCells.get(1), FOO, 1);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndNonBlankLastCellWithColspanTwo() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getTestEnumColumn(),
				getDataTable().getEntityColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(2);
		assertCell(headerCells.get(0), null, 8);
		assertCell(headerCells.get(1), FOO, 2);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndBlankLastCell() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getTestEnumColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(3);
		assertCell(headerCells.get(0), null, 8);
		assertCell(headerCells.get(1), FOO, 1);
		assertCell(headerCells.get(2), null, 1);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndBlankLastCellWithColspanTwo() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getDayTimeColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(3);
		assertCell(headerCells.get(0), null, 7);
		assertCell(headerCells.get(1), FOO, 1);
		assertCell(headerCells.get(2), null, 2);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndIntermediateBlankCell() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getIntegerColumn())
			.addCell(BAR)
			.setColumns(getDataTable().getDoubleColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(4);
		assertCell(headerCells.get(0), FOO, 1);
		assertCell(headerCells.get(1), null, 1);
		assertCell(headerCells.get(2), BAR, 1);
		assertCell(headerCells.get(3), null, 7);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndIntermediateBlankCellWithColspanTwo() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getIntegerColumn())
			.addCell(BAR)
			.setColumns(getDataTable().getBigDecimalColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(4);
		assertCell(headerCells.get(0), FOO, 1);
		assertCell(headerCells.get(1), null, 2);
		assertCell(headerCells.get(2), BAR, 1);
		assertCell(headerCells.get(3), null, 6);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndCollapsedColumn() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getIntegerColumn(),
				getDataTable().getLongColumn(),
				getDataTable().getDoubleColumn());
		builder.setHidden(getDataTable().getIntegerColumn(), true);

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getVisibleColumns(tableDiv).size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(2);
		assertCell(headerCells.get(0), FOO, 2);
		assertCell(headerCells.get(1), null, 7);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndConcealedColumn() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getIntegerColumn(),
				getDataTable().getLongColumn(),
				getDataTable().getDoubleColumn());
		builder.setConcealed(getDataTable().getIntegerColumn(), true);

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size() - 1);
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(2);
		assertCell(headerCells.get(0), FOO, 2);
		assertCell(headerCells.get(1), null, 7);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndAllColumnsConcealed() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getLongColumn(),
				getDataTable().getDoubleColumn());
		builder.setConcealed(getDataTable().getLongColumn(), true);
		builder.setConcealed(getDataTable().getDoubleColumn(), true);

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size() - 2);
		headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertOne();
	}

	@Test
	public void testWithSingleSecondaryHeaderAndOverlap() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getIntegerColumn(),
				getDataTable().getLongColumn(),
				getDataTable().getDoubleColumn())
			.addCell(BAR)
			.setColumns(//
				getDataTable().getDoubleColumn(),
				getDataTable().getBigDecimalColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(3);
		assertCell(headerCells.get(0), FOO, 2);
		assertCell(headerCells.get(1), BAR, 2);
		assertCell(headerCells.get(2), null, 6);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndGap() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getIntegerColumn(),
				getDataTable().getDoubleColumn(),
				getDataTable().getBigDecimalColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(4);
		assertCell(headerCells.get(0), FOO, 1);
		assertCell(headerCells.get(1), null, 1);
		assertCell(headerCells.get(2), FOO, 2);
		assertCell(headerCells.get(3), null, 6);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndSameName() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getIntegerColumn(), getDataTable().getLongColumn())
			.addCell(FOO)
			.setColumns(getDataTable().getDoubleColumn(), getDataTable().getBigDecimalColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(3);
		assertCell(headerCells.get(0), FOO, 2);
		assertCell(headerCells.get(1), FOO, 2);
		assertCell(headerCells.get(2), null, 6);
	}

	@Test
	public void testWithSingleSecondaryHeaderAndInterleaving() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getIntegerColumn(), getDataTable().getLongColumn(), getDataTable().getDoubleColumn())
			.addCell(BAR)
			.setColumns(getDataTable().getLongColumn(), getDataTable().getBigDecimalColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(5);
		assertCell(headerCells.get(0), FOO, 1);
		assertCell(headerCells.get(1), BAR, 1);
		assertCell(headerCells.get(2), FOO, 1);
		assertCell(headerCells.get(3), BAR, 1);
		assertCell(headerCells.get(4), null, 6);
	}

	@Test
	public void testWithSingleSecondaryHeaderAfterTablePaging() {

		List<TestTableRow> rows = Arrays
			.asList(//
				new TestTableRow().setIntegerValue(0),
				new TestTableRow().setIntegerValue(1),
				new TestTableRow().setIntegerValue(2));
		builder = createDataTableDivBuilder(() -> rows);
		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(getDataTable().getIntegerColumn());
		builder.setPageSize(2);

		setup(builder);
		tableDiv.setCurrentPage(1);

		assertEquals(1, tableDiv.getCurrentPage());
		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(2);
		assertCell(headerCells.get(0), FOO, 1);
		assertCell(headerCells.get(1), null, 9);
	}

	@Test
	public void testWithSingleSecondaryHeader() {

		builder//
			.addSecondaryHeaderRow()
			.addCell(FOO)
			.setColumns(//
				getDataTable().getLongColumn())
			.addCell(BAR)
			.setColumns(//
				getDataTable().getBigDecimalColumn(),
				getDataTable().getStringColumn())
			.addCell(BAZ)
			.setColumns(//
				getDataTable().getDayColumn());

		setup(builder);

		DomNodeTester headerRow = tableDivNode.findNodes(EmfDataTableDivMarker.EXTRA_ROW).assertOne();
		assertTotalColspan(headerRow, getDataTable().getTableColumns().size());
		List<DomNodeTester> headerCells = headerRow.findNodes(EmfDataTableDivMarker.EXTRA_ROW_CELL).assertSize(7);
		assertCell(headerCells.get(0), null, 1);
		assertCell(headerCells.get(1), FOO, 1);
		assertCell(headerCells.get(2), null, 1);
		assertCell(headerCells.get(3), BAR, 2);
		assertCell(headerCells.get(4), null, 1);
		assertCell(headerCells.get(5), BAZ, 1);
		assertCell(headerCells.get(6), null, 3);
	}
}
