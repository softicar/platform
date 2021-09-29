package com.softicar.platform.db.core.utils;

import com.softicar.platform.db.core.AbstractDbResultSetTest;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.metadata.DbMetadataSchema;
import com.softicar.platform.db.core.metadata.DbMetadataTable;
import com.softicar.platform.db.core.statement.DbDdlStatements;
import com.softicar.platform.db.core.table.DbCoreTestTable;
import java.util.List;
import org.junit.Test;

public class DbResultSetConverterTest extends AbstractDbResultSetTest {

	private final DbMetadataSchema schema;
	private final DbMetadataTable metadataTableA;
	private final DbCoreTestTable table;
	private final int rowFooId;
	private final int rowBarId;

	public DbResultSetConverterTest() {

		this.schema = DbDdlStatements.createSchema("TEST_SCHEMA");
		this.metadataTableA = DbDdlStatements.createTable(schema, "TABLE_A", "id INT AUTO_INCREMENT, integer int, string VARCHAR");
		this.table = new DbCoreTestTable(metadataTableA);
		this.rowFooId = insertIntegerStringIntoTable(table, 31, "foo");
		this.rowBarId = insertIntegerStringIntoTable(table, 37, "bar");
	}

	@Test
	public void testToRows() {

		try (DbResultSet resultSet = selectFromTable(table, "id")) {
			List<DbResultSetRow> rows = new DbResultSetConverter(resultSet).toRows();

			assertEquals(2, rows.size());
			assertEquals(String.format("[id=%s, integer=31, string=foo]", rowFooId), rows.get(0).entrySet().toString());
			assertEquals(String.format("[id=%s, integer=37, string=bar]", rowBarId), rows.get(1).entrySet().toString());
		}
	}

}
