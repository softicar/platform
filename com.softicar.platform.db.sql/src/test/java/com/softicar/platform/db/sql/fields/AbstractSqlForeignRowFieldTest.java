package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;
import org.junit.Test;

public class AbstractSqlForeignRowFieldTest extends AbstractSqlBuildableTest {

	private static final int ROW_ID = 31;
	private final SqlTestEntity row;
	private final SqlForeignRowTestField<SqlTestEntity, SqlTestEntity, Integer> field1;
	private final SqlForeignRowTestField<SqlTestEntity, SqlTestEntity, Integer> field2;

	public AbstractSqlForeignRowFieldTest() {

		this.row = new SqlTestEntity(ROW_ID);
		this.field1 = new SqlForeignRowTestField<>(SqlTestEntity.TABLE, SqlTestEntity.ID, "f1", 1);
		this.field2 = new SqlForeignRowTestField<>(SqlTestEntity.TABLE, SqlTestEntity.ID, "f2", 2);

		setAlias("t");
	}

	@Test
	public void testToString() {

		assertEquals("f1", field1.toString());
		assertEquals("f2", field2.toString());
	}

	@Test
	public void testGetFieldType() {

		assertEquals(SqlFieldType.INTEGER, field1.getFieldType());
		assertEquals(SqlFieldType.INTEGER, field1.getFieldType());
	}

	@Test
	public void testGetValueType() {

		ISqlValueType<SqlTestEntity> valueType = field1.getValueType();

		assertEquals(1, valueType.getColumnCount());
		assertEquals(SqlTestEntity.class, valueType.getValueClass());
	}

	// -------------------- isNull() and isNotNull() -------------------- //

	@Test
	public void testIsNull() {

		assertSql(field1.isNull(), "(t.`f1` IS NULL)");
	}

	@Test
	public void testIsNotNull() {

		assertSql(field1.isNotNull(), "(t.`f1` IS NOT NULL)");
	}

	// -------------------- isEqual(row) -------------------- //

	@Test
	public void testIsEqualWithTableRow() {

		assertSql(field1.isEqual(row), "(t.`f1` = ?)", ROW_ID);
	}

	@Test
	public void testIsEqualWithNullRow() {

		assertSql(field1.isEqual((SqlTestEntity) null), "(t.`f1` IS NULL)");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsEqualWithNullPk() {

		assertSql(field1.isEqual(new SqlTestEntity(null)), "");
	}

	// -------------------- isNotEqual(row) -------------------- //

	@Test
	public void testIsNotEqualWithTableRow() {

		assertSql(field1.isNotEqual(row), "(t.`f1` != ?)", ROW_ID);
	}

	@Test
	public void testIsNotEqualWithNullRow() {

		assertSql(field1.isNotEqual((SqlTestEntity) null), "(t.`f1` IS NOT NULL)");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsNotEqualWithNullPk() {

		assertSql(field1.isNotEqual(new SqlTestEntity(null)), "");
	}

	// -------------------- isEqual(field) and isNotEqual(field) -------------------- //

	@Test
	public void testIsEqualWithField() {

		assertSql(field1.isEqual(field2), "(t.`f1` = t.`f2`)");
	}

	@Test
	public void testIsNotEqualWithField() {

		assertSql(field1.isNotEqual(field2), "(t.`f1` != t.`f2`)");
	}

	// -------------------- isEqualToTargetField() and isNotEqualToTargetField() -------------------- //

	@Test
	public void testIsEqualToTargetField() {

		assertSql(field1.isEqualToTargetField(), "(t.`f1` = t.`id`)");
	}

	@Test
	public void testIsNotEqualTargetField() {

		assertSql(field1.isNotEqualToTargetField(), "(t.`f1` != t.`id`)");
	}

	// -------------------- isEqualPk() -------------------- //

	@Test
	public void testIsEqualPk() {

		assertSql(field1.isEqualPk(ROW_ID), "(t.`f1` = ?)", ROW_ID);
	}

	@Test
	public void testIsEqualPkWithNull() {

		assertSql(field1.isEqualPk(null), "(t.`f1` IS NULL)");
	}

	// -------------------- isNotEqualPk() -------------------- //

	@Test
	public void testIsNotEqualPk() {

		assertSql(field1.isNotEqualPk(ROW_ID), "(t.`f1` != ?)", ROW_ID);
	}

	@Test
	public void testIsNotEqualPkWithNull() {

		assertSql(field1.isNotEqualPk(null), "(t.`f1` IS NOT NULL)");
	}

	// -------------------- isInPks() -------------------- //

	@Test
	public void testIsInPks() {

		assertSql(field1.isInPks(3, 23, 7), "t.`f1` IN (?,?,?)", 3, 23, 7);
	}

	@Test
	public void testIsInPksWithEmptyList() {

		assertSql(field1.isInPks(), "FALSE");
	}

	// -------------------- isNotInPks() -------------------- //

	@Test
	public void testIsNotInPks() {

		assertSql(field1.isNotInPks(3, 37, 31), "t.`f1` NOT IN (?,?,?)", 3, 37, 31);
	}

	@Test
	public void testIsNotInPksWithEmptyList() {

		assertSql(field1.isNotInPks(), "TRUE");
	}

	// -------------------- private -------------------- //

	private void assertSql(ISqlClauseBuildable buildable, String expectedText, Object...expectedParameters) {

		build(buildable);
		assertText(expectedText);
		assertParameters(expectedParameters);
	}
}
