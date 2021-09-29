package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of a {@link Long} column of a data table. Uses
 * a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByLongTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, Long> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setLongValue(1l),
			new TestTableRow().setLongValue(3l),
			new TestTableRow().setLongValue(2l),
			new TestTableRow().setLongValue(null),
			new TestTableRow().setLongValue(2l));

	public EmfDataTableOrderByLongTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getLongColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(column);

		assertRowsInColumnContainTexts(column, null, "1", "2", "2", "3");
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(column, "3", "2", "2", "1", null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			"1",
			"3",
			"2",
			null,
			"2");
	}
}
