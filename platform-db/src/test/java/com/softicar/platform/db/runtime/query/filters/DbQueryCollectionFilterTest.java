package com.softicar.platform.db.runtime.query.filters;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.select.DbSqlBuilder;
import com.softicar.platform.db.runtime.select.DbSqlFormatter;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DbQueryCollectionFilterTest extends Assert {

	private final IDbQueryColumn<?, String> column;
	private final IDbSqlBuilder builder;

	public DbQueryCollectionFilterTest() {

		this.column = Mockito.mock(IDbQueryColumn.class);
		this.builder = new DbSqlBuilder().WHERE();

		Mockito.when(column.getName()).thenReturn("column");
	}

	@Test
	public void testIn() {

		new DbQueryCollectionFilter<>(column, DataTableCollectionFilterOperator.IN, List.of("foo", "bar")).buildCondition(builder);

		assertSelect("WHERE `column` IN (?, ?)", "foo", "bar");
	}

	@Test
	public void testInWithEmptyValueList() {

		new DbQueryCollectionFilter<>(column, DataTableCollectionFilterOperator.IN, List.of()).buildCondition(builder);

		assertSelect("WHERE FALSE");
	}

	@Test
	public void testNotIn() {

		new DbQueryCollectionFilter<>(column, DataTableCollectionFilterOperator.NOT_IN, List.of("foo", "bar")).buildCondition(builder);

		assertSelect("WHERE `column` NOT IN (?, ?)", "foo", "bar");
	}

	@Test
	public void testNotInWithEmptyValueList() {

		new DbQueryCollectionFilter<>(column, DataTableCollectionFilterOperator.NOT_IN, List.of()).buildCondition(builder);

		assertSelect("WHERE TRUE");
	}

	private void assertSelect(String expectedSelect, String...expectedParameters) {

		assertEquals(expectedSelect, new DbSqlFormatter(builder.getSelect()).format());
		assertEquals(List.of(expectedParameters), builder.getSelect().getParameters());
	}
}
