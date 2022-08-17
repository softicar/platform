package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.emf.EmfTestMarker;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of a {@link BigDecimal} column of a data
 * table. Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByBigDecimalTest extends AbstractEmfDataTableFilterByValueTest {

	private final IDataTableColumn<TestTableRow, BigDecimal> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setBigDecimalValue(new BigDecimal("1.1")),
			new TestTableRow().setBigDecimalValue(new BigDecimal("2.2")),
			new TestTableRow().setBigDecimalValue(new BigDecimal("2.2")),
			new TestTableRow().setBigDecimalValue(new BigDecimal("3.3")),
			new TestTableRow().setBigDecimalValue(null));

	public EmfDataTableFilterByBigDecimalTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getBigDecimalColumn();
	}

	@Test
	public void testFilteringWithEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, "2.2");

		assertRowsInColumnContainTexts(column, "2.2", "2.2");
	}

	@Test
	public void testFilteringWithEmptyEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, "2.2");

		assertRowsInColumnContainTexts(column, "1.1", "3.3", null);
	}

	@Test
	public void testFilteringWithEmptyNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, "1.1");

		assertRowsInColumnContainTexts(column, "2.2", "2.2", "3.3");
	}

	@Test
	public void testFilteringWithEmptyGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, "2.2");

		assertRowsInColumnContainTexts(column, "2.2", "2.2", "3.3");
	}

	@Test
	public void testFilteringWithEmptyGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, "2.2");

		assertRowsInColumnContainTexts(column, "1.1", null);
	}

	@Test
	public void testFilteringWithEmptyLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, "1.1");

		assertRowsInColumnContainTexts(column, "1.1", null);
	}

	@Test
	public void testFilteringWithEmptyLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	private void applyFilter(DataTableValueFilterOperator filterType, String filterString) {

		openFilterPopup(column);
		selectFilterType(filterType);
		findInput(EmfTestMarker.DATA_TABLE_FILTER_INPUT_VALUE).setInputValue(filterString != null? filterString : "");
		confirmFilterPopup();
	}
}
