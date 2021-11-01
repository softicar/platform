package com.softicar.platform.db.core.table.definition;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.core.table.metadata.IDbTableMetadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DbTableDefinition {

	private final DbTableName tableName;
	private final DbTableKeyDefinition primaryKeyDefinition;
	private final List<String> columns;

	public DbTableDefinition(DbTableName tableName, Function<DbTableName, IDbTableMetadata> metadataFetcher) {

		this.tableName = tableName;
		this.primaryKeyDefinition = new DbTableKeyDefinition(tableName, metadataFetcher);
		this.columns = new ArrayList<>(metadataFetcher.apply(tableName).getTableColumns().values());
	}

	public DbTableKeyDefinition getPrimaryKey() {

		return primaryKeyDefinition;
	}

	public List<String> getColumns() {

		return Collections.unmodifiableList(columns);
	}

	public DbTableName getTableName() {

		return tableName;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(String.format("name: %s, ", tableName));
		builder.append(String.format("pk: %s, ", primaryKeyDefinition.getColumns()));
		builder.append(String.format("colums: %s", getColumns()));
		builder.append(")");
		return builder.toString();
	}
}
