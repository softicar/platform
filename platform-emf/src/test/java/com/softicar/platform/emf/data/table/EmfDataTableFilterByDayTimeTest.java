package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of a {@link DayTime} column of a data table.
 * Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByDayTimeTest extends AbstractEmfDataTableFilterByValueTest {

	private final IDataTableColumn<TestTableRow, DayTime> column;
	private final DayTime dayTime1 = DayTime.now().plusDays(1);
	private final DayTime dayTime2 = DayTime.now().plusDays(2);
	private final DayTime dayTime3 = DayTime.now().plusDays(3);
	private final DayTime dayTime4 = null;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setDayTimeValue(dayTime1),
			new TestTableRow().setDayTimeValue(dayTime2),
			new TestTableRow().setDayTimeValue(dayTime2),
			new TestTableRow().setDayTimeValue(dayTime3),
			new TestTableRow().setDayTimeValue(dayTime4));

	public EmfDataTableFilterByDayTimeTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getDayTimeColumn();
	}

	@Test
	public void testFilteringWithEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, dayTime2);

		assertRowsInColumnContainTexts(column, dayTime2.toString(), dayTime2.toString());
	}

	@Test
	public void testFilteringWithEmptyEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, dayTime2);

		assertRowsInColumnContainTexts(column, dayTime1.toString(), dayTime3.toString(), null);
	}

	@Test
	public void testFilteringWithEmptyNotEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.NOT_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, dayTime1);

		assertRowsInColumnContainTexts(column, dayTime2.toString(), dayTime2.toString(), dayTime3.toString());
	}

	@Test
	public void testFilteringWithEmptyGreaterFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, dayTime2);

		assertRowsInColumnContainTexts(column, dayTime2.toString(), dayTime2.toString(), dayTime3.toString());
	}

	@Test
	public void testFilteringWithEmptyGreaterEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.GREATER_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, dayTime2);

		assertRowsInColumnContainTexts(column, dayTime1.toString(), null);
	}

	@Test
	public void testFilteringWithEmptyLessFilter() {

		applyFilter(DataTableValueFilterOperator.LESS, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, dayTime1);

		assertRowsInColumnContainTexts(column, dayTime1.toString(), null);
	}

	@Test
	public void testFilteringWithEmptyLessEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.LESS_EQUAL, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	private void applyFilter(DataTableValueFilterOperator filterType, DayTime dayTime) {

		DomNodeTester popup = openFilterPopup(column);
		selectFilterType(filterType);
		popup.setDayTimeInputValue(EmfTestMarker.DATA_TABLE_FILTER_INPUT_VALUE, getDayString(dayTime), getTimeString(dayTime));
		confirmFilterPopup();
	}

	private String getDayString(DayTime dayTime) {

		return dayTime != null? dayTime.getDay().toString() : "";
	}

	private String getTimeString(DayTime dayTime) {

		return dayTime != null? dayTime.getTimeAsString() : "";
	}
}
