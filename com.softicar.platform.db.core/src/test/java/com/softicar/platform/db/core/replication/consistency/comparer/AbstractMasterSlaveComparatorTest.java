package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.core.table.definition.DbTableDefinition;
import com.softicar.platform.db.core.table.metadata.DbMysqlTableMetadata;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractMasterSlaveComparatorTest extends AbstractDbCoreTest {

	protected static final String DEFAULT_SCHEMA = "PUBLIC";
	protected static final String A = "A";
	protected static final String B = "B";
	protected final List<Pair<TableRow, TableRow>> mismatches;
	protected final DbTableName tableNameA;
	protected final DbTableName tableNameB;
	protected final DbTableDefinition tableDefinitionA;
	protected final DbTableDefinition tableDefinitionB;

	protected AbstractMasterSlaveComparatorTest() {

		createTable(A);
		createTable(B);
		this.mismatches = new ArrayList<>();
		this.tableNameA = new DbTableName(DEFAULT_SCHEMA, A);
		this.tableNameB = new DbTableName(DEFAULT_SCHEMA, B);
		this.tableDefinitionA = new DbTableDefinition(tableNameA, DbMysqlTableMetadata::new);
		this.tableDefinitionB = new DbTableDefinition(tableNameB, DbMysqlTableMetadata::new);
	}

	protected void assertRow(TableRow row, String expectedId, String expectedText) {

		Map<String, String> valueMap = row.getValueMap();
		assertEquals(expectedId, valueMap.get("id"));
		assertEquals(expectedText, valueMap.get("text"));
	}

	protected void addMismatch(TableKeyRow keyRow, TableRow rowA, TableRow rowB) {

		DevNull.swallow(keyRow);
		mismatches.add(new Pair<>(rowA, rowB));
	}

	protected static void createTable(String tableName) {

		getCreateTableStatement(tableName).execute();
	}

	protected static DbStatement getCreateTableStatement(String tableName) {

		return new DbStatement("CREATE TABLE `%s` (`id` INT AUTO_INCREMENT, `text` VARCHAR(255), PRIMARY KEY (`id`))", tableName);
	}

	protected static void insertInto(String tableName, int id, String text) {

		new DbStatement("INSERT INTO %s VALUES (?, ?)", tableName).addParameter(id).addParameter(text).execute();
	}
}
