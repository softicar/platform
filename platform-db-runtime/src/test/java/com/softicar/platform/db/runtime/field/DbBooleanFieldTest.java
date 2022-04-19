package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.sql.statement.builder.SqlSelectBuilder;
import org.junit.Test;

public class DbBooleanFieldTest extends AbstractTest {

	@Test
	public void testIsTrue() {

		SqlSelectBuilder builder = new SqlSelectBuilder(DbTestObject.TABLE);
		builder.getWhereBuilder().where(DbTestObject.BOOLEAN_FIELD.isTrue());
		assertEquals(" WHERE t.`boolean`", builder.getWhereBuilder().getText());
	}

	@Test
	public void testIsFalse() {

		SqlSelectBuilder builder = new SqlSelectBuilder(DbTestObject.TABLE);
		builder.getWhereBuilder().where(DbTestObject.BOOLEAN_FIELD.isFalse());
		assertEquals(" WHERE (NOT t.`boolean`)", builder.getWhereBuilder().getText());
	}
}
