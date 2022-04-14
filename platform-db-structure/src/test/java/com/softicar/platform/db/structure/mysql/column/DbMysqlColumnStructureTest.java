package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import java.util.Arrays;
import org.junit.Test;

public class DbMysqlColumnStructureTest extends AbstractTest {

	@Test
	public void testAutoIncrement() {

		assertFalse(create("`x` INT").isAutoIncrement());
		assertTrue(create("`x` INT AUTO_INCREMENT").isAutoIncrement());
	}

	@Test
	public void testBaseColumn() {

		assertFalse(create("`x` INT").isBaseColumn());
		assertTrue(create("`x` INT COMMENT '@base@'").isBaseColumn());
	}

	@Test
	public void testBits() {

		assertEquals(8, create("`x` TINYINT").getBits());
		assertEquals(16, create("`x` SMALLINT").getBits());
		assertEquals(24, create("`x` MEDIUMINT").getBits());
		assertEquals(32, create("`x` INT").getBits());
		assertEquals(64, create("`x` BIGINT").getBits());

		// some other types
		assertEquals(0, create("`x` TINYINT(1)").getBits());
		assertEquals(0, create("`x` VARCHAR(123)").getBits());
	}

	@Test
	public void testCharacterSet() {

		assertNull(create("`x` VARCHAR").getCharacterSet());
		assertEquals("xyz", create("`x` VARCHAR CHARACTER SET xyz").getCharacterSet());

		assertNull(create("`x` ENUM('A','B')").getCharacterSet());
		assertEquals("xyz", create("`x` ENUM('A','B') CHARACTER SET xyz").getCharacterSet());
	}

	@Test
	public void testCollation() {

		assertNull(create("`x` VARCHAR").getCollation());
		assertEquals("xyz", create("`x` VARCHAR COLLATE xyz").getCollation());
	}

	@Test
	public void testComment() {

		assertNull(create("`x` INT").getComment());
		assertEquals("abc", create("`x` INT COMMENT 'abc'").getComment());
	}

	@Test
	public void testDefaultValue() {

		assertNull(create("`x` DATETIME DEFAULT CURRENT_TIMESTAMP").getDefaultValue());
		assertNull(create("`x` VARCHAR").getDefaultValue());
		assertEquals("abc", create("`x` VARCHAR DEFAULT 'abc'").getDefaultValue());
	}

	@Test
	public void testDefaultType() {

		assertEquals(DbColumnDefaultType.CURRENT_TIMESTAMP, create("`x` DATETIME DEFAULT CURRENT_TIMESTAMP").getDefaultType());
		assertEquals(DbColumnDefaultType.NONE, create("`x` VARCHAR").getDefaultType());
		assertEquals(DbColumnDefaultType.NORMAL, create("`x` VARCHAR DEFAULT 'abc'").getDefaultType());
		assertEquals(DbColumnDefaultType.NULL, create("`x` VARCHAR DEFAULT NULL").getDefaultType());
	}

	@Test
	public void testEnumValues() {

		assertEquals(Arrays.asList("A", "B", "C"), create("`x` ENUM('A','B','C')").getEnumValues());
	}

	@Test
	public void testFieldType() {

		assertEquals(SqlFieldType.BIG_DECIMAL, create("`x` DECIMAL(7)").getFieldType());
		assertEquals(SqlFieldType.BOOLEAN, create("`x` BOOL").getFieldType());
		assertEquals(SqlFieldType.BOOLEAN, create("`x` TINYINT(1)").getFieldType());
		assertEquals(SqlFieldType.BYTE_ARRAY, create("`x` BINARY").getFieldType());
		assertEquals(SqlFieldType.BYTE_ARRAY, create("`x` BLOB").getFieldType());
		assertEquals(SqlFieldType.BYTE_ARRAY, create("`x` LONGBLOB").getFieldType());
		assertEquals(SqlFieldType.BYTE_ARRAY, create("`x` MEDIUMBLOB").getFieldType());
		assertEquals(SqlFieldType.BYTE_ARRAY, create("`x` TINYBLOB").getFieldType());
		assertEquals(SqlFieldType.DAY, create("`x` DATE").getFieldType());
		assertEquals(SqlFieldType.DAY_TIME, create("`x` DATETIME").getFieldType());
		assertEquals(SqlFieldType.DAY_TIME, create("`x` TIMESTAMP").getFieldType());
		assertEquals(SqlFieldType.DOUBLE, create("`x` DECIMAL(7) COMMENT '@double@'").getFieldType());
		assertEquals(SqlFieldType.DOUBLE, create("`x` DOUBLE").getFieldType());
		assertEquals(SqlFieldType.ENUM, create("`x` ENUM('A')").getFieldType());
		assertEquals(SqlFieldType.FLOAT, create("`x` FLOAT").getFieldType());
		assertEquals(SqlFieldType.INTEGER, create("`x` INT").getFieldType());
		assertEquals(SqlFieldType.INTEGER, create("`x` MEDIUMINT").getFieldType());
		assertEquals(SqlFieldType.INTEGER, create("`x` SMALLINT").getFieldType());
		assertEquals(SqlFieldType.INTEGER, create("`x` TINYINT").getFieldType());
		assertEquals(SqlFieldType.LONG, create("`x` BIGINT").getFieldType());
		assertEquals(SqlFieldType.STRING, create("`x` LONGTEXT").getFieldType());
		assertEquals(SqlFieldType.STRING, create("`x` MEDIUMTEXT").getFieldType());
		assertEquals(SqlFieldType.STRING, create("`x` TEXT").getFieldType());
		assertEquals(SqlFieldType.STRING, create("`x` TINYTEXT").getFieldType());
		assertEquals(SqlFieldType.STRING, create("`x` VARCHAR").getFieldType());
		assertEquals(SqlFieldType.TIME, create("`x` TIME").getFieldType());
	}

	@Test
	public void testLengthBits() {

		assertEquals(8, create("`x` TINYTEXT").getLengthBits());
		assertEquals(16, create("`x` TEXT").getLengthBits());
		assertEquals(24, create("`x` MEDIUMTEXT").getLengthBits());
		assertEquals(32, create("`x` LONGTEXT").getLengthBits());
	}

	@Test
	public void testLogicalName() {

		assertEquals("x", create("`x` INT").getLogicalName());
		assertEquals("y", create("`x` INT COMMENT '@name=y@'").getLogicalName());
	}

	@Test
	public void testMaxLength() {

		assertEquals(23, create("`x` BINARY(23)").getMaxLength());
		assertEquals(24, create("`x` CHAR(24)").getMaxLength());
		assertEquals(25, create("`x` VARBINARY(25)").getMaxLength());
		assertEquals(26, create("`x` VARCHAR(26)").getMaxLength());
	}

	@Test
	public void testNullable() {

		assertFalse(create("`x` INT NOT NULL").isNullable());
		assertTrue(create("`x` INT").isNullable());
	}

	@Test
	public void testNameOrThrow() {

		assertEquals("x", create("`x` INT").getNameOrThrow());
		assertEquals("x", create("`x` INT COMMENT '@name=y@'").getNameOrThrow());
	}

	@Test
	public void testPrecision() {

		assertEquals(5, create("`x` DECIMAL(5)").getPrecision());
		assertEquals(7, create("`x` DECIMAL(7,5)").getPrecision());
	}

	@Test
	public void testPrecisionWithDoubleOverride() {

		assertEquals(0, create("`x` DECIMAL(7,5) COMMENT '@double@'").getPrecision());
	}

	@Test
	public void testOnUpdateNowWithDateTime() {

		assertFalse(create("`x` DATETIME").isOnUpdateNow());
		assertTrue(create("`x` DATETIME ON UPDATE CURRENT_TIMESTAMP").isOnUpdateNow());
	}

	@Test
	public void testOnUpdateNowWithTimestamp() {

		assertFalse(create("`x` TIMESTAMP").isOnUpdateNow());
		assertTrue(create("`x` TIMESTAMP ON UPDATE CURRENT_TIMESTAMP").isOnUpdateNow());
	}

	@Test
	public void testScale() {

		assertEquals(0, create("`x` DECIMAL(5)").getScale());
		assertEquals(5, create("`x` DECIMAL(7,5)").getScale());
	}

	@Test
	public void testScaleWithDoubleOverride() {

		assertEquals(0, create("`x` DECIMAL(7,5) COMMENT '@double@'").getScale());
	}

	@Test
	public void testTimestamp() {

		assertTrue(create("`x` TIMESTAMP").isTimestamp());
		assertFalse(create("`x` DATETIME").isTimestamp());
	}

	@Test
	public void testUnsigned() {

		assertFalse(create("`x` INT").isUnsigned());
		assertTrue(create("`x` INT UNSIGNED").isUnsigned());
	}

	private IDbColumnStructure create(String columnDefinition) {

		String sql = String.format("CREATE TABLE `foo` (%s)", columnDefinition);
		return new DbMysqlCreateTableStatementParser(sql).parse().getColumns().get(0);
	}
}
