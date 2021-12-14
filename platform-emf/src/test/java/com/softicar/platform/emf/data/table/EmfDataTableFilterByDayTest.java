package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of a {@link Day} column of a data table. Uses
 * a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByDayTest extends AbstractEmfDataTableFilterByValueTest {

	private final IDataTableColumn<TestTableRow, Day> column;
	private final Day day1 = Day.today().getRelative(1);
	private final Day day2 = Day.today().getRelative(2);
	private final Day day3 = Day.today().getRelative(3);
	private final Day day4 = null;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setDayValue(day1),
			new TestTableRow().setDayValue(day2),
			new TestTableRow().setDayValue(day2),
			new TestTableRow().setDayValue(day3),
			new TestTableRow().setDayValue(day4));

	public EmfDataTableFilterByDayTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getDayColumn();
	}

	@Test
	public void testFilteringWithEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, day2.toISOString());

		assertRowsInColumnContainTexts(column, day2.toISOString(), day2.toISOString());
	}

	@Test
	public void testFilteringWithEmptyEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, day2.toISOString());

		assertRowsInColumnContainTexts(column, day1.toISOString(), day3.toISOString(), null);
	}

	@Test
	public void testFilteringWithEmptyNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, day1.toISOString());

		assertRowsInColumnContainTexts(column, day2.toISOString(), day2.toISOString(), day3.toISOString());
	}

	@Test
	public void testFilteringWithEmptyGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, day2.toISOString());

		assertRowsInColumnContainTexts(column, day2.toISOString(), day2.toISOString(), day3.toISOString());
	}

	@Test
	public void testFilteringWithEmptyGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, day2.toISOString());

		assertRowsInColumnContainTexts(column, day1.toISOString(), null);
	}

	@Test
	public void testFilteringWithEmptyLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, day1.toISOString());

		assertRowsInColumnContainTexts(column, day1.toISOString(), null);
	}

	@Test
	public void testFilteringWithEmptyLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	private void applyFilter(DataTableValueFilterOperator filterType, String filterString) {

		DomNodeTester popup = openFilterPopup(column);
		selectFilterType(filterType);
		popup.setInputValue(EmfDataTableDivMarker.FILTER_INPUT_VALUE, filterString != null? filterString : "");
		confirmFilterPopup();
	}
}
