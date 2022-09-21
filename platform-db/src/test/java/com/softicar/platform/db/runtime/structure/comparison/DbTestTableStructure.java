package com.softicar.platform.db.runtime.structure.comparison;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnStructure;
import com.softicar.platform.db.structure.enums.DbEnumTableRowStructure;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.table.DbTableStructure;
import java.util.Collection;
import java.util.Collections;

public class DbTestTableStructure extends DbTableStructure {

	public DbTestTableStructure(String schemaName, String tableName) {

		super(new DbTableName(schemaName, tableName));
	}

	public DbColumnStructure addColumn(String columnName, SqlFieldType fieldType) {

		DbColumnStructure columnStructure = createColumnStructure(columnName, fieldType);
		addColumnStructure(columnStructure);
		return columnStructure;
	}

	public DbKeyStructure addKey(String keyName, DbKeyType keyType) {

		DbKeyStructure keyStructure = createKeyStructure(keyName, keyType, Collections.EMPTY_LIST);
		addKeyStructure(keyStructure);
		return keyStructure;
	}

	public DbForeignKeyStructure addForeignKey(String foreignKeyName, String foreignDatabaseName, String foreignTableName) {

		DbForeignKeyStructure foreignKeyStructure = createForeignKeyStructure(foreignKeyName, foreignDatabaseName, foreignTableName);
		addForeignKeyStructure(foreignKeyStructure);
		return foreignKeyStructure;
	}

	public DbEnumTableRowStructure addEnumTableRow() {

		DbEnumTableRowStructure structure = createEnumTableRowStructure();
		addEnumTableRow(structure);
		return structure;
	}

	private DbColumnStructure createColumnStructure(String columnName, SqlFieldType fieldType) {

		return new DbColumnStructure(this)//
			.setName(columnName)
			.setFieldType(fieldType)
			.setBits(32);
	}

	private DbKeyStructure createKeyStructure(String name, DbKeyType type, Collection<String> columnNames) {

		return new DbKeyStructure(this)//
			.setType(type)
			.setName(name)
			.addColumnNames(columnNames);
	}

	private DbForeignKeyStructure createForeignKeyStructure(String foreignKeyName, String foreignDatabaseName, String foreignTableName) {

		return new DbForeignKeyStructure(this)//
			.setName(foreignKeyName)
			.setForeignTableName(new DbTableName(foreignDatabaseName, foreignTableName));
	}

	private DbEnumTableRowStructure createEnumTableRowStructure() {

		return new DbEnumTableRowStructure(this);
	}
}
