package com.softicar.platform.db.runtime.table;

import com.softicar.platform.db.runtime.table.configuration.DbTableConfiguration;
import com.softicar.platform.db.runtime.table.configuration.IDbTableDataInitializer;
import com.softicar.platform.db.runtime.table.row.AbstractDbTableRow;
import com.softicar.platform.db.runtime.table.row.getter.IDbTableRowGetterStrategy;
import java.util.function.Consumer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DbTableTest extends Assert {

	private final TestTable table;
	private Consumer<DbTableConfiguration<TestObject, Integer>> configurationCustomizer;

	public DbTableTest() {

		this.table = new TestTable();
		this.configurationCustomizer = configuration -> {
			/* nothing by default */ };
	}

	@Test
	public void testGetConfiguration() {

		IDbTableDataInitializer dataInitializer = Mockito.mock(IDbTableDataInitializer.class);
		IDbTableRowGetterStrategy<TestObject, Integer> getterStrategy = Mockito.mock(IDbTableRowGetterStrategy.class);

		configurationCustomizer = configuration -> {
			configuration.setDataInitializer(dataInitializer);
			configuration.setTableRowGetterStrategy(getterStrategy);
		};

		assertSame(dataInitializer, table.getConfiguration().getDataInitializer());
		assertSame(getterStrategy, table.getConfiguration().getTableRowGetterStrategy());
	}

	private class TestTable extends DbTable<TestObject, Integer> {

		public TestTable() {

			super(new DbTableBuilder<>("database", "table", TestObject::new, TestObject.class));
		}

		@Override
		protected void customizeTableConfiguration(DbTableConfiguration<TestObject, Integer> configuration) {

			configurationCustomizer.accept(configuration);
		}
	}

	private static class TestObject extends AbstractDbTableRow<TestObject, Integer> {

		@Override
		public IDbTable<TestObject, Integer> table() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean saveIfNecessary() {

			throw new UnsupportedOperationException();
		}
	}
}
