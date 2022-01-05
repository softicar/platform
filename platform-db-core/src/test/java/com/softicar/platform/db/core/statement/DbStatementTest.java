package com.softicar.platform.db.core.statement;

import com.softicar.platform.db.core.AbstractDbResultSetTest;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.metadata.DbMetadataRequester;
import com.softicar.platform.db.core.metadata.DbMetadataSchema;
import com.softicar.platform.db.core.metadata.DbMetadataTable;
import com.softicar.platform.db.core.table.DbCoreTestTable;
import com.softicar.platform.db.core.utils.DbResultSetAsserter;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class DbStatementTest extends AbstractDbResultSetTest {

	private static final String ROW_X_TEXT = "X";
	private static final String ROW_Y_TEXT = "Y";
	private final DbMetadataSchema schema;
	private final DbCoreTestTable tableA;
	private final DbCoreTestTable tableB;
	private final DbMetadataTable metadataTableA;
	private final DbMetadataTable metadataTableB;
	private final int rowXId;
	private final int rowYId;
	private final DbTableAccessListener listener;

	public DbStatementTest() {

		this.schema = DbDdlStatements.createSchema("TEST_SCHEMA");
		this.metadataTableA = DbDdlStatements.createTable(schema, "TABLE_A", "id INT AUTO_INCREMENT, text VARCHAR");
		this.metadataTableB = DbDdlStatements.createTable(schema, "TABLE_B", "someValue INT");
		this.tableA = new DbCoreTestTable(metadataTableA);
		this.tableB = new DbCoreTestTable(metadataTableB);
		this.rowXId = insertTextIntoTable(tableA, ROW_X_TEXT);
		this.rowYId = insertTextIntoTable(tableA, ROW_Y_TEXT);
		this.listener = new DbTableAccessListener();

		testDatabase.addListener(listener);
	}

	@Test
	public void testAddTextWithFormatParameters() {

		String text = new DbStatement().addText("SELECT * FROM %s WHERE %s = ?", "Foo", "x").getText();

		assertEquals("SELECT * FROM Foo WHERE x = ?", text);
	}

	@Test
	public void testAddTextWithoutFormatParameters() {

		String text = new DbStatement().addText("UPDATE Foo SET x = '%s'").getText();

		assertEquals("UPDATE Foo SET x = '%s'", text);
	}

	// -------------------- data definition language -------------------- //

	@Test
	public void testExecuteAlterTable() {

		new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("ALTER TABLE %s ADD COLUMN newColumn VARCHAR", tableA)
			.execute();

		listener.assertAccess(tableA, tableB);
		assertTableContainsColumn(metadataTableA, "newColumn");
	}

	@Test
	public void testExecuteCreateSchema() {

		new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("CREATE SCHEMA newSchema")
			.execute();

		listener.assertAccess(tableA, tableB);
		assertSchemaExists("newSchema");
	}

	@Test
	public void testExecuteCreateTable() {

		new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("CREATE TABLE %s.newTable (id INT)", schema.getSchemaName())
			.execute();

		listener.assertAccess(tableA, tableB);
		assertTableExists(schema, "newTable");
	}

	@Test
	public void testExecuteShow() {

		try (DbResultSet resultSet = new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("SHOW TABLES FROM %s", schema.getSchemaName())
			.executeQuery()) {

			listener.assertAccess(tableA, tableB);
			new DbResultSetAsserter(resultSet)//
				.assertNextRow()
				.assertString("TABLE_NAME", tableA.getFullName().getSimpleName())
				.assertNextRow()
				.assertString("TABLE_NAME", tableB.getFullName().getSimpleName())
				.assertEndOfResultSet();
		}
	}

	// -------------------- data manipulation language -------------------- //

	@Test
	public void testExecuteDelete() {

		new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("DELETE FROM %s WHERE text = ?", tableA)
			.addParameter(ROW_Y_TEXT)
			.executeUpdate();

		listener.assertAccess(tableA, tableB);
		new DbResultSetAsserter(() -> selectFromTable(tableA, "id"))//
			.assertNextRow()
			.assertInt("id", rowXId)
			.assertString("text", ROW_X_TEXT)
			.assertEndOfResultSet();
	}

	@Test
	public void testExecuteInsert() {

		int id = new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("INSERT INTO %s (text) VALUES (?)", tableA)
			.addParameter("Z")
			.executeInsert();

		listener.assertAccess(tableA, tableB);
		new DbResultSetAsserter(() -> selectFromTable(tableA, "id"))//
			.assertNextRow()
			.assertInt("id", rowXId)
			.assertString("text", ROW_X_TEXT)
			.assertNextRow()
			.assertInt("id", rowYId)
			.assertString("text", ROW_Y_TEXT)
			.assertNextRow()
			.assertInt("id", id)
			.assertString("text", "Z")
			.assertEndOfResultSet();
	}

	@Test
	public void testExecuteInsertWithoutIdGeneration() {

		new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("INSERT INTO %s (someValue) VALUES (12)", tableB)
			.executeUpdate();

		listener.assertAccess(tableA, tableB);
		new DbResultSetAsserter(() -> selectFromTable(tableB, "someValue"))//
			.assertNextRow()
			.assertInt("someValue", 12)
			.assertEndOfResultSet();
	}

	@Test
	public void testExecuteMultiRowInsert() {

		List<Integer> ids = new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("INSERT INTO %s (text) VALUES (?), (?)", tableA)
			.addParameter("U")
			.addParameter("V")
			.executeMultiRowInsert();

		listener.assertAccess(tableA, tableB);
		assertEquals(2, ids.size());
		new DbResultSetAsserter(() -> selectFromTable(tableA, "id"))//
			.assertNextRow()
			.assertInt("id", rowXId)
			.assertString("text", ROW_X_TEXT)
			.assertNextRow()
			.assertInt("id", rowYId)
			.assertString("text", ROW_Y_TEXT)
			.assertNextRow()
			.assertInt("id", ids.get(0))
			.assertString("text", "U")
			.assertNextRow()
			.assertInt("id", ids.get(1))
			.assertString("text", "V")
			.assertEndOfResultSet();
	}

	@Test
	public void testExecuteSelect() {

		try (DbResultSet resultSet = new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("SELECT * FROM %s ORDER BY id", tableA)
			.executeQuery()) {

			listener.assertAccess(tableA, tableB);
			new DbResultSetAsserter(resultSet)//
				.assertNextRow()
				.assertInt("id", rowXId)
				.assertString("text", ROW_X_TEXT)
				.assertNextRow()
				.assertInt("id", rowYId)
				.assertString("text", ROW_Y_TEXT)
				.assertEndOfResultSet();
		}
	}

	@Test
	public void testExecuteUpdate() {

		new DbStatement()//
			.addTable(tableA)
			.addTable(tableB)
			.addText("UPDATE %s SET text = ? WHERE id = ?", tableA)
			.addParameter("Z")
			.addParameter(rowYId)
			.executeUpdate();

		listener.assertAccess(tableA, tableB);
		new DbResultSetAsserter(() -> selectFromTable(tableA, "id"))//
			.assertNextRow()
			.assertInt("id", rowXId)
			.assertString("text", ROW_X_TEXT)
			.assertNextRow()
			.assertInt("id", rowYId)
			.assertString("text", "Z")
			.assertEndOfResultSet();
	}

	// -------------------- assert methods -------------------- //

	private static void assertSchemaExists(String expectedSchema) {

		Set<String> existingSchemas = new DbMetadataRequester().getSchemaNames();
		assertTrue(//
			String.format("Missing schema '%s'. Existing schemas: %s", expectedSchema, existingSchemas),
			existingSchemas.contains(expectedSchema));
	}

	private static void assertTableExists(DbMetadataSchema schema, String expectedTable) {

		Set<String> existingTable = new DbMetadataRequester().getTableNames(schema);
		assertTrue(//
			String.format("Missing table '%s'. Existing tables: %s", expectedTable, existingTable),
			existingTable.contains(expectedTable));
	}

	private static void assertTableContainsColumn(DbMetadataTable table, String extectedColumn) {

		Set<String> existingColumns = new DbMetadataRequester().getColumnNames(table);
		assertTrue(//
			String.format("Missing column '%s' in table '%s'. Existing columns: %s", extectedColumn, table, existingColumns),
			existingColumns.contains(extectedColumn));
	}
}
