package com.softicar.platform.db.sql.fields;

import static org.mockito.Mockito.mock;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import org.junit.Test;

public class SqlBooleanFieldTest extends AbstractSqlBuildableTest {

	private static interface Table {
		// nothing to add
	}

	private static final ISqlTable<Table> TABLE = CastUtils.cast(mock(ISqlTable.class));
	private static final ISqlBooleanField<Table> FOO = new SqlBooleanTestField<>(TABLE, "foo", 42);
	private static final ISqlBooleanField<Table> BAR = new SqlBooleanTestField<>(TABLE, "bar", 1337);

	// ---------------------------------------- BOOLEAN EXPRESSION ---------------------------------------- //

	@Test
	public void not() {

		build(FOO.not());

		assertText("(NOT .`foo`)");
	}

	@Test
	public void and() {

		build(FOO.and(BAR));

		assertText("(.`foo` AND .`bar`)");
	}

	@Test
	public void or() {

		build(FOO.or(BAR));

		assertText("(.`foo` OR .`bar`)");
	}
}
