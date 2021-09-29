package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of a {@link BigDecimal} column of a data
 * table. Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByBigDecimalTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, BigDecimal> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setBigDecimalValue(new BigDecimal("1.1")),
			new TestTableRow().setBigDecimalValue(new BigDecimal("3.3")),
			new TestTableRow().setBigDecimalValue(new BigDecimal("2.2")),
			new TestTableRow().setBigDecimalValue(null),
			new TestTableRow().setBigDecimalValue(new BigDecimal("2.2")));

	public EmfDataTableOrderByBigDecimalTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getBigDecimalColumn();
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

		assertRowsInColumnContainTexts(column, "1.1", "3.3", "2.2", null, "2.2");
	}
}
