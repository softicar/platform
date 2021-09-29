package com.softicar.platform.db.core.table;

import com.softicar.platform.db.core.metadata.DbMetadataSchema;
import com.softicar.platform.db.core.statement.DbDdlStatements;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.util.Map;
import org.junit.Test;

public class DbTableNameFetcherTest extends AbstractDbCoreTest {

	private final DbTableNameFetcher fetcher;

	public DbTableNameFetcherTest() {

		this.fetcher = new DbTableNameFetcher()//
			.addIgnoredTableSchema("INFORMATION_SCHEMA");
	}

	@Test
	public void test() {

		DbMetadataSchema schema = DbDdlStatements.createSchema("a");
		DbDdlStatements.createTable(schema, "table", "");
		DbDdlStatements.createView(schema, "view", "SELECT 1");

		Map<DbTableName, String> tableNames = fetcher.fetchAllAsMap();

		assertEquals("{`a`.`table`=TABLE, `a`.`view`=VIEW}", tableNames.toString());
	}

	@Test
	public void testWithTableTypeFilter() {

		DbMetadataSchema schema = DbDdlStatements.createSchema("a");
		DbDdlStatements.createTable(schema, "table", "");
		DbDdlStatements.createView(schema, "view", "SELECT 1");

		Map<DbTableName, String> tableNames = fetcher.addTableType("VIEW").fetchAllAsMap();

		assertEquals("{`a`.`view`=VIEW}", tableNames.toString());
	}

	@Test
	public void testWithTableSchemaFilter() {

		DbMetadataSchema schemaA = DbDdlStatements.createSchema("a");
		DbDdlStatements.createTable(schemaA, "table", "");

		DbMetadataSchema schemaB = DbDdlStatements.createSchema("b");
		DbDdlStatements.createView(schemaB, "view", "SELECT 1");

		Map<DbTableName, String> tableNames = fetcher.addIgnoredTableSchema("a").fetchAllAsMap();

		assertEquals("{`b`.`view`=VIEW}", tableNames.toString());
	}

	@Test
	public void testWithEmtpyDatabase() {

		Map<DbTableName, String> tableNames = fetcher.fetchAllAsMap();

		assertEquals("{}", tableNames.toString());
	}
}
