package com.softicar.platform.emf.data.table;


import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * UI test for the basic visualization of the header of a data table. Uses a
 * non-database back-end.
 * <p>
 * Asserts the presence of UI elements providing certain functionality (like
 * filtering, ordering, etc.) but does not actually test that functionality.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTablePrimaryHeaderVisualizationTest extends AbstractEmfDataTableTest {

	@Test
	public void testVisualizationWithEmptyTable() {

		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(ArrayList::new);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = executeSetup(builder);
		executeAssertions(dataTableDiv);
	}

	@Test
	public void testVisualizationWithEmptyTableAndRowSelection() {

		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(ArrayList::new);
		builder.setRowSelectionMode(EmfDataTableRowSelectionMode.MULTI);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = executeSetup(builder);
		executeAssertions(dataTableDiv);
	}

	@Test
	public void testVisualizationWithSinglePageTable() {

		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(new TestTableRowSupplier());
		IEmfDataTableDiv<TestTableRow> dataTableDiv = executeSetup(builder);
		executeAssertions(dataTableDiv);
	}

	@Test
	public void testVisualizationWithSinglePageTableAndRowSelection() {

		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(new TestTableRowSupplier());
		builder.setRowSelectionMode(EmfDataTableRowSelectionMode.MULTI);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = executeSetup(builder);
		executeAssertions(dataTableDiv);
	}

	@Test
	public void testVisualizationWithMultiPageTableAfterPaging() {

		final int pageSize = 3;
		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(new TestTableRowSupplier());
		builder.setPageSize(pageSize);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = executeSetup(builder);

		assertTrue(dataTableDiv.getTotalRowCount() > pageSize);
		executeAssertions(dataTableDiv);

		dataTableDiv.setCurrentPage(1);
		executeAssertions(dataTableDiv);
	}

	private IEmfDataTableDiv<TestTableRow> executeSetup(EmfDataTableDivBuilder<TestTableRow> builder) {

		builder.setColumnTitle(getDataTable().getIntegerColumn(), "foo");
		builder.setHidden(getDataTable().getLongColumn(), true);
		return setNode(builder.build());
	}

	private void executeAssertions(IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		DomNodeTester tableDivNode = findNodes(EmfDataTableDivMarker.TABLE_DIV).assertOne();
		DomNodeTester headerRowNode = tableDivNode.findNodes(EmfDataTableDivMarker.HEADER_PRIMARY_ROW).assertOne();

		if (dataTableDiv.isRowSelectionEnabled()) {
			headerRowNode.findNodes(EmfDataTableDivMarker.ACTION_HEADER_CELL).assertOne();
		} else {
			headerRowNode.findNodes(EmfDataTableDivMarker.ACTION_HEADER_CELL).assertNone();
		}

		List<IDataTableColumn<TestTableRow, ?>> columns = getVisibleColumns(dataTableDiv);
		List<DomNodeTester> headerCells = headerRowNode.findNodes(EmfDataTableDivMarker.HEADER_PRIMARY_CELL).assertSize(columns.size());

		for (int i = 0; i < columns.size(); i++) {
			DomNodeTester headerCellNode = headerCells.get(i);
			IDataTableColumn<TestTableRow, ?> column = columns.get(i);

			assertHeaderCaption(headerCellNode, column, dataTableDiv.getColumnSettings(column).getTitleOverride());
			assertHeaderClickableFilter(dataTableDiv, headerCellNode, column);
			assertOrderByColumnButton(dataTableDiv, headerCellNode, column);
		}
	}

	private void assertHeaderCaption(DomNodeTester headerCellNode, IDataTableColumn<TestTableRow, ?> column, IDisplayString titleOverride) {

		IDisplayString expectedCaption = titleOverride;
		if (expectedCaption == null) {
			expectedCaption = column.getTitle();
		}
		headerCellNode.findNodes(EmfDataTableDivMarker.HEADER_CAPTION).assertOne().assertContainsText(expectedCaption);
	}

	private void assertHeaderClickableFilter(IEmfDataTableDiv<TestTableRow> dataTableDiv, DomNodeTester headerCellNode,
			IDataTableColumn<TestTableRow, ?> column) {

		if (dataTableDiv.isFilterable(column)) {
			headerCellNode.findNodes(EmfDataTableDivMarker.FILTER_POPUP_BUTTON).assertOne();
		} else {
			headerCellNode.findNodes(EmfDataTableDivMarker.FILTER_POPUP_BUTTON).assertNone();
		}
	}

	private void assertOrderByColumnButton(IEmfDataTableDiv<TestTableRow> dataTableDiv, DomNodeTester headerCellNode,
			IDataTableColumn<TestTableRow, ?> column) {

		if (dataTableDiv.isSortable(column)) {
			headerCellNode.findNodes(EmfDataTableDivMarker.ORDER_BY_BUTTON).assertOne();
		} else {
			headerCellNode.findNodes(EmfDataTableDivMarker.ORDER_BY_BUTTON).assertNone();
		}
	}
}
