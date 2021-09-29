package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of an {@link Integer} column of a data table.
 * Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByIntegerTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, Integer> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setIntegerValue(1),
			new TestTableRow().setIntegerValue(3),
			new TestTableRow().setIntegerValue(2),
			new TestTableRow().setIntegerValue(null),
			new TestTableRow().setIntegerValue(2));

	public EmfDataTableOrderByIntegerTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getIntegerColumn();
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
