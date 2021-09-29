package com.softicar.platform.db.core.table.definition;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.core.table.metadata.IDbTableMetadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DbTableKeyDefinition {

	private final List<String> columns;

	public DbTableKeyDefinition(List<String> columns) {

		this.columns = columns;
	}

	public DbTableKeyDefinition(DbTableName tableName, Function<DbTableName, IDbTableMetadata> metadataFetcher) {

		this(new ArrayList<>(metadataFetcher.apply(tableName).getPrimaryKeyColumns().values()));
	}

	public List<String> getColumns() {

		return Collections.unmodifiableList(columns);
	}

	@Override
	public String toString() {

		return columns.toString();
	}
}
