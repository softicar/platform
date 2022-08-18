package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of a {@link Double} column of a data table.
 * Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByDoubleTest extends AbstractEmfDataTableFilterByValueTest {

	private final IDataTableColumn<TestTableRow, Double> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setDoubleValue(1.1d),
			new TestTableRow().setDoubleValue(2.2d),
			new TestTableRow().setDoubleValue(2.2d),
			new TestTableRow().setDoubleValue(3.3d),
			new TestTableRow().setDoubleValue(null));

	public EmfDataTableFilterByDoubleTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getDoubleColumn();
	}

	@Test
	public void testFilteringWithEqualsFilter() {

		applyFilter(DataTableValueFilterOperator.EQUAL, "2.2");

		assertRowsInColumnContainTexts(column, "2.2", "2.2");
	}

	protected void applyFilter(DataTableValueFilterOperator filterType, String filterString) {

		openFilterPopup(column);
		selectFilterType(filterType);
		findInput(EmfTestMarker.DATA_TABLE_FILTER_INPUT_VALUE).setInputValue(filterString != null? filterString : "");
		confirmFilterPopup();
	}
}
