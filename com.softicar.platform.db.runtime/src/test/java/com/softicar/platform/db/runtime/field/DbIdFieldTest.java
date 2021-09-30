package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.sql.statement.builder.SqlSelectBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Arrays;
import org.junit.Test;

public class DbIdFieldTest extends AbstractDbTest {

	private final SqlSelectBuilder builder;
	private final SqlWhereClauseBuilder whereBuilder;

	public DbIdFieldTest() {

		this.builder = new SqlSelectBuilder(DbTestObject.TABLE);
		this.whereBuilder = builder.getWhereBuilder();
	}

	@Test
	public void testGetFieldType() {

		assertEquals(SqlFieldType.INTEGER, DbTestObject.ID_FIELD.getFieldType());
	}

	@Test
	public void testGetValueType() {

		ISqlValueType<Integer> valueType = DbTestObject.ID_FIELD.getValueType();

		assertEquals(1, valueType.getColumnCount());
		assertEquals(Integer.class, valueType.getValueClass());
	}

	// ------------------------------ isEqualToRow ------------------------------ //

	@Test
	public void testIsEqualToRow() {

		DbTestObject object = new DbTestObject().save();

		whereBuilder.where(DbTestObject.ID_FIELD.isEqual(object));

		assertEquals(" WHERE (t.`id` = ?)", whereBuilder.getText());
		assertEquals(Arrays.asList(object.getId()), whereBuilder.getParameters());
	}

	@Test
	public void testIsEqualToRowWithNullRow() {

		DbTestObject object = null;

		whereBuilder.where(DbTestObject.ID_FIELD.isEqual(object));

		assertEquals(" WHERE (t.`id` IS NULL)", whereBuilder.getText());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsEqualToRowWithNonPersistentRow() {

		DbTestObject object = new DbTestObject();

		whereBuilder.where(DbTestObject.ID_FIELD.isEqual(object));
	}

	// ------------------------------ isNotEqualToRow ------------------------------ //

	@Test
	public void testIsNotEqualToRow() {

		DbTestObject object = new DbTestObject().save();

		whereBuilder.where(DbTestObject.ID_FIELD.isNotEqual(object));

		assertEquals(" WHERE (t.`id` != ?)", whereBuilder.getText());
		assertEquals(Arrays.asList(object.getId()), whereBuilder.getParameters());
	}

	@Test
	public void testIsNotEqualToRowWithNullRow() {

		DbTestObject object = null;

		whereBuilder.where(DbTestObject.ID_FIELD.isNotEqual(object));

		assertEquals(" WHERE (t.`id` IS NOT NULL)", whereBuilder.getText());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsNotEqualToRowWithNonPersistentRow() {

		DbTestObject object = new DbTestObject();

		whereBuilder.where(DbTestObject.ID_FIELD.isNotEqual(object));
	}
}
