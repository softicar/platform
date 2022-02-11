package com.softicar.platform.emf.data.table;





import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.dom.elements.testing.node.DomNodeAssertionError;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.emf.data.table.column.settings.IEmfDataTableColumnSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.junit.Test;

/**
 * UI test for the basic visualization of the body of a data table. Uses a
 * non-database back-end.
 * <p>
 * Asserts the presence of UI elements providing certain functionality (like row
 * selection, etc.) but does not actually test that functionality.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableBodyVisualizationTest extends AbstractEmfDataTableTest {

	@Test
	public void testVisualizationWithEmptyTable() {

		setNodeSupplier(createDataTableDivBuilder(ArrayList::new)::build);

		findNodes(EmfDataTableDivMarker.TABLE_DIV)//
			.assertOne()
			.findNodes(EmfDataTableDivMarker.EMPTY_TABLE_PLACEHOLDER_ROW)
			.assertOne()
			.findNodes(EmfDataTableDivMarker.EMPTY_TABLE_PLACEHOLDER_CELL)
			.assertOne()
			.assertContainsText(EmfDataTableI18n.NO_ENTRIES_FOUND);
	}

	@Test
	public void testVisualizationWithNonEmptyTable() {

		TestTableRowSupplier rowSupplier = new TestTableRowSupplier();
		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(rowSupplier);
		builder.setHidden(getDataTable().getLongColumn(), true);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = builder.build();
		setNodeSupplier(() -> dataTableDiv);

		assertRowValues(rowSupplier, dataTableDiv);
	}

	@Test
	public void testVisualizationWithNonEmptyTableAndRowSelection() {

		TestTableRowSupplier rowSupplier = new TestTableRowSupplier();
		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(rowSupplier);
		builder.setHidden(getDataTable().getLongColumn(), true);
		builder.setRowSelectionMode(EmfDataTableRowSelectionMode.MULTI);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = builder.build();
		setNodeSupplier(() -> dataTableDiv);

		assertRowValues(rowSupplier, dataTableDiv);
	}

	private void assertRowValues(Supplier<List<TestTableRow>> rowSupplier, IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		List<TestTableRow> rows = rowSupplier.get();
		List<DomNodeTester> tableBodyRows = assertExpectedNumberOfTableBodyRows(rows, dataTableDiv);

		for (int rowIndex = 0; rowIndex < tableBodyRows.size(); rowIndex++) {
			TestTableRow row = rows.get(rowIndex);
			DomNodeTester tableBodyRowNode = tableBodyRows.get(rowIndex);
			List<? extends IDataTableColumn<TestTableRow, ?>> columns = getVisibleColumns(dataTableDiv);

			assertRowSelectionCell(dataTableDiv, tableBodyRowNode);

			List<DomNodeTester> cellNodes = tableBodyRowNode//
				.findNodes(EmfDataTableDivMarker.BODY_CELL)
				.assertSize(columns.size());
			assertColumnValues(dataTableDiv, row, columns, cellNodes);
		}
	}

	private void assertRowSelectionCell(IEmfDataTableDiv<TestTableRow> dataTableDiv, DomNodeTester tableBodyRowNode) {

		if (dataTableDiv.isRowSelectionEnabled()) {
			tableBodyRowNode.findNodes(EmfDataTableDivMarker.ROW_SELECTION_CHECKBOX).assertOne();
		} else {
			tableBodyRowNode.findNodes(EmfDataTableDivMarker.ROW_SELECTION_CHECKBOX).assertNone();
		}
	}

	private List<DomNodeTester> assertExpectedNumberOfTableBodyRows(List<TestTableRow> rows, IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		assertEquals("The row counts of the table and the list provided by the row supplier must match.", rows.size(), dataTableDiv.getTotalRowCount());

		List<DomNodeTester> tableBodyRows = findNodes(EmfDataTableDivMarker.TABLE_DIV)//
			.assertOne()
			.findNodes(EmfDataTableDivMarker.BODY_ROW)
			.assertSize(rows.size());

		return tableBodyRows;
	}

	private void assertColumnValues(IEmfDataTableDiv<TestTableRow> dataTableDiv, TestTableRow row, List<? extends IDataTableColumn<TestTableRow, ?>> columns,
			List<DomNodeTester> cellNodes) {

		for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
			IDataTableColumn<TestTableRow, ?> column = columns.get(columnIndex);
			IEmfDataTableColumnSettings columnSettings = dataTableDiv.getColumnSettings(column);

			DomNodeTester cellNode = cellNodes.get(columnIndex);
			if (columnSettings.isHidden()) {
				cellNode.findChildren().assertNone();
			} else {
				assertCellValue(cellNode, row, column);
			}
		}
	}

	private void assertCellValue(DomNodeTester cellNode, TestTableRow row, IDataTableColumn<TestTableRow, ?> column) {

		Object cellValue = assertCellValueType(column, column.getValue(row));
		IDomNodeIterable<IDomNode> cellChildren = cellNode.findChildren();
		if (cellValue != null) {
			assertCellValueDisplayed(cellChildren, column, cellValue);
		} else {
			assertNoCellValueDisplayed(cellChildren, column);
		}
	}

	private void assertCellValueDisplayed(IDomNodeIterable<IDomNode> cellChildren, IDataTableColumn<TestTableRow, ?> column, Object cellValue) {

		if (columnsOfValueClassGenerateTextNodes(column)) {

			// TODO: The following is simplified way too much.
			// TODO: Introduce rendering tests for all basic value types instead of just checking if there is ANY
			// content.
			// TODO: Build a distinction of cases wrt. column.getValueClass() ?
			// TODO: However, do not necessarily implement that in here.

			IDomNode cellChildNode = cellChildren.assertOne(createAssertionFailedMessage(column)).getNode();
			String nodeText = ((DomTextNode) cellChildNode).getText().trim();
			if (cellValue instanceof String) {
				String cellValueString = (String) cellValue;
				if (!cellValueString.equals("")) {
					assertNotEquals("A the non-empty String value of \"%s\" was displayed as an empty text.", "", nodeText);
				} else {
					// nothing to do -- empty strings result in empty cells
				}
			} else {
				assertNotEquals("A non-null value was displayed as an empty text.", "", nodeText);
			}
		}
	}

	private boolean columnsOfValueClassGenerateTextNodes(IDataTableColumn<TestTableRow, ?> column) {

		Class<?> valueClass = Objects.requireNonNull(column.getValueClass());
		// currently, only boolean columns result in non-textual cells (i.e. checkbox icons)
		if (valueClass.equals(Boolean.class)) {
			return false;
		} else {
			return true;
		}
	}

	private void assertNoCellValueDisplayed(IDomNodeIterable<IDomNode> cellChildren, IDataTableColumn<TestTableRow, ?> column) {

		// there may be either no child node at all, or an empty text node
		assertTrue(cellChildren.size() <= 1);
		if (cellChildren.size() == 1) {
			IDomNode cellChildNode = cellChildren.iterator().next();
			assertNotNull(createAssertionFailedMessage(column), cellChildNode);
			if (cellChildNode instanceof DomTextNode) {
				String nodeText = ((DomTextNode) cellChildNode).getText().trim();
				assertEquals(createAssertionFailedMessage(column), "", nodeText);
			} else {
				throw new DomNodeAssertionError(
					createAssertionFailedMessage(column),
					"Unexpected child node of class %s.",
					cellChildNode.getClass().getCanonicalName());
			}
		}
	}

	private Object assertCellValueType(IDataTableColumn<TestTableRow, ?> column, Object cellValue) {

		if (cellValue != null && !column.getValueClass().isAssignableFrom(cellValue.getClass())) {
			throw new DomNodeAssertionError(
				createAssertionFailedMessage(column),
				"Encountered an unexpected cell value of type %s while expecting a value of type %s.",
				cellValue.getClass().getCanonicalName(),
				column.getValueClass().getCanonicalName());
		}
		return cellValue;
	}
}
