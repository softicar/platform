package com.softicar.platform.db.structure.mysql;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyColumnPair;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.ForeignKeyRow;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.ArrayList;
import java.util.List;

public class DbMysqlForeignKeyStructure extends DbForeignKeyStructure {

	public DbMysqlForeignKeyStructure(IDbTableStructure tableStructure, ForeignKeyRow foreignKeyRow) {

		super(tableStructure);

		setName(foreignKeyRow.getName());
		setForeignTableName(determineForeignTableName(foreignKeyRow));
		addColumnPairs(determineColumnPairs(foreignKeyRow));
		setOnDeleteAction(foreignKeyRow.getOnDeleteAction());
		setOnUpdateAction(foreignKeyRow.getOnUpdateAction());
	}

	private DbTableName determineForeignTableName(ForeignKeyRow foreignKeyRow) {

		String sourceDatabaseName = getTableStructure().getTableName().getDatabaseName();
		String targetDatabaseName = foreignKeyRow.getForeignDatabase();
		String targetTableName = foreignKeyRow.getForeignTable();
		return new DbTableName(targetDatabaseName != null? targetDatabaseName : sourceDatabaseName, targetTableName);
	}

	private List<DbForeignKeyColumnPair> determineColumnPairs(ForeignKeyRow foreignKeyRow) {

		List<DbForeignKeyColumnPair> columnPairs = new ArrayList<>();
		List<String> sourceColumns = foreignKeyRow.getColumnList();
		List<String> targetColumns = foreignKeyRow.getForeignColumnList();
		for (int i = 0; i < sourceColumns.size(); i++) {
			columnPairs.add(new DbForeignKeyColumnPair(sourceColumns.get(i), targetColumns.get(i)));
		}
		return columnPairs;
	}
}
