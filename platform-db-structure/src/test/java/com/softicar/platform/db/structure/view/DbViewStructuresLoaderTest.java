package com.softicar.platform.db.structure.view;

import com.softicar.platform.db.core.metadata.DbMetadataSchema;
import com.softicar.platform.db.core.statement.DbDdlStatements;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.util.Map;
import org.junit.Test;

public class DbViewStructuresLoaderTest extends AbstractDbCoreTest {

	@Test
	public void test() {

		DbMetadataSchema schema = DbDdlStatements.createSchema("a");
		DbDdlStatements.createTable(schema, "tab", "");
		DbDdlStatements.createView(schema, "foo", "SELECT * FROM a.tab");

		Map<DbTableName, DbViewStructure> views = new DbViewStructuresLoader().loadAll();

		assertEquals(1, views.size());
		IDbViewStructure viewStructure = views.get(new DbTableName("a", "foo"));
		assertEquals("CREATE FORCE VIEW `a`.`foo` AS SELECT FROM `a`.`tab`".replace('`', '"'), viewStructure.getViewQuery().replace("\n", " "));
	}

	@Test
	public void testWithEmptyDatabase() {

		Map<DbTableName, DbViewStructure> views = new DbViewStructuresLoader().loadAll();

		assertEquals("{}", views.toString());
	}
}
