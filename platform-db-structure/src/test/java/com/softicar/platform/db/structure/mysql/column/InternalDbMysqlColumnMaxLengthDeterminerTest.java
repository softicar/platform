package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import org.junit.Assert;
import org.junit.Test;

public class InternalDbMysqlColumnMaxLengthDeterminerTest extends Assert {

	@Test
	public void testWithMaximumLength() {

		assertMaxLength(31, "`x` BINARY(31)");
		assertMaxLength(43, "`x` CHAR(43)");
		assertMaxLength(65, "`x` VARBINARY(65)");
		assertMaxLength(71, "`x` VARCHAR(71)");
	}

	@Test
	public void testWithoutMaximumLength() {

		assertZeroMaxLength("`x` BINARY");
		assertZeroMaxLength("`x` CHAR");
		assertZeroMaxLength("`x` VARBINARY");
		assertZeroMaxLength("`x` VARCHAR");
	}

	@Test
	public void testBlobTypes() {

		assertZeroMaxLength("`x` BLOB");
		assertZeroMaxLength("`x` LONGBLOB");
		assertZeroMaxLength("`x` MEDIUMBLOB");
		assertZeroMaxLength("`x` TINYBLOB");
	}

	@Test
	public void testTextTypes() {

		assertZeroMaxLength("`x` LONGTEXT");
		assertZeroMaxLength("`x` MEDIUMTEXT");
		assertZeroMaxLength("`x` TEXT");
		assertZeroMaxLength("`x` TINYTEXT");
	}

	@Test
	public void testTimeTypes() {

		assertZeroMaxLength("`x` DATE");
		assertZeroMaxLength("`x` DATETIME");
		assertZeroMaxLength("`x` TIME");
		assertZeroMaxLength("`x` TIMESTAMP");
	}

	@Test
	public void testEnumTypes() {

		assertZeroMaxLength("`x` ENUM('A')");
	}

	@Test
	public void testNumericTypes() {

		assertZeroMaxLength("`x` BIGINT");
		assertZeroMaxLength("`x` BOOL");
		assertZeroMaxLength("`x` DECIMAL(7)");
		assertZeroMaxLength("`x` DOUBLE");
		assertZeroMaxLength("`x` FLOAT");
		assertZeroMaxLength("`x` INT(11)");
		assertZeroMaxLength("`x` MEDIUMINT(8)");
		assertZeroMaxLength("`x` SMALLINT(5)");
		assertZeroMaxLength("`x` TINYINT(1)");
	}

	private void assertZeroMaxLength(String columnDefinition) {

		assertMaxLength(0, columnDefinition);
	}

	private void assertMaxLength(int expectedMaxLength, String columnDefinition) {

		String sql = String.format("CREATE TABLE `foo` (%s)", columnDefinition);
		IDbColumnStructure columnStructure = new DbMysqlCreateTableStatementParser(sql).parse().getColumns().get(0);
		assertEquals(expectedMaxLength, columnStructure.getMaxLength());
	}
}
