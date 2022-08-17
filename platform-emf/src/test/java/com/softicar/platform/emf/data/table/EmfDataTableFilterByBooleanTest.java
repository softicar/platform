package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of a {@link Boolean} column of a data table.
 * Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByBooleanTest extends AbstractEmfDataTableFilterTest {

	private final IDataTableColumn<TestTableRow, Boolean> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setBooleanValue(true),
			new TestTableRow().setBooleanValue(false),
			new TestTableRow().setBooleanValue(null),
			new TestTableRow().setBooleanValue(false),
			new TestTableRow().setBooleanValue(true));

	public EmfDataTableFilterByBooleanTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		this.column = getDataTable().getBooleanColumn();
	}

	@Test
	public void testFilteringWithTrue() {

		applyFilter(true);

		assertRowsInColumnContainCheckboxes(//
			column,
			true,
			true);
	}

	@Test
	public void testFilteringWithFalse() {

		applyFilter(false);

		assertRowsInColumnContainCheckboxes(//
			column,
			false,
			false);
	}

	@Test
	public void testFilteringWithNull() {

		applyFilter(null);

		assertRowsInColumnContainCheckboxes(//
			column,
			true,
			false,
			null,
			false,
			true);
	}

	private void applyFilter(Boolean value) {

		openFilterPopup(column);
		findSelect(EmfTestMarker.DATA_TABLE_FILTER_INPUT_BOOLEAN).selectValue(value);
		confirmFilterPopup();
	}
}
