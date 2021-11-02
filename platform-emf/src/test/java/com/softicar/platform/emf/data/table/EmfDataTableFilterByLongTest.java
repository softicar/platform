package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of a {@link Long} column of a data table.
 * Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByLongTest extends AbstractEmfDataTableFilterByValueTest {

	private final IDataTableColumn<TestTableRow, Long> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setLongValue(1l),
			new TestTableRow().setLongValue(2l),
			new TestTableRow().setLongValue(2l),
			new TestTableRow().setLongValue(3l),
			new TestTableRow().setLongValue(null));

	public EmfDataTableFilterByLongTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getLongColumn();
	}

	@Test
	public void testFilteringWithEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, "2");

		assertRowsInColumnContainTexts(column, "2", "2");
	}

	@Test
	public void testFilteringWithEmptyEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, "2");

		assertRowsInColumnContainTexts(column, "1", "3", null);
	}

	@Test
	public void testFilteringWithEmptyNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, "1");

		assertRowsInColumnContainTexts(column, "2", "2", "3");
	}

	@Test
	public void testFilteringWithEmptyGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, "2");

		assertRowsInColumnContainTexts(column, "2", "2", "3");
	}

	@Test
	public void testFilteringWithEmptyGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, "2");

		assertRowsInColumnContainTexts(column, "1", null);
	}

	@Test
	public void testFilteringWithEmptyLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, "1");

		assertRowsInColumnContainTexts(column, "1", null);
	}

	@Test
	public void testFilteringWithEmptyLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	private void applyFilter(DataTableValueFilterOperator filterType, String filterString) {

		openFilterPopup(column);
		selectFilterType(filterType);
		findInput(EmfDataTableDivMarker.FILTER_INPUT_VALUE).setInputValue(filterString != null? filterString : "");
		confirmFilterPopup();
	}
}
