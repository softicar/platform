package com.softicar.platform.db.structure.mysql.column;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import org.junit.Test;

public class InternalDbMysqlColumnFieldTypeDeterminerTest {

	private static final DbTableName TABLE_NAME = new DbTableName("db", "tab");

	@Test
	public void testBinaryTypes() {

		assertType(SqlFieldType.BYTE_ARRAY, "`foo` TINYBLOB");
		assertType(SqlFieldType.BYTE_ARRAY, "`foo` BLOB");
		assertType(SqlFieldType.BYTE_ARRAY, "`foo` MEDIUMBLOB");
		assertType(SqlFieldType.BYTE_ARRAY, "`foo` LONGBLOB");
		assertType(SqlFieldType.BYTE_ARRAY, "`foo` BINARY");
		assertType(SqlFieldType.BYTE_ARRAY, "`foo` VARBINARY");
	}

	@Test
	public void testTextualTypes() {

		assertType(SqlFieldType.STRING, "`foo` TINYTEXT");
		assertType(SqlFieldType.STRING, "`foo` TEXT");
		assertType(SqlFieldType.STRING, "`foo` MEDIUMTEXT");
		assertType(SqlFieldType.STRING, "`foo` LONGTEXT");
		assertType(SqlFieldType.STRING, "`foo` CHAR");
		assertType(SqlFieldType.STRING, "`foo` VARCHAR");
	}

	@Test
	public void testIntegralTypes() {

		assertType(SqlFieldType.BOOLEAN, "`foo` BOOL");
		assertType(SqlFieldType.INTEGER, "`foo` TINYINT");
		assertType(SqlFieldType.INTEGER, "`foo` SMALLINT");
		assertType(SqlFieldType.INTEGER, "`foo` MEDIUMINT");
		assertType(SqlFieldType.INTEGER, "`foo` INT");
		assertType(SqlFieldType.LONG, "`foo` BIGINT");
	}

	@Test
	public void testDecimalTypes() {

		assertType(SqlFieldType.BIG_DECIMAL, "`foo` DECIMAL(10,4)");
		assertType(SqlFieldType.DOUBLE, "`foo` DOUBLE");
		assertType(SqlFieldType.FLOAT, "`foo` FLOAT");
	}

	@Test
	public void testEnumTypes() {

		assertType(SqlFieldType.ENUM, "`foo` ENUM('A','B','C')");
	}

	@Test
	public void testTimeTypes() {

		assertType(SqlFieldType.DAY, "`foo` DATE");
		assertType(SqlFieldType.DAY_TIME, "`foo` DATETIME");
		assertType(SqlFieldType.DAY_TIME, "`foo` TIMESTAMP");
		assertType(SqlFieldType.TIME, "`foo` TIME");
	}

	@Test
	public void testWithTinyInt() {

		assertType(SqlFieldType.BOOLEAN, "`foo` TINYINT(1)");
		assertType(SqlFieldType.INTEGER, "`foo` TINYINT(2)");
		assertType(SqlFieldType.INTEGER, "`foo` TINYINT(3)");
		assertType(SqlFieldType.INTEGER, "`foo` TINYINT(4)");
	}

	@Test
	public void testWithDoubleOverride() {

		assertType(SqlFieldType.DOUBLE, "`foo` DECIMAL(10,4) COMMENT '@double@'");
	}

	@Test
	public void testWithUnsupportedSetType() {

		try {
			determineType("`foo` SET('A','B')");
			fail("Expected exception.");
		} catch (DbMysqlUnsupportedColumnTypeException exception) {
			assertEquals(TABLE_NAME, exception.getTableName());
			assertEquals("foo", exception.getColumnName());
			assertEquals("SET", exception.getColumnType());
		}
	}

	private void assertType(SqlFieldType expectedType, String columnDefinition) {

		assertEquals(expectedType, determineType(columnDefinition));
	}

	private SqlFieldType determineType(String columnDefinition) {

		String sql = String.format("CREATE TABLE %s (%s)", TABLE_NAME, columnDefinition);
		IDbColumnStructure columnStructure = new DbMysqlCreateTableStatementParser(sql).parse().getColumns().get(0);
		return columnStructure.getFieldType();
	}
}
