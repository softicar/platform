package com.softicar.platform.db.structure.mysql.column;

import org.junit.Assert;
import org.junit.Test;

public class InternalDbMysqlColumnLengthBitsDeterminerTest extends Assert {

	@Test
	public void testBlobTypes() {

		assertLengthBits(16, InternalDbMysqlColumnType.BLOB);
		assertLengthBits(32, InternalDbMysqlColumnType.LONGBLOB);
		assertLengthBits(24, InternalDbMysqlColumnType.MEDIUMBLOB);
		assertLengthBits(8, InternalDbMysqlColumnType.TINYBLOB);
	}

	@Test
	public void testTextTypes() {

		assertLengthBits(32, InternalDbMysqlColumnType.LONGTEXT);
		assertLengthBits(24, InternalDbMysqlColumnType.MEDIUMTEXT);
		assertLengthBits(16, InternalDbMysqlColumnType.TEXT);
		assertLengthBits(8, InternalDbMysqlColumnType.TINYTEXT);
	}

	@Test
	public void testNumericTypes() {

		assertZeroLengthBits(InternalDbMysqlColumnType.BIGINT);
		assertZeroLengthBits(InternalDbMysqlColumnType.BOOL);
		assertZeroLengthBits(InternalDbMysqlColumnType.DECIMAL);
		assertZeroLengthBits(InternalDbMysqlColumnType.FLOAT);
		assertZeroLengthBits(InternalDbMysqlColumnType.DOUBLE);
		assertZeroLengthBits(InternalDbMysqlColumnType.INT);
		assertZeroLengthBits(InternalDbMysqlColumnType.MEDIUMINT);
		assertZeroLengthBits(InternalDbMysqlColumnType.SMALLINT);
		assertZeroLengthBits(InternalDbMysqlColumnType.TINYINT);
	}

	@Test
	public void testEnumTypes() {

		assertZeroLengthBits(InternalDbMysqlColumnType.ENUM);
		assertZeroLengthBits(InternalDbMysqlColumnType.SET);
	}

	@Test
	public void testTimeTypes() {

		assertZeroLengthBits(InternalDbMysqlColumnType.DATE);
		assertZeroLengthBits(InternalDbMysqlColumnType.DATETIME);
		assertZeroLengthBits(InternalDbMysqlColumnType.TIME);
		assertZeroLengthBits(InternalDbMysqlColumnType.TIMESTAMP);
	}

	@Test
	public void testTypesWithMaxLength() {

		assertZeroLengthBits(InternalDbMysqlColumnType.BINARY);
		assertZeroLengthBits(InternalDbMysqlColumnType.CHAR);
		assertZeroLengthBits(InternalDbMysqlColumnType.VARBINARY);
		assertZeroLengthBits(InternalDbMysqlColumnType.VARCHAR);
	}

	private void assertZeroLengthBits(InternalDbMysqlColumnType columnType) {

		assertLengthBits(0, columnType);
	}

	private void assertLengthBits(int expectedBits, InternalDbMysqlColumnType columnType) {

		int actualBits = new InternalDbMysqlColumnLengthBitsDeterminer(columnType).determineLengthBits();
		assertEquals(expectedBits, actualBits);
	}
}
