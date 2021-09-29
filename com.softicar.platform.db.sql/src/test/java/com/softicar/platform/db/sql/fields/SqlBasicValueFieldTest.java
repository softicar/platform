package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.field.ISqlValueField;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import org.junit.Before;
import org.junit.Test;

public class SqlBasicValueFieldTest extends AbstractSqlBuildableTest {

	private static final ISqlValueField<SqlTestEntity, Integer> FIELD = SqlTestEntity.INTEGER;
	private static final Integer VALUE = 1337;

	@Before
	public void before() {

		setAlias("foo");
	}

	@Test
	public void testToString() {

		assertEquals(FIELD.getName(), FIELD.toString());
	}

	@Test
	public void testIsEqual() {

		buildsCorrectly(FIELD.isEqual(VALUE), "=");
	}

	@Test
	public void testIsNotEqual() {

		buildsCorrectly(FIELD.isNotEqual(VALUE), "!=");
	}

	@Test
	public void testIsLess() {

		buildsCorrectly(FIELD.isLess(VALUE), "<");
	}

	@Test
	public void testIsLessEqual() {

		buildsCorrectly(FIELD.isLessEqual(VALUE), "<=");
	}

	@Test
	public void testIsGreater() {

		buildsCorrectly(FIELD.isGreater(VALUE), ">");
	}

	@Test
	public void testIsGreaterEqual() {

		buildsCorrectly(FIELD.isGreaterEqual(VALUE), ">=");
	}

	@Test
	public void testIsEqualWithNull() {

		buildsCorrectNullTest(FIELD.isEqual((Integer) null), "IS NULL");
	}

	@Test
	public void testIsNotEqualWithNull() {

		buildsCorrectNullTest(FIELD.isNotEqual((Integer) null), "IS NOT NULL");
	}

	@Test
	public void testIsNull() {

		buildsCorrectNullTest(FIELD.isNull(), "IS NULL");
	}

	@Test
	public void testIsNotNull() {

		buildsCorrectNullTest(FIELD.isNotNull(), "IS NOT NULL");
	}

	private void buildsCorrectly(ISqlClauseBuildable buildable, String operation) {

		build(buildable);

		assertParameters(VALUE);
		assertText("(foo.`%s` %s ?)", FIELD, operation);
	}

	private void buildsCorrectNullTest(ISqlClauseBuildable buildable, String operation) {

		build(buildable);

		assertParameters();
		assertText("(foo.`%s` %s)", FIELD, operation);
	}
}
