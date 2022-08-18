package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.emf.data.table.filter.entity.EmfDataTableEntityFilterNode;
import com.softicar.platform.emf.data.table.filter.entity.EmfDataTableEntityFilterType;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for filtering by values of an {@link IEntity} column of a data table.
 * Uses a non-database back-end.
 * <p>
 * TODO: test filter popup reset (change -> cancel -> reopen)
 * <p>
 * TODO: consider IDs...
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableFilterByEntityTest extends AbstractEmfDataTableFilterTest {

	private final IDataTableColumn<TestTableRow, IEntity> column;
	private final TestEntity entity1 = new TestEntity(1);
	private final TestEntity entity2 = new TestEntity(2);
	private final TestEntity entity3 = new TestEntity(3);
	private final TestEntity entity4 = null;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setEntityValue(entity1),
			new TestTableRow().setEntityValue(entity2),
			new TestTableRow().setEntityValue(entity2),
			new TestTableRow().setEntityValue(entity3),
			new TestTableRow().setEntityValue(entity4));

	public EmfDataTableFilterByEntityTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getEntityColumn();
	}

	@Test
	public void testFilteringWithEqualsFilter() {

		applyFilter(EmfDataTableEntityFilterType.IS, entity2);

		assertRowsInColumnContainTexts(column, entity2.toDisplay().toString(), entity2.toDisplay().toString());
	}

	@Test
	public void testFilteringWithEmptyEqualsFilter() {

		applyFilter(EmfDataTableEntityFilterType.IS, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithNotEqualsFilter() {

		applyFilter(EmfDataTableEntityFilterType.IS_NOT, entity2);

		assertRowsInColumnContainTexts(column, entity1.toDisplay().toString(), entity3.toDisplay().toString(), null);
	}

	@Test
	public void testFilteringWithEmptyNotEqualsFilter() {

		applyFilter(EmfDataTableEntityFilterType.IS_NOT, null);

		assertNumberOfTableBodyRows(rows.size());
	}

	@Test
	public void testFilteringWithEmptyFilter() {

		applyFilterWithoutValue(EmfDataTableEntityFilterType.IS_EMPTY);

		assertNumberOfTableBodyRows(1);
	}

	@Test
	public void testFilteringWithNotEmptyFilter() {

		applyFilterWithoutValue(EmfDataTableEntityFilterType.IS_NOT_EMPTY);

		assertNumberOfTableBodyRows(4);
	}

	private void applyFilter(EmfDataTableEntityFilterType filterType, TestEntity item) {

		openFilterPopup(column);
		selectFilterType(filterType);
		findAutoCompleteInput(EmfDataTableDivMarker.FILTER_INPUT_ENTITY).selectValue(item);
		confirmFilterPopup();
	}

	private void applyFilterWithoutValue(EmfDataTableEntityFilterType filterType) {

		openFilterPopup(column);
		selectFilterType(filterType);
		confirmFilterPopup();
	}

	private void selectFilterType(EmfDataTableEntityFilterType filterType) {

		IDomNodeIterable<EmfDataTableEntityFilterNode<?, ?>> filterNodes = CastUtils.cast(findNodes(EmfDataTableEntityFilterNode.class));
		filterNodes.assertOne();
		EmfDataTableEntityFilterNode<?, ?> filterNode = filterNodes.iterator().next();
		filterNode.getFilterSelect().setSelectedType(filterType);
	}
}
