package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of an {@link Enum} column of a data table.
 * Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByEnumTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, TestEnum> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setTestEnumValue(TestEnum.FOO),
			new TestTableRow().setTestEnumValue(TestEnum.BAR),
			new TestTableRow().setTestEnumValue(TestEnum.BAR),
			new TestTableRow().setTestEnumValue(TestEnum.BAZ),
			new TestTableRow().setTestEnumValue(null));

	public EmfDataTableOrderByEnumTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getTestEnumColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			null,
			TestEnum.BAR.toString(),
			TestEnum.BAR.toString(),
			TestEnum.BAZ.toString(),
			TestEnum.FOO.toString());
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			TestEnum.FOO.toString(),
			TestEnum.BAZ.toString(),
			TestEnum.BAR.toString(),
			TestEnum.BAR.toString(),
			null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			TestEnum.FOO.toString(),
			TestEnum.BAR.toString(),
			TestEnum.BAR.toString(),
			TestEnum.BAZ.toString(),
			null);
	}
}
