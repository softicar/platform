package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import org.junit.Assert;
import org.junit.Test;

public class InternalDbMysqlColumnBitsDeterminerTest extends Assert {

	@Test
	public void testIntegerTypes() {

		assertBits(64, "`x` BIGINT");
		assertBits(32, "`x` INT");
		assertBits(24, "`x` MEDIUMINT");
		assertBits(16, "`x` SMALLINT");
		assertBits(8, "`x` TINYINT");
	}

	@Test
	public void testNonIntegerTypes() {

		assertZeroBits("`x` BINARY");
		assertZeroBits("`x` BLOB");
		assertZeroBits("`x` BOOL");
		assertZeroBits("`x` CHAR");
		assertZeroBits("`x` DATE");
		assertZeroBits("`x` DATETIME");
		assertZeroBits("`x` DECIMAL(7)");
		assertZeroBits("`x` FLOAT");
		assertZeroBits("`x` DOUBLE");
		assertZeroBits("`x` ENUM('A')");
		assertZeroBits("`x` LONGBLOB");
		assertZeroBits("`x` LONGTEXT");
		assertZeroBits("`x` MEDIUMBLOB");
		assertZeroBits("`x` MEDIUMTEXT");
		assertZeroBits("`x` TEXT");
		assertZeroBits("`x` TIME");
		assertZeroBits("`x` TIMESTAMP");
		assertZeroBits("`x` TINYBLOB");
		assertZeroBits("`x` TINYINT(1)");
		assertZeroBits("`x` TINYTEXT");
		assertZeroBits("`x` VARBINARY");
		assertZeroBits("`x` VARCHAR");
	}

	private void assertZeroBits(String columnDefinition) {

		assertBits(0, columnDefinition);
	}

	private void assertBits(int expectedBits, String columnDefinition) {

		String sql = String.format("CREATE TABLE `foo` (%s)", columnDefinition);
		IDbColumnStructure columnStructure = new DbMysqlCreateTableStatementParser(sql).parse().getColumns().get(0);
		assertEquals(expectedBits, columnStructure.getBits());
	}
}
