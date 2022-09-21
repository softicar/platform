package com.softicar.platform.db.structure.mysql;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.DbMultiResultSetIterable;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.DbEnumTableRowStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBigDecimalValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBooleanValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowDoubleValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowFloatValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowIntegerValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowLongValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowNullValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowStringValue;
import com.softicar.platform.db.structure.table.DbTableStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class InternalDbMysqlEnumTableRowsLoader {

	private final List<DbTableStructure> tableStructures;

	public InternalDbMysqlEnumTableRowsLoader(Collection<DbTableStructure> tableStructures) {

		this.tableStructures = tableStructures//
			.stream()
			.filter(IDbTableStructure::isEnumTable)
			.collect(Collectors.toList());
	}

	public void loadAll() {

		if (!tableStructures.isEmpty()) {
			List<String> queries = tableStructures//
				.stream()
				.map(this::createTableRowsQuery)
				.collect(Collectors.toList());
			loadTableRows(queries);
		}
	}

	private String createTableRowsQuery(IDbTableStructure tableStructure) {

		return String.format("SELECT * FROM %s", tableStructure.getTableName().getQuoted());
	}

	private void loadTableRows(List<String> queries) {

		int index = 0;
		DbStatement query = new DbStatement(Imploder.implode(queries, ";"));
		try (DbMultiResultSetIterable resultSets = query.executeMultiQuery()) {
			for (DbResultSet resultSet: resultSets) {
				loadTableRows(tableStructures.get(index), resultSet);
				index++;
			}
		}
	}

	private void loadTableRows(DbTableStructure tableStructure, DbResultSet resultSet) {

		while (resultSet.next()) {
			DbEnumTableRowStructure tableRowStructure = new DbEnumTableRowStructure(tableStructure);
			for (IDbColumnStructure columnStructure: tableStructure.getColumns()) {
				tableRowStructure.setValue(columnStructure, getValue(resultSet, columnStructure));
			}
			tableStructure.addEnumTableRow(tableRowStructure);
		}
	}

	private IDbEnumTableRowValue getValue(DbResultSet resultSet, IDbColumnStructure columnStructure) {

		if (resultSet.get(columnStructure.getNameOrThrow()) == null) {
			return new DbEnumTableRowNullValue();
		}

		switch (columnStructure.getFieldType()) {
		case BIG_DECIMAL:
			return new DbEnumTableRowBigDecimalValue(resultSet.getBigDecimal(columnStructure.getNameOrThrow()));
		case BOOLEAN:
			return new DbEnumTableRowBooleanValue(resultSet.getBoolean(columnStructure.getNameOrThrow()));
		case DOUBLE:
			return new DbEnumTableRowDoubleValue(resultSet.getDouble(columnStructure.getNameOrThrow()));
		case FLOAT:
			return new DbEnumTableRowFloatValue(resultSet.getFloat(columnStructure.getNameOrThrow()));
		case INTEGER:
			return new DbEnumTableRowIntegerValue(resultSet.getInteger(columnStructure.getNameOrThrow()));
		case LONG:
			return new DbEnumTableRowLongValue(resultSet.getLong(columnStructure.getNameOrThrow()));
		case STRING:
			return new DbEnumTableRowStringValue(resultSet.getString(columnStructure.getNameOrThrow()));
		default:
			throw new SofticarException("Column type %s is not supported for enum table rows.", columnStructure.getFieldType());
		}
	}
}
