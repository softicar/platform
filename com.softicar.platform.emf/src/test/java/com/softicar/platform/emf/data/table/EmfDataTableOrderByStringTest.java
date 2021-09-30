package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of a {@link String} column of a data table.
 * Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByStringTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, String> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setStringValue("aaa"),
			new TestTableRow().setStringValue("ccc"),
			new TestTableRow().setStringValue("bbb"),
			new TestTableRow().setStringValue(null),
			new TestTableRow().setStringValue("bbb"));

	public EmfDataTableOrderByStringTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getStringColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(getDataTable().getStringColumn());

		assertRowsInColumnContainTexts(column, null, "aaa", "bbb", "bbb", "ccc");
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(getDataTable().getStringColumn());
		clickOrderByButton(getDataTable().getStringColumn());

		assertRowsInColumnContainTexts(column, "ccc", "bbb", "bbb", "aaa", null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			"aaa",
			"ccc",
			"bbb",
			null,
			"bbb");
	}
}
