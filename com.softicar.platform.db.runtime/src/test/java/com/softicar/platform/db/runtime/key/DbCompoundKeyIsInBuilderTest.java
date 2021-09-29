package com.softicar.platform.db.runtime.key;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.record.DbTestRecord;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;

public class DbCompoundKeyIsInBuilderTest extends AbstractDbCompoundKeyTest {

	private final DbTableKey2<DbTestRecord, Integer, String> key;

	public DbCompoundKeyIsInBuilderTest() {

		this.key = new DbTableKey2<>(DbTestRecord.INDEX, DbTestRecord.NAME);
	}

	@Test
	public void test() {

		Collection<Tuple2<Integer, String>> values = Arrays
			.asList(//
				new Tuple2<>(7, "foo"),
				new Tuple2<>(13, "bar"),
				new Tuple2<>(31, "baz"));
		ISqlBooleanExpression<DbTestRecord> expression = new DbCompoundKeyIsInBuilder<>(key, values).build();

		setAlias("t");
		build(expression);

		assertText(
			new StringBuilder()//
				.append("(")
				.append("((t.`index` = ?) AND (t.`name` = ?))")
				.append(" OR ")
				.append("((t.`index` = ?) AND (t.`name` = ?))")
				.append(" OR ")
				.append("((t.`index` = ?) AND (t.`name` = ?))")
				.append(")")
				.toString());
		assertParameters(7, "foo", 13, "bar", 31, "baz");
	}

	@Test
	public void testWithEmptyCollection() {

		Collection<Tuple2<Integer, String>> values = Collections.emptyList();
		ISqlBooleanExpression<DbTestRecord> expression = new DbCompoundKeyIsInBuilder<>(key, values).build();

		setAlias("t");
		build(expression);

		assertText("FALSE");
		assertParameters();
	}
}
