package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractEmfDataTableTest extends AbstractEmfTest {

	private TestTable dataTable = null;

	protected EmfDataTableDivBuilder<TestTableRow> createDataTableDivBuilder(Supplier<? extends Iterable<TestTableRow>> rowSupplier) {

		return new EmfDataTableDivBuilder<>(createAndSaveDataTable(rowSupplier));
	}

	protected TestTable getDataTable() {

		return Objects.requireNonNull(dataTable, String.format("%s not created yet", IDataTable.class.getSimpleName()));
	}

	protected List<DomNodeTester> assertTableHeaderCells(int...size) {

		return findNodes(EmfTestMarker.DATA_TABLE_TABLE_DIV)//
			.assertOne()
			.findNodes(EmfTestMarker.DATA_TABLE_HEADER_PRIMARY_ROW)
			.assertOne()
			.findNodes(EmfTestMarker.DATA_TABLE_HEADER_PRIMARY_CELL)
			.assertSize(size.length);
	}

	protected DomNodeTester assertHeaderCell(IDataTableColumn<TestTableRow, ?> column) {

		return assertHeaderCaption(column).findParentOfType(DomHeaderCell.class);
	}

	protected DomNodeTester assertHeaderCaption(IDataTableColumn<TestTableRow, ?> column) {

		return findNodes(EmfTestMarker.DATA_TABLE_TABLE_DIV)//
			.assertOne()
			.findNodes(EmfTestMarker.DATA_TABLE_HEADER_PRIMARY_ROW)
			.assertOne()
			.findNodes(EmfTestMarker.DATA_TABLE_HEADER_CAPTION)
			.withText(column.getTitle())
			.assertOne();
	}

	protected int getIndexOfHeaderCell(IDataTableColumn<TestTableRow, ?> column) {

		IDomNode headerCell = assertHeaderCell(column).getNode();
		return headerCell.getParent().getChildIndex(headerCell);
	}

	protected List<DomNodeTester> assertNumberOfTableBodyRows(int number) {

		return findNodes(EmfTestMarker.DATA_TABLE_TABLE_DIV)//
			.assertOne()
			.findNodes(EmfTestMarker.DATA_TABLE_BODY_ROW)
			.assertSize(number);
	}

	protected String createAssertionFailedMessage(IDataTableColumn<TestTableRow, ?> column) {

		if (column != null) {
			return String
				.format(//
					"Assertion failed while processing column '%s' (value class: %s)",
					column.getTitle(),
					column.getValueClass().getCanonicalName());
		} else {
			return "Column reference was null";
		}
	}

	/**
	 * @param column
	 *            The table column whose textual content shall be investigated.
	 * @param expectedTexts
	 *            The expected textual contents of rows in the given column, in
	 *            expected sequence. Pass null arguments to assert nodes without
	 *            text.
	 */
	protected void assertRowsInColumnContainTexts(IDataTableColumn<TestTableRow, ?> column, String...expectedTexts) {

		List<DomNodeTester> rows = assertNumberOfTableBodyRows(expectedTexts.length);
		for (int i = 0; i < rows.size(); i++) {
			String expectedText = expectedTexts[i];
			DomNodeTester cell = getCell(rows.get(i), column);
			if (expectedText != null) {
				assertNodeContainsText(cell, expectedText);
			} else {
				assertNodeContainsNoText(cell);
			}
		}
	}

	protected void assertRowsInColumnContainCheckboxes(IDataTableColumn<TestTableRow, ?> column, Boolean...states) {

		List<DomNodeTester> rows = assertNumberOfTableBodyRows(states.length);
		for (int i = 0; i < rows.size(); i++) {
			Boolean state = states[i];
			DomNodeTester cell = getCell(rows.get(i), column);
			if (state != null) {
				assertCheckboxInCell(cell, state);
			} else {
				assertNodeContainsNoChildren(cell);
			}
		}
	}

	protected List<IDataTableColumn<TestTableRow, ?>> getVisibleColumns(IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		return getDataTable()//
			.getTableColumns()
			.stream()
			.filter(it -> !dataTableDiv.getColumnSettings(it).isHidden())
			.collect(Collectors.toList());
	}

	private TestTable createAndSaveDataTable(Supplier<? extends Iterable<TestTableRow>> rowSupplier) {

		return dataTable = new TestTable(rowSupplier);
	}

	private DomNodeTester getCell(DomNodeTester row, IDataTableColumn<TestTableRow, ?> column) {

		// FIXME: index based column retrieval will break as soon as we totally remove collapsed columns
//		return row.getChildrenInTree(EmfDataTableDivMarker.BODY_CELL).assertSome().get(column.getIndex());
		return row//
			.findNodes(EmfTestMarker.DATA_TABLE_BODY_CELL)
			.assertSome()
			.get(getIndexOfHeaderCell(column));
	}

	private void assertNodeContainsText(DomNodeTester node, String expectedText) {

		node.assertContainsText(expectedText);
	}

	private void assertNodeContainsNoText(DomNodeTester node) {

		assertEquals("", node.getAllTextsInTree().collect(Collectors.joining()));
	}

	private void assertNodeContainsNoChildren(DomNodeTester node) {

		node.findChildren().assertNone();
	}

	private void assertCheckboxInCell(DomNodeTester cell, boolean state) {

		DomCheckbox checkbox = cell//
			.findNodes(DomCheckbox.class)
			.assertOne()
			.assertType(DomCheckbox.class);
		assertEquals(state, checkbox.isChecked());
	}
}
