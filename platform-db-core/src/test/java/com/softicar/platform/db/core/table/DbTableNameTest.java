package com.softicar.platform.db.core.table;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import org.junit.Test;

public class DbTableNameTest extends AbstractTest {

	private static final DbTableName TABLE_NAME = new DbTableName("database", "table");

	@Test
	public void testToStringAndGetQuotedWithMysql() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.MYSQL)
				.build())) {
			assertEquals("`database`.`table`", TABLE_NAME.toString());
			assertEquals("`database`.`table`", TABLE_NAME.getQuoted());
		}
	}

	@Test
	public void testToStringAndGetQuotedWithMssql() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.MSSQL_SERVER_2000)
				.build())) {
			assertEquals("[database].[table]", TABLE_NAME.toString());
			assertEquals("[database].[table]", TABLE_NAME.getQuoted());
		}
	}

	@Test
	public void testGetDatabaseNameAndTableName() {

		assertEquals("database", TABLE_NAME.getDatabaseName());
		assertEquals("table", TABLE_NAME.getSimpleName());
	}

	@Test
	public void testEqualsAndHashCode() {

		// equal to itself but not to null
		assertTrue(TABLE_NAME.equals(TABLE_NAME));
		assertFalse(TABLE_NAME.equals(null));

		// equal to equal name
		DbTableName equalName = new DbTableName("database", "table");
		assertTrue(TABLE_NAME.equals(equalName));
		assertTrue(equalName.equals(TABLE_NAME));
		assertEquals(TABLE_NAME.hashCode(), equalName.hashCode());

		// not equal with different database name
		DbTableName differentDatabaseName = new DbTableName("foo", "table");
		assertFalse(TABLE_NAME.equals(differentDatabaseName));
		assertFalse(differentDatabaseName.equals(TABLE_NAME));

		// not equal with different table name
		DbTableName differentTableName = new DbTableName("database", "foo");
		assertFalse(TABLE_NAME.equals(differentTableName));
		assertFalse(differentTableName.equals(TABLE_NAME));
	}

	@Test
	public void testCompareTo() {

		assertEquals(0, new DbTableName("a", "A").compareTo(new DbTableName("a", "A")));

		assertTrue(new DbTableName("a", "A").compareTo(new DbTableName("b", "A")) < 0);
		assertTrue(new DbTableName("a", "A").compareTo(new DbTableName("a", "B")) < 0);

		assertTrue(new DbTableName("b", "A").compareTo(new DbTableName("a", "A")) > 0);
		assertTrue(new DbTableName("a", "B").compareTo(new DbTableName("a", "A")) > 0);
	}
}
