package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.object.DbTestObjectFlag;
import com.softicar.platform.db.sql.statement.builder.SqlSelectBuilder;
import org.junit.Assert;
import org.junit.Test;

public class DbEnumFieldTest extends Assert {

	@Test
	public void testInExpression() {

		SqlSelectBuilder builder = new SqlSelectBuilder(DbTestObject.TABLE);
		builder.getWhereBuilder().where(DbTestObject.ENUM_FIELD.isEqual(DbTestObjectFlag.SMALL));
		assertEquals(" WHERE (t.`enum` = ?)", builder.getWhereBuilder().getText());
	}
}
