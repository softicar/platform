package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.object.DbTinyTestObject;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class DbTableSelectTest extends AbstractDbStatementTest {

	@Test
	public void testSelecting() {

		DbTestObject a = createAndSaveObject(1, "foo");
		DbTestObject b = createAndSaveObject(1, "foo");
		DbTestObject c = createAndSaveObject(2, "bar");
		DbTestObject d = createAndSaveObject(3, "foo");

		assertResult(DbTestObject.TABLE.createSelect(), a, b, c, d);
		assertResult(DbTestObject.TABLE.createSelect().where(DbTestObject.STRING_FIELD.isEqual("foo")), a, b, d);
		assertResult(DbTestObject.TABLE.createSelect().where(DbTestObject.STRING_FIELD.isEqual("bar")), c);
		assertResult(DbTestObject.TABLE.createSelect().where(DbTestObject.STRING_FIELD.isEqual("foo")).where(DbTestObject.INTEGER_FIELD.isEqual(3)), d);
		assertResult(DbTestObject.TABLE.createSelect().where(DbTestObject.INTEGER_FIELD.isEqual(1)), a, b);
	}

	@Test
	public void testCountWithoutGroupBy() {

		String expectedStatement = String.format("SELECT COUNT(*) FROM %s t", DbTinyTestObject.TABLE);

		DbTinyTestObject.TABLE//
			.createSelect()
			.count();
		assertSqlStatement(expectedStatement);
	}

	@Test
	public void testCountWithGroupBy() {

		String expectedStatement = String.format("SELECT COUNT(*) FROM (SELECT 1 FROM %s t GROUP BY t.`id`) s", DbTinyTestObject.TABLE);

		DbTinyTestObject.TABLE//
			.createSelect()
			.groupBy(DbTinyTestObject.ID_FIELD)
			.count();

		assertSqlStatement(expectedStatement);
	}

	private void assertResult(ISqlSelect<DbTestObject> select, DbTestObject...expectedResult) {

		List<DbTestObject> result = select.list();
		assertEquals(Arrays.asList(expectedResult), result);
	}

	@Test
	public void testMysqlIdentifierQuoting() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.MYSQL)
				.build())) {

			String select = DbTinyTestObject.TABLE
				.createSelect()//
				.where(DbTinyTestObject.STRING_FIELD.isEqual("foo"))
				.join(DbTinyTestObject.FOREIGN_FIELD)
				.getSelectText();
			assertEquals(
				new StringBuilder()
					.append("SELECT t.`id`, t.`string`, t.`foreign` ")
					.append("FROM `database`.`tiny` t ")
					.append("JOIN `database`.`table` a1 ON a1.`id` = t.`foreign` ")
					.append("WHERE (t.`string` = ?)")
					.toString(),
				select);
		}
	}

	@Test
	public void testMssqlIdentifierQuoting() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.MSSQL_SERVER_2000)
				.build())) {

			String select = DbTinyTestObject.TABLE
				.createSelect()//
				.where(DbTinyTestObject.STRING_FIELD.isEqual("foo"))
				.join(DbTinyTestObject.FOREIGN_FIELD)
				.getSelectText();
			assertEquals(
				new StringBuilder()
					.append("SELECT t.[id], t.[string], t.[foreign] ")
					.append("FROM [database].[tiny] t ")
					.append("JOIN [database].[table] a1 ON a1.[id] = t.[foreign] ")
					.append("WHERE (t.[string] = ?)")
					.toString(),
				select);
		}
	}
}
