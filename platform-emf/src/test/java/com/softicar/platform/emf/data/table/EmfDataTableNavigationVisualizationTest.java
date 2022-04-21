package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableMarker;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.Test;

/**
 * UI test for the basic visualization of the navigation of a data table. Uses a
 * non-database back-end.
 * <p>
 * Asserts the presence of UI elements providing certain functionality (like
 * filtering, ordering, etc.) but does not actually test that functionality.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableNavigationVisualizationTest extends AbstractEmfDataTableTest {

	@Test
	public void testVisualizationWithEmptyTable() {

		setNodeSupplier(createDataTableDivBuilder(ArrayList::new)::build);

		List<DomNodeTester> navigationNodes = getAndAssertOneNavigationElement();
		for (DomNodeTester navigation: navigationNodes) {
			// for empty tables, navigation elements are expected to be present but their bodies are expected to be
			// empty
			navigation.findChildren().assertNone();
		}
	}

	@Test
	public void testVisualizationWithSinglePageTable() {

		setNodeSupplier(createDataTableDivBuilder(new TestTableGeneratedRowSupplier(5))::build);

		for (DomNodeTester navigation: getAndAssertOneNavigationElement()) {
			assertNavigationLayoutSinglePage(navigation);
			navigation.findNodes(DomPageableTableMarker.NAVIGATION_PAGE_NUMBER_BUTTON).assertNone();
		}
	}

	@Test
	public void testVisualizationWithMultiPageTable() {

		final int pageSize = 2;

		Supplier<List<TestTableRow>> rowSupplier = new TestTableGeneratedRowSupplier(5);
		List<TestTableRow> rows = rowSupplier.get();
		IEmfDataTableDiv<TestTableRow> dataTableDiv = setNode(createDataTableDivBuilder(rowSupplier).setPageSize(pageSize).build());

		assertTrue(pageSize < rows.size());
		for (DomNodeTester navigation: getAndAssertOneNavigationElement()) {
			assertNavigationLayoutMultiPage(navigation, dataTableDiv);
			// expectation will be wrong for big test tables, i.e. when page selection buttons are displayed with
			// ellipsis (like "1 2 3 4 ... 999")
			int expectedPageCount = EmfDataTableTestUtil.calculateTotalPageCount(rows.size(), pageSize);
			navigation.findNodes(DomPageableTableMarker.NAVIGATION_PAGE_NUMBER_BUTTON).assertSize(expectedPageCount);
		}
	}

	@Test
	public void testVisualizationWithMultiPageTableAndLeadingEllipsis() {

		final int totalRowCount = 512;
		final int pageSize = 18;
		final int targetPageIndex = 0;
		final int expectedNumberOfEllipsis = 1;
		assertEllipsisRendering(totalRowCount, pageSize, targetPageIndex, expectedNumberOfEllipsis);
	}

	@Test
	public void testVisualizationWithMultiPageTableAndBothEllipsis() {

		final int totalRowCount = 512;
		final int pageSize = 18;
		final int targetPageIndex = EmfDataTableTestUtil.calculateTotalPageCount(totalRowCount, pageSize) / 2;
		final int expectedNumberOfEllipsis = 2;
		assertEllipsisRendering(totalRowCount, pageSize, targetPageIndex, expectedNumberOfEllipsis);
	}

	@Test
	public void testVisualizationWithMultiPageTableTailingEllipsis() {

		final int totalRowCount = 512;
		final int pageSize = 18;
		final int targetPageIndex = EmfDataTableTestUtil.calculateTotalPageCount(totalRowCount, pageSize);
		final int expectedNumberOfEllipsis = 1;
		assertEllipsisRendering(totalRowCount, pageSize, targetPageIndex, expectedNumberOfEllipsis);
	}

	private void assertEllipsisRendering(int totalRowCount, int pageSize, int targetPageIndex, int expectedNumberOfEllipsis) {

		EmfDataTableDivBuilder<TestTableRow> builder = createDataTableDivBuilder(new TestTableGeneratedRowSupplier(totalRowCount)).setPageSize(pageSize);
		IEmfDataTableDiv<TestTableRow> dataTableDiv = setNode(builder.build());
		dataTableDiv.setCurrentPage(targetPageIndex);
		for (DomNodeTester navigation: getAndAssertOneNavigationElement()) {
			assertNavigationLayoutMultiPage(navigation, dataTableDiv);
			navigation.findNodes(DomPageableTableMarker.NAVIGATION_PAGE_NUMBER_BUTTON).withText("...").assertSize(expectedNumberOfEllipsis);
		}
	}

	private List<DomNodeTester> getAndAssertOneNavigationElement() {

		return findNodes(DomPageableTableMarker.NAVIGATION).assertSize(1);
	}

	private DomNodeTester assertNavigationLayoutMultiPage(DomNodeTester navigation, IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		assertCommonNavigationButtonsExist(navigation);
		assertPageNavigationButtonsExist(navigation, true);
		assertCurrentPageSelectButtonIsDisplayed(navigation, dataTableDiv);
		return navigation;
	}

	private DomNodeTester assertNavigationLayoutSinglePage(DomNodeTester navigation) {

		assertCommonNavigationButtonsExist(navigation);
		assertPageNavigationButtonsExist(navigation, false);
		return navigation;
	}

	private void assertCommonNavigationButtonsExist(DomNodeTester navigation) {

		navigation.findNodes(DomPageableTableMarker.NAVIGATION_EXPORT_BUTTON).assertOne();
	}

	private void assertPageNavigationButtonsExist(DomNodeTester navigation, boolean existenceExpected) {

		navigation.findNodes(DomPageableTableMarker.NAVIGATION_PAGE_PREV_BUTTON).assertSize(existenceExpected? 1 : 0);
		navigation.findNodes(DomPageableTableMarker.NAVIGATION_PAGE_NEXT_BUTTON).assertSize(existenceExpected? 1 : 0);
	}

	private void assertCurrentPageSelectButtonIsDisplayed(DomNodeTester navigation, IEmfDataTableDiv<TestTableRow> dataTableDiv) {

		final String currentPageNumberString = dataTableDiv.getCurrentPage() + 1 + "";

		for (DomNodeTester buttonTester: navigation.findNodes(DomPageableTableMarker.NAVIGATION_PAGE_NUMBER_BUTTON).assertSome()) {
			if (buttonTester.containsText(currentPageNumberString)) {
				return;
			}
		}
		fail("Could not find page-select button for current page.");
	}
}
