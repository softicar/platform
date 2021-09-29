package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.date.DayTime;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of a {@link DayTime} column of a data table.
 * Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByDayTimeTest extends AbstractEmfDataTableOrderTest {

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

	public EmfDataTableOrderByDayTimeTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getDayTimeColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			null,
			dayTime1.toString(),
			dayTime2.toString(),
			dayTime2.toString(),
			dayTime3.toString());
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			dayTime3.toString(),
			dayTime2.toString(),
			dayTime2.toString(),
			dayTime1.toString(),
			null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			dayTime1.toString(),
			dayTime2.toString(),
			dayTime2.toString(),
			dayTime3.toString(),
			null);
	}
}
