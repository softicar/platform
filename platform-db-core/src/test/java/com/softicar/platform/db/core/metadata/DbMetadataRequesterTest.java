package com.softicar.platform.db.core.metadata;

import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.sql.Types;
import java.util.Map;
import org.junit.Test;

public class DbMetadataRequesterTest extends AbstractDbCoreTest {

	@Test
	public void testGetSchemas() {

		new DbStatement().addText("CREATE SCHEMA foo").execute();
		new DbStatement().addText("CREATE SCHEMA bar").execute();

		Map<String, DbMetadataSchema> schemas = new DbMetadataRequester().getSchemas();
		assertTrue(schemas.containsKey("foo"));
		assertTrue(schemas.containsKey("bar"));

	}

	@Test
	public void testGetTables() {

		new DbStatement().addText("CREATE SCHEMA sch").execute();
		new DbStatement().addText("CREATE TABLE sch.tab (id int)").execute();
		new DbStatement().addText("CREATE VIEW sch.v AS SELECT * FROM sch.tab").execute();

		DbMetadataSchema schema = new DbMetadataRequester().getSchemas().get("sch");

		Map<String, DbMetadataTable> tables = new DbMetadataRequester().getTables(schema);
		assertEquals(2, tables.size());
		assertTrue(tables.containsKey("tab"));
		assertTrue(tables.containsKey("v"));

		DbMetadataTable tableA = tables.get("tab");
		assertSame(schema, tableA.getSchema());
		assertEquals("tab", tableA.getTableName());
		assertEquals("BASE TABLE", tableA.getTableType());

		DbMetadataTable tableB = tables.get("v");
		assertSame(schema, tableB.getSchema());
		assertEquals("v", tableB.getTableName());
		assertEquals("VIEW", tableB.getTableType());
	}

	@Test
	public void testGetColumns() {

		new DbStatement().addText("CREATE SCHEMA someSchema").execute();
		new DbStatement()
			.addText("CREATE TABLE someSchema.someTable (")
			.addText("id int AUTO_INCREMENT,")
			.addText("name VARCHAR(33) NULL DEFAULT 'abc',")
			.addText("someValue DECIMAL(10,3) NOT NULL DEFAULT 3.14")
			.addText(")")
			.execute();

		DbMetadataSchema schema = new DbMetadataRequester().getSchema("someSchema").get();
		DbMetadataTable table = new DbMetadataRequester().getTable(schema, "someTable").get();

		Map<String, DbMetadataColumn> columns = new DbMetadataRequester().getColumns(table);
		assertEquals(3, columns.size());
		assertTrue(columns.containsKey("id"));
		assertTrue(columns.containsKey("name"));
		assertTrue(columns.containsKey("someValue"));

		DbMetadataColumn idColumn = columns.get("id");
		assertSame(table, idColumn.getTable());
		assertEquals("id", idColumn.getColumnName());
		assertEquals(1, idColumn.getColumnIndex());
		assertEquals(Types.INTEGER, idColumn.getDataType());
		assertTrue(idColumn.isAutoIncrement());
		assertFalse(idColumn.isNullable());

		DbMetadataColumn nameColumn = columns.get("name");
		assertSame(table, nameColumn.getTable());
		assertEquals("name", nameColumn.getColumnName());
		assertEquals(2, nameColumn.getColumnIndex());
		assertEquals(Types.VARCHAR, nameColumn.getDataType());
		assertEquals(33, nameColumn.getCharOctetLength());
		assertEquals("'abc'", nameColumn.getDefaultValue());
		assertFalse(nameColumn.isAutoIncrement());
		assertTrue(nameColumn.isNullable());

		DbMetadataColumn valueColumn = columns.get("someValue");
		assertSame(table, valueColumn.getTable());
		assertEquals("someValue", valueColumn.getColumnName());
		assertEquals(3, valueColumn.getColumnIndex());
		assertEquals(Types.DECIMAL, valueColumn.getDataType());
		assertEquals(10, valueColumn.getCharOctetLength());
		assertEquals(3, valueColumn.getDecimalDigits());
		assertEquals("3.14", valueColumn.getDefaultValue());
		assertFalse(valueColumn.isAutoIncrement());
		assertFalse(valueColumn.isNullable());
	}
}
