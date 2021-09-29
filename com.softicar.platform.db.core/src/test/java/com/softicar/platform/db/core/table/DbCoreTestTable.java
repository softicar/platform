package com.softicar.platform.db.core.table;

import com.softicar.platform.db.core.metadata.DbMetadataTable;

public class DbCoreTestTable implements IDbCoreTable {

	private final DbTableName name;

	public DbCoreTestTable(DbMetadataTable metadataTable) {

		this.name = new DbTableName(metadataTable.getSchema().getSchemaName(), metadataTable.getTableName());
	}

	@Override
	public DbTableName getFullName() {

		return name;
	}

	@Override
	public String toString() {

		return name.toString();
	}
}
