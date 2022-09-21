package com.softicar.platform.db.structure.mysql;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.dbms.h2.DbH2Database;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import com.softicar.platform.db.structure.table.DbTableStructure;
import java.util.Arrays;
import org.junit.After;
import org.junit.Test;

public class InternalDbMysqlEnumTableRowsLoaderTest extends AbstractTest {

	private static final String CREATE_TABLE_STATEMENT = new StringBuilder()//
		.append("CREATE TABLE `db`.`foo` (")
		.append("  `id` INT AUTO_INCREMENT,")
		.append("  `name` VARCHAR,")
		.append("  PRIMARY KEY (`id`))")
		.toString();

	public InternalDbMysqlEnumTableRowsLoaderTest() {

		DbCurrentDatabase.set(new DbH2Database(""));
	}

	@After
	public void cleanup() {

		DbConnections.closeAll();
		DbCurrentDatabase.set(null);
	}

	@Test
	public void testWithTableRows() {

		DbTableStructure tableStructure = createTableAndParseStructure(CREATE_TABLE_STATEMENT);
		new DbStatement("INSERT INTO `db`.`foo` (id, name) VALUES (1, 'foo'), (2, 'bar')").execute();

		new InternalDbMysqlEnumTableRowsLoader(Arrays.asList(tableStructure)).loadAll();

		assertEnumTableRows(tableStructure, "[{id=1, name=foo}, {id=2, name=bar}]");
	}

	@Test
	public void testWithoutTableRows() {

		DbTableStructure tableStructure = createTableAndParseStructure(CREATE_TABLE_STATEMENT);

		new InternalDbMysqlEnumTableRowsLoader(Arrays.asList(tableStructure)).loadAll();

		assertEnumTableRows(tableStructure, "[]");
	}

	@Test
	public void testWithoutEnumComment() {

		DbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(CREATE_TABLE_STATEMENT).parse();

		new InternalDbMysqlEnumTableRowsLoader(Arrays.asList(tableStructure)).loadAll();

		assertFalse(tableStructure.isEnumTable());
	}

	@Test
	public void testWithNullValues() {

		DbTableStructure tableStructure = createTableAndParseStructure(CREATE_TABLE_STATEMENT);
		new DbStatement("INSERT INTO `db`.`foo` (id, name) VALUES (1, 'foo'), (2, NULL)").execute();

		new InternalDbMysqlEnumTableRowsLoader(Arrays.asList(tableStructure)).loadAll();

		assertEnumTableRows(tableStructure, "[{id=1, name=foo}, {id=2, name=null}]");
	}

	// ------------------------------ private ------------------------------ //

	private void assertEnumTableRows(DbTableStructure tableStructure, String expectedRows) {

		assertTrue(tableStructure.isEnumTable());
		assertEquals(expectedRows, tableStructure.getEnumTableRows().toString());
	}

	private DbTableStructure createTableAndParseStructure(String createTableStatementWithoutComment) {

		// create table
		new DbStatement("CREATE SCHEMA `db`").execute();
		new DbStatement(createTableStatementWithoutComment).execute();

		// parse table structure
		String createTableStatementWithComment = createTableStatementWithoutComment + " COMMENT '@enum@'";
		return new DbMysqlCreateTableStatementParser(createTableStatementWithComment).parse();
	}
}
