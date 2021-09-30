package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.entity.IEntity;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * UI test for ordering by values of an {@link IEntity} column of a data table.
 * Uses a non-database back-end.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableOrderByEntityTest extends AbstractEmfDataTableOrderTest {

	private final IDataTableColumn<TestTableRow, IEntity> column;
	private final TestEntity entity1 = new TestEntity(1);
	private final TestEntity entity2 = new TestEntity(2);
	private final TestEntity entity3 = new TestEntity(3);
	private final TestEntity entity4 = null;
	private final List<TestTableRow> rows = Arrays//
		.asList(
			new TestTableRow().setEntityValue(entity1),
			new TestTableRow().setEntityValue(entity3),
			new TestTableRow().setEntityValue(entity2),
			new TestTableRow().setEntityValue(entity4),
			new TestTableRow().setEntityValue(entity2));

	public EmfDataTableOrderByEntityTest() {

		setNodeSupplier(createDataTableDivBuilder(() -> rows)::build);
		column = getDataTable().getEntityColumn();
	}

	@Test
	public void testAscendingOrder() {

		clickOrderByButton(getDataTable().getEntityColumn());

		assertRowsInColumnContainTexts(//
			column,
			null,
			entity1.toDisplay().toString(),
			entity2.toDisplay().toString(),
			entity2.toDisplay().toString(),
			entity3.toDisplay().toString());
	}

	@Test
	public void testDescendingOrder() {

		clickOrderByButton(getDataTable().getEntityColumn());
		clickOrderByButton(getDataTable().getEntityColumn());

		assertRowsInColumnContainTexts(//
			column,
			entity3.toDisplay().toString(),
			entity2.toDisplay().toString(),
			entity2.toDisplay().toString(),
			entity1.toDisplay().toString(),
			null);
	}

	@Test
	public void testResetOrder() {

		clickOrderByButton(column);
		clickOrderByButton(column);
		clickOrderByButton(column);

		assertRowsInColumnContainTexts(//
			column,
			entity1.toDisplay().toString(),
			entity3.toDisplay().toString(),
			entity2.toDisplay().toString(),
			null,
			entity2.toDisplay().toString());
	}
}
