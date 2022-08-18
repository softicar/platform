package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

/**
 * UI test for the effects that interacting with a navigation element has on a
 * data table's body. Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTablePagingTest extends AbstractEmfDataTableTest {

	private final int numTotalRows;
	private final TestTableGeneratedRowSupplier rowSupplier;
	private final EmfDataTableDivBuilder<TestTableRow> builder;

	public EmfDataTablePagingTest() {

		numTotalRows = 512;
		rowSupplier = new TestTableGeneratedRowSupplier(numTotalRows);
		builder = createDataTableDivBuilder(rowSupplier);
	}

	@Test
	public void testPagingWithNextPageButton() {

		IEmfDataTableDiv<TestTableRow> dataTableDiv = setNode(builder.build());

		assertFirstPageDisplayed(dataTableDiv);
		clickNextPageButton();
		assertSecondPageDisplayed(dataTableDiv);
	}

	@Test
	public void testPagingWithPrevPageButton() {

		IEmfDataTableDiv<TestTableRow> dataTableDiv = setNode(builder.build());
		dataTableDiv.setCurrentPage(1);

		assertSecondPageDisplayed(dataTableDiv);
		clickPrevPageButton();
		assertFirstPageDisplayed(dataTableDiv);
	}

	@Test
	public void testPagingWithPageNumberButton() {

		IEmfDataTableDiv<TestTableRow> dataTableDiv = setNode(builder.build());

		assertFirstPageDisplayed(dataTableDiv);
		clickPageNumberButton(1);
		assertSecondPageDisplayed(dataTableDiv);
	}

	@Test
	public void testPagingWithEllipsisButton() {

		final int pageSize = 18;

		builder.setPageSize(pageSize);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = setNode(builder.build());

		assertFirstPageDisplayed(dataTableDiv);
		clickEllipsisButton();
		// TODO: properly determine the page number at which we want to end up
		assertTrue("Wrong page was displayed.", dataTableDiv.getCurrentPage() > 1);
	}

	private void clickNextPageButton() {

		findFirstButton(DomTestMarker.PAGEABLE_TABLE_NAVIGATION_PAGE_NEXT_BUTTON).click();
	}

	private void clickPrevPageButton() {

		findFirstButton(DomTestMarker.PAGEABLE_TABLE_NAVIGATION_PAGE_PREV_BUTTON).click();
	}

	private void clickPageNumberButton(int targetPageIndex) {

		String targetPageNumberString = targetPageIndex + 1 + "";

		findNodes(DomTestMarker.PAGEABLE_TABLE_NAVIGATION_PAGE_NUMBER_BUTTON)//
			.withType(DomButton.class)
			.withText(targetPageNumberString)
			.assertSome()
			.iterator()
			.next()
			.click();
	}

	private void clickEllipsisButton() {

		findNodes(DomTestMarker.PAGEABLE_TABLE_NAVIGATION_PAGE_NUMBER_BUTTON)//
			.withType(DomButton.class)
			.withText("...")
			.assertSome()
			.iterator()
			.next()
			.click();
	}

	private void assertFirstPageDisplayed(IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		assertExpectedPageDisplayed(dataTableDiv, 0);
	}

	private void assertSecondPageDisplayed(IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		assertExpectedPageDisplayed(dataTableDiv, 1);
	}

	private void assertExpectedPageDisplayed(IEmfDataTableDiv<TestTableRow> dataTableDiv, int expectedPageIndex) {

		final int currentPageIndex = dataTableDiv.getCurrentPage();
		final int totalRowCount = dataTableDiv.getTotalRowCount();
		final int pageSize = dataTableDiv.getPageSize();
		final int firstRowIndexOnPage = expectedPageIndex * pageSize;
		final int expectedRowCountOnPage = EmfDataTableTestUtil.calculateRowCountOnPage(currentPageIndex, totalRowCount, pageSize);

		assertEquals("Wrong page was displayed.", expectedPageIndex, currentPageIndex);

		List<DomNodeTester> tableBodyRows = findNodes(EmfTestMarker.DATA_TABLE_TABLE_DIV)//
			.assertOne()
			.findNodes(EmfTestMarker.DATA_TABLE_BODY_ROW)
			.assertSize(expectedRowCountOnPage);

		Collection<? extends IDataTableColumn<TestTableRow, ?>> columns = getDataTable().getTableColumns();
		IDataTableColumn<TestTableRow, ?> integerColumn = getDataTable().getIntegerColumn();
		assertTrue(columns.contains(integerColumn));

		List<TestTableRow> rows = rowSupplier.get();
		for (int i = 0; i < expectedRowCountOnPage; i++) {
			TestTableRow row = rows.get(firstRowIndexOnPage + i);
			DomNodeTester tableBodyRowNode = tableBodyRows.get(i);

			List<DomNodeTester> cellNodesFromDomRow = tableBodyRowNode//
				.findNodes(EmfTestMarker.DATA_TABLE_BODY_CELL)
				.assertSize(columns.size());

			assertExpectedRowDisplayed(integerColumn, row, cellNodesFromDomRow);
		}
	}

	/**
	 * Uses the unique-valued integer column to identify a row, comparing the
	 * integer value of the data model row to the integer value of the rendered
	 * DOM row.
	 */
	private void assertExpectedRowDisplayed(IDataTableColumn<TestTableRow, ?> column, TestTableRow row, List<DomNodeTester> cellNodesFromDomRow) {

		cellNodesFromDomRow//
			.get(getIndexOfHeaderCell(column))
			.findChildren()
			.assertOne()
			.assertContainsText(column.getValue(row).toString());
	}
}
