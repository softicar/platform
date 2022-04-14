package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.field.DbBaseField;
import com.softicar.platform.db.runtime.field.DbBigDecimalField;
import com.softicar.platform.db.runtime.field.DbByteArrayField;
import com.softicar.platform.db.runtime.field.DbDayTimeField;
import com.softicar.platform.db.runtime.field.DbEnumField;
import com.softicar.platform.db.runtime.field.DbForeignField;
import com.softicar.platform.db.runtime.field.DbIdField;
import com.softicar.platform.db.runtime.field.DbIntegerField;
import com.softicar.platform.db.runtime.field.DbLongField;
import com.softicar.platform.db.runtime.field.DbStringField;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.Test;
import org.mockito.Mockito;

public class DbRuntimeColumnStructureTest extends AbstractTest {

	@Test
	public void testAutoIncrement() {

		assertTrue(getStructure(createIdField()).isAutoIncrement());
		assertFalse(getStructure(createIntegerField()).isAutoIncrement());
	}

	@Test
	public void testBaseColumn() {

		assertTrue(getStructure(createBaseField()).isBaseColumn());
		assertFalse(getStructure(createIntegerField()).isBaseColumn());
	}

	@Test
	public void testBits() {

		assertBits(8, createIntegerField().setBits(8));
		assertBits(16, createIdField().setBits(16));
		assertBits(24, createForeignField(createIdField().setBits(24)));
		assertBits(64, createLongField().setBits(64));
	}

	@Test
	public void testCollation() {

		assertNull(getStructure(createStringField()).getCollation());
		assertEquals("abc", getStructure(createStringField().setCollation("abc")).getCollation());
	}

	@Test
	public void testComment() {

		assertNull(getStructure(createStringField()).getComment());
		assertEquals("abc", getStructure(createStringField().setComment("abc")).getComment());
	}

	@Test
	public void testDefaultValue() {

		assertNull(getStructure(createStringField()).getDefaultValue());
		assertEquals("abc", getStructure(createStringField().setDefault("abc")).getDefaultValue());
	}

	@Test
	public void testDefaultType() {

		assertEquals(DbColumnDefaultType.NONE, getStructure(createStringField()).getDefaultType());
		assertEquals(DbColumnDefaultType.NORMAL, getStructure(createStringField().setDefault("abc")).getDefaultType());
		assertEquals(DbColumnDefaultType.NULL, getStructure(createStringField().setDefault(null)).getDefaultType());
		assertEquals(DbColumnDefaultType.CURRENT_TIMESTAMP, getStructure(createDayTimeField().setDefaultNow()).getDefaultType());
	}

	@Test
	public void testEnumValues() {

		assertEquals(TestEnum.getValuesAsStrings(), getStructure(createEnumField(TestEnum.class)).getEnumValues());
	}

	@Test
	public void testFieldType() {

		assertFieldType(SqlFieldType.BIG_DECIMAL, DbTestObject.BIG_DECIMAL_FIELD);
		assertFieldType(SqlFieldType.BOOLEAN, DbTestObject.BOOLEAN_FIELD);
		assertFieldType(SqlFieldType.BYTE_ARRAY, DbTestObject.BYTES_FIELD);
		assertFieldType(SqlFieldType.DAY, DbTestObject.DAY_FIELD);
		assertFieldType(SqlFieldType.DAY_TIME, DbTestObject.DAY_TIME_FIELD);
		assertFieldType(SqlFieldType.DOUBLE, DbTestObject.DOUBLE_FIELD);
		assertFieldType(SqlFieldType.ENUM, DbTestObject.ENUM_FIELD);
		assertFieldType(SqlFieldType.FLOAT, DbTestObject.FLOAT_FIELD);
		assertFieldType(SqlFieldType.INTEGER, DbTestObject.ID_FIELD);
		assertFieldType(SqlFieldType.INTEGER, DbTestObject.INTEGER_FIELD);
		assertFieldType(SqlFieldType.INTEGER, DbTestObject.FOREIGN_FIELD);
		assertFieldType(SqlFieldType.LONG, DbTestObject.LONG_FIELD);
		assertFieldType(SqlFieldType.STRING, DbTestObject.STRING_FIELD);
		assertFieldType(SqlFieldType.TIME, DbTestObject.TIME_FIELD);
	}

	@Test
	public void testLengthBits() {

		assertLengthBits(0, createByteArrayField());
		assertLengthBits(8, createByteArrayField().setLengthBits(8));
		assertLengthBits(16, createByteArrayField().setLengthBits(16));
		assertLengthBits(24, createByteArrayField().setLengthBits(24));
		assertLengthBits(32, createByteArrayField().setLengthBits(32));
	}

	@Test
	public void testLogicalName() {

		DbIntegerField<DbTestObject> field1 = createIntegerField();
		DbIntegerField<DbTestObject> field2 = createIntegerField().setComment("@name=y@");

		assertEquals("x", getStructure(field1).getLogicalName());
		assertEquals("y", getStructure(field2).getLogicalName());
	}

	@Test
	public void testMaxLength() {

		assertMaxLength(0, createStringField());
		assertMaxLength(100, createStringField().setMaximumLength(100));
		assertMaxLength(123, createStringField().setMaximumLength(123));
	}

	@Test
	public void testNullable() {

		DbIntegerField<DbTestObject> field1 = createIntegerField();
		DbIntegerField<DbTestObject> field2 = createIntegerField().setNullable();

		assertFalse(getStructure(field1).isNullable());
		assertTrue(getStructure(field2).isNullable());
	}

	@Test
	public void testNameOrThrow() {

		DbIntegerField<DbTestObject> field1 = createIntegerField();
		DbIntegerField<DbTestObject> field2 = createIntegerField().setComment("@name=y@");

		assertEquals("x", getStructure(field1).getNameOrThrow());
		assertEquals("x", getStructure(field2).getNameOrThrow());
	}

	@Test
	public void testPrecision() {

		DbBigDecimalField<DbTestObject> field = createBigDecimalField().setSize(7, 5);

		assertEquals(7, getStructure(field).getPrecision());
	}

	@Test
	public void testOnUpdateNow() {

		DbDayTimeField<DbTestObject> field1 = createDayTimeField();
		DbDayTimeField<DbTestObject> field2 = createDayTimeField().setOnUpdateNow();

		assertFalse(getStructure(field1).isOnUpdateNow());
		assertTrue(getStructure(field2).isOnUpdateNow());
	}

	@Test
	public void testScale() {

		DbBigDecimalField<DbTestObject> field = createBigDecimalField().setSize(7, 5);

		assertEquals(5, getStructure(field).getScale());
	}

	@Test
	public void testTimestamp() {

		DbDayTimeField<DbTestObject> field1 = createDayTimeField();
		DbDayTimeField<DbTestObject> field2 = createDayTimeField().setTimestamp();

		assertFalse(getStructure(field1).isTimestamp());
		assertTrue(getStructure(field2).isTimestamp());
	}

	@Test
	public void testUnsigned() {

		DbIntegerField<DbTestObject> field1 = createIntegerField();
		DbIntegerField<DbTestObject> field2 = createIntegerField().setUnsigned();

		assertFalse(getStructure(field1).isUnsigned());
		assertTrue(getStructure(field2).isUnsigned());
	}

	// ------------------------------ assert methods ------------------------------ //

	private void assertBits(int expectedBits, IDbField<?, ?> field) {

		assertEquals(expectedBits, getStructure(field).getBits());
	}

	private void assertFieldType(SqlFieldType expectedFieldType, IDbField<?, ?> field) {

		assertEquals(expectedFieldType, getStructure(field).getFieldType());
	}

	private void assertLengthBits(int expectedLengthBits, IDbField<?, ?> field) {

		assertEquals(expectedLengthBits, getStructure(field).getLengthBits());
	}

	private void assertMaxLength(int expectedMaxLength, IDbField<?, ?> field) {

		assertEquals(expectedMaxLength, getStructure(field).getMaxLength());
	}

	// ------------------------------ field creation ------------------------------ //

	private DbBaseField<DbTestObject, DbTestObject, Integer> createBaseField() {

		return new DbBaseField<>(DbTestObject.BUILDER, "x", null, null, DbTestObject.TABLE).setComment("@base@");
	}

	private DbBigDecimalField<DbTestObject> createBigDecimalField() {

		return new DbBigDecimalField<>(DbTestObject.BUILDER, "x", null, null);
	}

	private DbByteArrayField<DbTestObject> createByteArrayField() {

		return new DbByteArrayField<>(DbTestObject.BUILDER, "x", null, null);
	}

	private <E extends Enum<E>> DbEnumField<DbTestObject, E> createEnumField(Class<E> enumClass) {

		return new DbEnumField<>(DbTestObject.BUILDER, "x", null, null, enumClass);
	}

	private DbDayTimeField<DbTestObject> createDayTimeField() {

		return new DbDayTimeField<>(DbTestObject.BUILDER, "x", null, null);
	}

	private DbIdField<DbTestObject> createIdField() {

		return new DbIdField<>(DbTestObject.BUILDER, "x", null, null);
	}

	private DbIntegerField<DbTestObject> createIntegerField() {

		return new DbIntegerField<>(DbTestObject.BUILDER, "x", null, null);
	}

	private DbForeignField<DbTestObject, ?> createForeignField(DbIdField<?> idField) {

		return new DbForeignField<>(DbTestObject.BUILDER, "x", null, null, idField);
	}

	private DbLongField<DbTestObject> createLongField() {

		return new DbLongField<>(DbTestObject.BUILDER, "x", null, null);
	}

	private DbStringField<DbTestObject> createStringField() {

		return new DbStringField<>(DbTestObject.BUILDER, "x", null, null);
	}

	// ------------------------------ utilities ------------------------------ //

	private IDbColumnStructure getStructure(IDbField<?, ?> idField) {

		IDbTableStructure tableStructure = Mockito.mock(IDbTableStructure.class);
		return new DbRuntimeColumnStructure(tableStructure, idField);
	}

	private static enum TestEnum {

		A,
		B,
		C;

		public static Collection<String> getValuesAsStrings() {

			return Arrays//
				.asList(values())
				.stream()
				.map(it -> it.toString())
				.collect(Collectors.toList());
		}
	}
}
