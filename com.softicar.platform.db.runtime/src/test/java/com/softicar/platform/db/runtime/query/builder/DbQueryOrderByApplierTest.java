package com.softicar.platform.db.runtime.query.builder;

import com.softicar.platform.db.runtime.query.sorters.IDbQuerySorter;
import com.softicar.platform.db.runtime.select.DbSqlFormatter;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class DbQueryOrderByApplierTest extends Assert {

	private final Builder builder;

	public DbQueryOrderByApplierTest() {

		this.builder = new Builder();
	}

	@Test
	public void testApply() {

		builder.setSorters(Collections.singletonList(new Sorter()));

		String sql = new DbSqlFormatter(builder.buildSelect()).format();
		assertEquals("SELECT f.id FROM db.foo f ORDER BY f.name, f.id", sql);
	}

	@Test
	public void testApplyWithEmptySorterList() {

		builder.setSorters(Collections.emptyList());

		String sql = new DbSqlFormatter(builder.buildSelect()).format();
		assertEquals("SELECT f.id FROM db.foo f ORDER BY f.id", sql);
	}

	@Test
	public void testApplyWithoutOriginalOrderBy() {

		builder.setSorters(Collections.singletonList(new Sorter()));
		builder.setSkipOrderBy(true);

		String sql = new DbSqlFormatter(builder.buildSelect()).format();
		assertEquals("SELECT f.id FROM db.foo f ORDER BY f.name", sql);
	}

	private static class Builder extends AbstractDbQuerySqlBuilder {

		private boolean skipOrderBy = false;

		public void setSkipOrderBy(boolean skipOrderBy) {

			this.skipOrderBy = skipOrderBy;
		}

		@Override
		protected void buildOriginalSelect() {

			SELECT();
			addIdentifier("f", "id");
			FROM();
			addIdentifier("db", "foo");
			addIdentifier("f");
			if (!skipOrderBy) {
				ORDER_BY();
				addIdentifier("f", "id");
			}
		}
	}

	private static class Sorter implements IDbQuerySorter {

		@Override
		public void buildExpression(IDbSqlBuilder builder) {

			builder.addIdentifier("f", "name");
		}
	}
}
