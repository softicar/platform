package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of a {@link Double} column of a data table.
 * Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByDoubleTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, Double> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setDoubleValue(1.1d),
			new TestTableRow().setDoubleValue(3.3d),
			new TestTableRow().setDoubleValue(2.2d),
			new TestTableRow().setDoubleValue(null),
			new TestTableRow().setDoubleValue(2.2d));

	public EmfDataTableOrderByDoubleTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getDoubleColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(column);

		assertRowsInColumnContainTexts(column, null, "1.1", "2.2", "2.2", "3.3");
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(column, "3.3", "2.2", "2.2", "1.1", null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			"1.1",
			"3.3",
			"2.2",
			null,
			"2.2");
	}
}
