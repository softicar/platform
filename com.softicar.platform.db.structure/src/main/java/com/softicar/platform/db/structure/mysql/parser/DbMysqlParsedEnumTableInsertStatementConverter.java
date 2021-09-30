package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.DbEnumTableRowStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DbMysqlParsedEnumTableInsertStatementConverter {

	private final IDbTableStructure tableStructure;

	public DbMysqlParsedEnumTableInsertStatementConverter(IDbTableStructure tableStructure) {

		this.tableStructure = tableStructure;
	}

	public Collection<DbEnumTableRowStructure> convert(DbMysqlParsedInsertStatement parsedInsertStatement) {

		Collection<DbEnumTableRowStructure> structures = new ArrayList<>();

		for (List<String> row: parsedInsertStatement.getValueRows()) {
			DbEnumTableRowStructure structure = new DbEnumTableRowStructure(tableStructure);
			structures.add(structure);

			List<String> columnNames = parsedInsertStatement.getColumnNames();
			for (int i = 0; i < columnNames.size(); i++) {
				String columnName = columnNames.get(i);
				String columnValue = row.get(i);

				IDbColumnStructure columnStructure = tableStructure.getColumnByPhysicalNameOrThrow(columnName);
				IDbEnumTableRowValue value = new InternalDbEnumTableRowValueFromSqlLiteralFactory(columnStructure.getFieldType(), columnValue).create();
				structure.setValue(columnStructure, value);
			}
		}

		return structures;
	}
}
