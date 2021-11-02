package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.date.Day;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of a {@link Day} column of a data table. Uses
 * a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByDayTest extends AbstractEmfDataTableOrderTest {

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

	public EmfDataTableOrderByDayTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getDayColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			null,
			day1.toISOString(),
			day2.toISOString(),
			day2.toISOString(),
			day3.toISOString());
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			day3.toISOString(),
			day2.toISOString(),
			day2.toISOString(),
			day1.toISOString(),
			null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			day1.toISOString(),
			day2.toISOString(),
			day2.toISOString(),
			day3.toISOString(),
			null);
	}
}
