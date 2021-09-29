package com.softicar.platform.db.structure.mysql;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import com.softicar.platform.db.structure.table.DbTableStructure;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class DbMysqlTableStructuresLoader {

	private final Collection<DbTableName> tableNames;
	private Map<DbTableName, DbTableStructure> tableStructures;

	public DbMysqlTableStructuresLoader(Collection<DbTableName> tableNames) {

		this.tableNames = tableNames;
	}

	public Map<DbTableName, DbTableStructure> loadAll() {

		this.tableStructures = new TreeMap<>();
		loadTableStructures();
		loadEnumTableRows();
		return tableStructures;
	}

	private void loadTableStructures() {

		for (Entry<DbTableName, String> entry: loadCreateTableStatements().entrySet()) {
			DbTableStructure tableStructure = new DbMysqlCreateTableStatementParser(entry.getValue())//
				.setFallbackDatabaseName(entry.getKey().getDatabaseName())
				.parse();
			tableStructures.put(entry.getKey(), tableStructure);
		}
	}

	private Map<DbTableName, String> loadCreateTableStatements() {

		return new DbMysqlCreateTableStatementsLoader(tableNames).loadAllAsMap();
	}

	private void loadEnumTableRows() {

		new InternalDbMysqlEnumTableRowsLoader(tableStructures.values()).loadAll();
	}
}
