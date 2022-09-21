package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.sql.type.SqlFieldType;
import org.junit.Test;

public class DbFieldTypeTest extends AbstractTest {

	@Test
	public void testFieldType() {

		assertSame(SqlFieldType.BIG_DECIMAL, DbTestObject.BIG_DECIMAL_FIELD.getFieldType());
		assertSame(SqlFieldType.BOOLEAN, DbTestObject.BOOLEAN_FIELD.getFieldType());
		assertSame(SqlFieldType.BYTE_ARRAY, DbTestObject.BYTES_FIELD.getFieldType());
		assertSame(SqlFieldType.DAY, DbTestObject.DAY_FIELD.getFieldType());
		assertSame(SqlFieldType.DAY_TIME, DbTestObject.DAY_TIME_FIELD.getFieldType());
		assertSame(SqlFieldType.DOUBLE, DbTestObject.DOUBLE_FIELD.getFieldType());
		assertSame(SqlFieldType.ENUM, DbTestObject.ENUM_FIELD.getFieldType());
		assertSame(SqlFieldType.FLOAT, DbTestObject.FLOAT_FIELD.getFieldType());
		assertSame(SqlFieldType.INTEGER, DbTestObject.INTEGER_FIELD.getFieldType());
		assertSame(SqlFieldType.LONG, DbTestObject.LONG_FIELD.getFieldType());
		assertSame(SqlFieldType.STRING, DbTestObject.STRING_FIELD.getFieldType());

		// special key fields
		assertSame(SqlFieldType.INTEGER, DbTestObject.ID_FIELD.getFieldType());
		assertSame(SqlFieldType.INTEGER, DbTestObject.FOREIGN_FIELD.getFieldType());
	}
}
