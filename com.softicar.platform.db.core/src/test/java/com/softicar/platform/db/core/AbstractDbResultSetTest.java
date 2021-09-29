package com.softicar.platform.db.core;

import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.DbCoreTestTable;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;

public abstract class AbstractDbResultSetTest extends AbstractDbCoreTest {

	protected void assertClassAndValue(Object expectedValue, String sql) {

		Object actual = executeSelect(sql);
		assertSame(expectedValue.getClass(), actual.getClass());
		assertEquals(expectedValue, actual);
	}

	protected Object executeSelect(String sql) {

		try (DbResultSet resultSet = new DbStatement().addText(sql).executeQuery()) {
			resultSet.next();
			return resultSet.get(1);
		}
	}

	protected int insertTextIntoTable(DbCoreTestTable table, String text) {

		return new DbStatement()//
			.addTable(table)
			.addText("INSERT INTO %s (text) VALUES (?)", table)
			.addParameter(text)
			.executeInsert();
	}

	protected int insertIntegerStringIntoTable(DbCoreTestTable table, Integer integer, String string) {

		return new DbStatement()//
			.addTable(table)
			.addText("INSERT INTO %s (integer, string) VALUES (?,?)", table)
			.addParameter(integer)
			.addParameter(string)
			.executeInsert();
	}

	protected DbResultSet selectFromTable(DbCoreTestTable table, String orderBy) {

		return new DbStatement()//
			.addTable(table)
			.addText("SELECT * FROM %s ORDER BY " + orderBy, table)
			.executeQuery();
	}
}
