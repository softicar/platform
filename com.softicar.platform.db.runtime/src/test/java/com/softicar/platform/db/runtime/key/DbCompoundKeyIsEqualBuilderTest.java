package com.softicar.platform.db.runtime.key;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.record.DbTestRecord;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import org.junit.Test;

public class DbCompoundKeyIsEqualBuilderTest extends AbstractDbCompoundKeyTest {

	private final DbTableKey2<DbTestRecord, Integer, String> key;

	public DbCompoundKeyIsEqualBuilderTest() {

		this.key = new DbTableKey2<>(DbTestRecord.INDEX, DbTestRecord.NAME);
	}

	@Test
	public void test() {

		Tuple2<Integer, String> value = new Tuple2<>(22, "foo");
		ISqlBooleanExpression<DbTestRecord> expression = new DbCompoundKeyIsEqualBuilder<>(key, value).build();

		setAlias("t");
		build(expression);

		assertText("((t.`index` = ?) AND (t.`name` = ?))");
		assertParameters(22, "foo");
	}

	@Test
	public void testWithNullValues() {

		Tuple2<Integer, String> value = new Tuple2<>(33, null);
		ISqlBooleanExpression<DbTestRecord> expression = new DbCompoundKeyIsEqualBuilder<>(key, value).build();

		setAlias("t");
		build(expression);

		assertText("((t.`index` = ?) AND (t.`name` IS NULL))");
		assertParameters(33);
	}
}
