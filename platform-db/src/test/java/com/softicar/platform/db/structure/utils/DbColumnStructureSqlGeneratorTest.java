package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import org.junit.Test;

public class DbColumnStructureSqlGeneratorTest extends AbstractTest {

	private static final Integer AUTO_INCREMENT_START = 666;
	private Integer autoIncrementCounter;
	private boolean h2Database;

	public DbColumnStructureSqlGeneratorTest() {

		this.autoIncrementCounter = null;
		this.h2Database = false;
	}

	@Test
	public void testAutoIncrement() {

		String signed = convert("`x` INT NOT NULL AUTO_INCREMENT");
		String unsigned = convert("`x` INT UNSIGNED NOT NULL AUTO_INCREMENT");

		assertEquals("`x` INT NOT NULL AUTO_INCREMENT", signed);
		assertEquals("`x` INT UNSIGNED NOT NULL AUTO_INCREMENT", unsigned);
	}

	@Test
	public void testAutoIncrementWithH2Start() {

		this.autoIncrementCounter = AUTO_INCREMENT_START;
		this.h2Database = true;

		String signed = convert("`x` INT NOT NULL AUTO_INCREMENT");
		String unsigned = convert("`x` INT UNSIGNED NOT NULL AUTO_INCREMENT");

		assertEquals("`x` INT NOT NULL AUTO_INCREMENT(" + AUTO_INCREMENT_START + ")", signed);
		assertEquals("`x` INT UNSIGNED NOT NULL AUTO_INCREMENT(" + AUTO_INCREMENT_START + ")", unsigned);
	}

	@Test
	public void testColumnWithSingleQuoteInComment() {

		assertEquals("`x` INT COMMENT 'the row''s id'", convert("`x` INT COMMENT 'the row''s id'"));
	}

	@Test
	public void testColumnWithSingleQuoteInDefaultValue() {

		assertEquals("`x` VARCHAR(100) DEFAULT 'stupid''s idea'", convert("`x` VARCHAR(100) DEFAULT 'stupid''s idea'"));
	}

	@Test
	public void testDatetimeAndTimestampColumns() {

		assertEquals("`x` DATETIME", convert("`x` DATETIME"));
		assertEquals("`x` TIMESTAMP", convert("`x` TIMESTAMP"));
	}

	@Test
	public void testDatetimeAndTimestampColumnsWithDefaultNull() {

		assertEquals("`x` DATETIME DEFAULT NULL", convert("`x` DATETIME DEFAULT NULL"));
		assertEquals("`x` TIMESTAMP NULL DEFAULT NULL", convert("`x` TIMESTAMP DEFAULT NULL"));
	}

	@Test
	public void testDatetimeAndTimestampColumnsWithOnUpdateNow() {

		assertEquals("`x` DATETIME ON UPDATE CURRENT_TIMESTAMP", convert("`x` DATETIME ON UPDATE CURRENT_TIMESTAMP"));
		assertEquals("`x` TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", convert("`x` TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"));
	}

	@Test
	public void testIntegerColumn() {

		String complex = convert("`x` INT UNSIGNED DEFAULT '1' COMMENT 'This is some ID'");
		String simple = convert("`x` INT NOT NULL");

		assertEquals("`x` INT UNSIGNED DEFAULT '1' COMMENT 'This is some ID'", complex);
		assertEquals("`x` INT NOT NULL", simple);
		assertEquals("`x` TINYINT", convert("`x` TINYINT"));
		assertEquals("`x` SMALLINT", convert("`x` SMALLINT"));
		assertEquals("`x` MEDIUMINT", convert("`x` MEDIUMINT"));
		assertEquals("`x` INT", convert("`x` INT"));
		assertEquals("`x` BIGINT", convert("`x` BIGINT"));
	}

	@Test
	public void testVarcharColumn() {

		String complex = convert("`x` VARCHAR(222) DEFAULT 'Nope' COLLATE utf8 COMMENT 'This is some varchar'");
		String simple = convert("`x` VARCHAR(111) NOT NULL COLLATE utf8");

		assertEquals("`x` VARCHAR(222) COLLATE utf8 DEFAULT 'Nope' COMMENT 'This is some varchar'", complex);
		assertEquals("`x` VARCHAR(111) COLLATE utf8 NOT NULL", simple);
	}

	@Test
	public void testEnumColumn() {

		String column = convert("`x` ENUM('A','B','C') NOT NULL DEFAULT 'First' COMMENT 'This is some enum'");

		assertEquals("`x` ENUM('A','B','C') NOT NULL DEFAULT 'First' COMMENT 'This is some enum'", column);
	}

	@Test
	public void testEnumColumnWithH2Database() {

		String column = convert("`x` ENUM('A','B','C') NOT NULL DEFAULT 'A' COMMENT 'This is some enum'");

		assertEquals("`x` ENUM('A','B','C') NOT NULL DEFAULT 'A' COMMENT 'This is some enum'", column);
	}

	@Test
	public void testStringColumns() {

		assertEquals("`x` VARCHAR(123)", convert("`x` VARCHAR(123)"));

		assertEquals("`x` TINYTEXT", convert("`x` TINYTEXT"));
		assertEquals("`x` TEXT", convert("`x` TEXT"));
		assertEquals("`x` MEDIUMTEXT", convert("`x` MEDIUMTEXT"));
		assertEquals("`x` LONGTEXT", convert("`x` LONGTEXT"));
	}

	@Test
	public void testStringColumnWithCharacterSet() {

		assertEquals("`x` VARCHAR(123)", convert("`x` VARCHAR(123)"));
		assertEquals("`x` VARCHAR(123) CHARACTER SET foo", convert("`x` VARCHAR(123) CHARACTER SET foo"));
	}

	@Test
	public void testStringColumnWithCharacterSetAndH2() {

		this.h2Database = true;
		assertEquals("`x` VARCHAR(123)", convert("`x` VARCHAR(123) CHARACTER SET foo"));
	}

	@Test
	public void testStringColumnWithCollate() {

		assertEquals("`x` VARCHAR(123)", convert("`x` VARCHAR(123)"));
		assertEquals("`x` VARCHAR(123) COLLATE foo", convert("`x` VARCHAR(123) COLLATE foo"));
	}

	@Test
	public void testStringColumnWithCollateAndH2() {

		this.h2Database = true;
		assertEquals("`x` VARCHAR(123)", convert("`x` VARCHAR(123) COLLATE foo"));
	}

	@Test
	public void testByteArrayColumnWithLengthBits() {

		assertEquals("`x` TINYBLOB NOT NULL", convert("`x` TINYBLOB NOT NULL"));
		assertEquals("`x` BLOB NOT NULL", convert("`x` BLOB NOT NULL"));
		assertEquals("`x` MEDIUMBLOB NOT NULL", convert("`x` MEDIUMBLOB NOT NULL"));
		assertEquals("`x` LONGBLOB NOT NULL", convert("`x` LONGBLOB NOT NULL"));
	}

	// ------------------------------ column creation ------------------------------ //

	private IDbColumnStructure parse(String columnDefinition) {

		String createTableStatement = "CREATE TABLE `db`.`tab` (" + columnDefinition + ")";
		return new DbMysqlCreateTableStatementParser(createTableStatement).parse().getColumns().get(0);
	}

	private String convert(String columnDefinition) {

		return new DbColumnStructureSqlGenerator(parse(columnDefinition))//
			.setAutoIncrementStart(autoIncrementCounter)
			.setIsH2Database(h2Database)
			.toString();
	}
}
