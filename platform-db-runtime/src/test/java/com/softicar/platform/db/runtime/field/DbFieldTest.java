package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.runtime.object.DbTestObject;
import java.util.List;
import org.junit.Test;

public class DbFieldTest extends AbstractTest {

	@Test
	public void testFieldsOfTestTable() {

		List<IDbField<DbTestObject, ?>> fields = DbTestObject.TABLE.getAllFields();
		assertEquals(14, fields.size());
		assertSame(DbTestObject.ID_FIELD, fields.get(0));
		assertSame(DbTestObject.BIG_DECIMAL_FIELD, fields.get(1));
		assertSame(DbTestObject.BOOLEAN_FIELD, fields.get(2));
		assertSame(DbTestObject.BYTES_FIELD, fields.get(3));
		assertSame(DbTestObject.DAY_FIELD, fields.get(4));
		assertSame(DbTestObject.DAY_TIME_FIELD, fields.get(5));
		assertSame(DbTestObject.DOUBLE_FIELD, fields.get(6));
		assertSame(DbTestObject.ENUM_FIELD, fields.get(7));
		assertSame(DbTestObject.FLOAT_FIELD, fields.get(8));
		assertSame(DbTestObject.FOREIGN_FIELD, fields.get(9));
		assertSame(DbTestObject.INTEGER_FIELD, fields.get(10));
		assertSame(DbTestObject.LONG_FIELD, fields.get(11));
		assertSame(DbTestObject.STRING_FIELD, fields.get(12));
		assertSame(DbTestObject.TIME_FIELD, fields.get(13));
	}

	@Test
	public void testFieldRegistering() {

		DbObjectTableBuilder<DbTestObject, DbTestObject> builder = new DbObjectTableBuilder<>("db", "foo", DbTestObject::new, DbTestObject.class);
		assertEquals(0, builder.getAllFields().size());

		builder.addIdField("id", null, null);
		assertEquals(1, builder.getControlFields().size());
		assertEquals(0, builder.getDataFields().size());
		assertEquals(1, builder.getAllFields().size());
		assertEquals(0, builder.getAllFields().get(0).getIndex());

		builder.addIntegerField("foo", null, null);
		assertEquals(1, builder.getControlFields().size());
		assertEquals(1, builder.getDataFields().size());
		assertEquals(2, builder.getAllFields().size());
		assertEquals(1, builder.getAllFields().get(1).getIndex());
	}
}
