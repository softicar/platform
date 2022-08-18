package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.emf.data.table.filter.string.EmfDataTableStringFilterNode;
import com.softicar.platform.emf.data.table.filter.string.EmfDataTableStringFilterType;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of a {@link String} column of a data table.
 * Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 * <p>
 * TODO: rename to "{@link EmfDataTableStringFilterNode}Test" and move next to
 * {@link EmfDataTableStringFilterNode}?
 * <p>
 * TODO: Currently there is no way to enter a null String. As soon as there is,
 * write a test here.
 * <p>
 * TODO: create more tests like this one
 * <p>
 * TODO: Reconsider if tests like this one (i.e. tests that validate the
 * rendering of values in the main table) cover correct cell content rendering
 * for all column types. If so, no detailed cell content tests are required in
 * {@link EmfDataTableBodyVisualizationTest}.
 * <p>
 * TODO: Case insensitivity is assured for filter input strings (and "contains"
 * semantics) but not for data rows, because all strings in constructed table
 * rows are lower case.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByStringTest extends AbstractEmfDataTableFilterTest {

	private final IDataTableColumn<TestTableRow, String> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setStringValue("foo"),
			new TestTableRow().setStringValue("bar"),
			new TestTableRow().setStringValue("baz"),
			new TestTableRow().setStringValue("zzz"),
			new TestTableRow().setStringValue("zzzasd"),
			new TestTableRow().setStringValue(""),
			new TestTableRow().setStringValue(null));

	public EmfDataTableFilterByStringTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getStringColumn();
	}

	@Test
	public void testFilteringWithContainsTextFilter() {

		applyFilter(EmfDataTableStringFilterType.CONTAINS_TEXT, "ba");

		assertRowsInColumnContainTexts(column, "bar", "baz");
	}

	@Test
	public void testFilteringWithContainsTextFilterIsCaseInsensitive() {

		applyFilter(EmfDataTableStringFilterType.CONTAINS_TEXT, "Ba");

		assertRowsInColumnContainTexts(column, "bar", "baz");
	}

	@Test
	public void testFilteringWithEmptyContainsTextFilter() {

		applyFilter(EmfDataTableStringFilterType.CONTAINS_TEXT, "");

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithContainsWordsFilter() {

		applyFilter(EmfDataTableStringFilterType.CONTAINS_WORDS, "zz as");

		assertRowsInColumnContainTexts(column, "zzzasd");
	}

	@Test
	public void testFilteringWithContainsWordsFilterIsCaseInsensitive() {

		applyFilter(EmfDataTableStringFilterType.CONTAINS_WORDS, "zz As");

		assertRowsInColumnContainTexts(column, "zzzasd");
	}

	@Test
	public void testFilteringWithEmptyContainsWordsFilter() {

		applyFilter(EmfDataTableStringFilterType.CONTAINS_WORDS, "");

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithEqualsTextFilter() {

		applyFilter(EmfDataTableStringFilterType.EQUALS_TEXT, "baz");

		assertRowsInColumnContainTexts(column, "baz");
	}

	@Test
	public void testFilteringWithEmptyEqualsTextFilter() {

		applyFilter(EmfDataTableStringFilterType.EQUALS_TEXT, "");

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithMatchesRegexpFilter() {

		applyFilter(EmfDataTableStringFilterType.MATCHES_REGEXP, "foo|ba.");

		assertRowsInColumnContainTexts(column, "foo", "bar", "baz");
	}

	@Test
	public void testFilteringWithEmptyMatchesRegexpFilter() {

		applyFilter(EmfDataTableStringFilterType.MATCHES_REGEXP, "");

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithEmptyFilter() {

		applyFilterWithoutText(EmfDataTableStringFilterType.IS_EMPTY);

		assertNumberOfTableBodyRows(2);
	}

	@Test
	public void testFilteringWithNotEmptyFilter() {

		applyFilterWithoutText(EmfDataTableStringFilterType.IS_NOT_EMPTY);

		assertNumberOfTableBodyRows(5);
	}

	private void applyFilter(EmfDataTableStringFilterType filterType, String filterString) {

		openFilterPopup(column);
		selectFilterType(filterType);
		findInput(EmfDataTableDivMarker.FILTER_INPUT_STRING).setInputValue(filterString);
		confirmFilterPopup();
	}

	private void applyFilterWithoutText(EmfDataTableStringFilterType filterType) {

		openFilterPopup(column);
		selectFilterType(filterType);
		confirmFilterPopup();
	}

	private void selectFilterType(EmfDataTableStringFilterType filterType) {

		IDomNodeIterable<EmfDataTableStringFilterNode<?>> filterNodes = CastUtils.cast(findNodes(EmfDataTableStringFilterNode.class));
		filterNodes.assertOne();
		EmfDataTableStringFilterNode<?> filterNode = filterNodes.iterator().next();
		filterNode.getFilterSelect().setSelectedType(filterType);
	}
}
