package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of a {@link Boolean} column of a data table.
 * Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByBooleanTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, Boolean> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setBooleanValue(true),
			new TestTableRow().setBooleanValue(false),
			new TestTableRow().setBooleanValue(null),
			new TestTableRow().setBooleanValue(false),
			new TestTableRow().setBooleanValue(true));

	public EmfDataTableOrderByBooleanTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getBooleanColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(column);

		assertRowsInColumnContainCheckboxes(//
			column,
			null,
			false,
			false,
			true,
			true);
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainCheckboxes(//
			column,
			true,
			true,
			false,
			false,
			null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainCheckboxes(//
			column,
			true,
			false,
			null,
			false,
			true);
	}
}
