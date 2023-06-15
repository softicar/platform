package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public class AbstractInMemoryDataTableTest extends AbstractTest {

	private final Set<String> rows = new TreeSet<>();

	@Test
	public void testList() {

		Table table = new Table();

		rows.add("A");
		rows.add("B");
		rows.add("C");

		assertEquals(Arrays.asList("A", "B", "C"), table.list());
		assertEquals(Arrays.asList("A", "B"), table.list(0, 2));
		assertEquals(Arrays.asList("B"), table.list(1, 1));
		assertEquals(Arrays.asList("B", "C"), table.list(1, 0));
	}

	@Test
	public void testInvalidateCaches() {

		// establish table and fill cache
		Table table = new Table();
		rows.add("A");
		table.list();

		// add more rows and invalidate cache
		rows.add("B");
		table.invalidateCaches();

		assertEquals(2, table.count());
		assertEquals(Arrays.asList("A", "B"), table.list());
	}

	private class Table extends AbstractInMemoryDataTable<String> {

		public Table() {

			newColumn(String.class)//
				.setGetter(it -> it)
				.setTitle(IDisplayString.create("Foo"))
				.addColumn();
			newCollectionColumn(String.class)//
				.setGetter(it -> List.of(it))
				.setTitle(IDisplayString.create("Bar"))
				.addColumn();
			newSetColumn(String.class)//
				.setGetter(it -> Set.of(it))
				.setTitle(IDisplayString.create("Baz"))
				.addColumn();
		}

		@Override
		public DataTableIdentifier getIdentifier() {

			return DataTableIdentifier.empty();
		}

		@Override
		protected Iterable<String> getTableRows() {

			return rows;
		}
	}
}
