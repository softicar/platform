package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.filter.enums.EmfDataTableEnumFilterNode;
import com.softicar.platform.emf.data.table.filter.enums.EmfDataTableEnumFilterType;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of an {@link Enum} column of a data table.
 * Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByEnumTest extends AbstractEmfDataTableFilterTest {

	private final IDataTableColumn<TestTableRow, TestEnum> column;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setTestEnumValue(TestEnum.FOO),
			new TestTableRow().setTestEnumValue(TestEnum.BAR),
			new TestTableRow().setTestEnumValue(TestEnum.BAR),
			new TestTableRow().setTestEnumValue(TestEnum.BAZ),
			new TestTableRow().setTestEnumValue(null));

	public EmfDataTableFilterByEnumTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getTestEnumColumn();
	}

	@Test
	public void testFilteringWithEqualsFilter() {

		applyFilter(EmfDataTableEnumFilterType.IS, TestEnum.BAR);

		assertRowsInColumnContainTexts(column, TestEnum.BAR.toString(), TestEnum.BAR.toString());
	}

	@Test
	public void testFilteringWithEmptyEqualsFilter() {

		applyFilter(EmfDataTableEnumFilterType.IS, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithNotEqualsFilter() {

		applyFilter(EmfDataTableEnumFilterType.IS_NOT, TestEnum.BAR);

		assertRowsInColumnContainTexts(column, TestEnum.FOO.toString(), TestEnum.BAZ.toString(), null);
	}

	@Test
	public void testFilteringWithEmptyNotEqualsFilter() {

		applyFilter(EmfDataTableEnumFilterType.IS_NOT, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	private void applyFilter(EmfDataTableEnumFilterType filterType, TestEnum selection) {

		openFilterPopup(column);
		selectFilterType(filterType);
		findSelect(EmfTestMarker.DATA_TABLE_FILTER_INPUT_ENUM).selectValue(selection);
		confirmFilterPopup();
	}

	private void selectFilterType(EmfDataTableEnumFilterType filterType) {

		IDomNodeIterable<EmfDataTableEnumFilterNode<?, ?>> filterNodes = CastUtils.cast(findNodes(EmfDataTableEnumFilterNode.class));
		filterNodes.assertOne();
		EmfDataTableEnumFilterNode<?, ?> filterNode = filterNodes.iterator().next();
		filterNode.getFilterSelect().setSelectedType(filterType);
	}
}
