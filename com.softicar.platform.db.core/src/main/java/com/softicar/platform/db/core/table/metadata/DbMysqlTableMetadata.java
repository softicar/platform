package com.softicar.platform.db.core.table.metadata;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.table.DbTableName;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

public class DbMysqlTableMetadata implements IDbTableMetadata {

	private final DbTableName tableName;

	public DbMysqlTableMetadata(DbTableName tableName) {

		this.tableName = tableName;
	}

	@Override
	public SortedMap<Integer, String> getTableColumns() {

		SortedMap<Integer, String> indexToColumnName = new TreeMap<>();
		DatabaseMetaData metaData = DbConnections.getMetaData();
		try (DbResultSet resultSet = new DbResultSet(metaData.getColumns(getCatalogName(), getSchemaName(), tableName.getSimpleName(), null))) {
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				Integer columnIndex = resultSet.getInteger("ORDINAL_POSITION");
				indexToColumnName.put(columnIndex, columnName);
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
		return indexToColumnName;
	}

	@Override
	public SortedMap<Integer, String> getPrimaryKeyColumns() {

		SortedMap<Integer, String> indexToColumnName = new TreeMap<>();
		DatabaseMetaData metaData = DbConnections.getMetaData();
		try (DbResultSet resultSet = new DbResultSet(metaData.getPrimaryKeys(getCatalogName(), getSchemaName(), tableName.getSimpleName()))) {
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				Integer columnKeyIndex = resultSet.getInteger("KEY_SEQ");
				indexToColumnName.put(columnKeyIndex, columnName);
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
		return indexToColumnName;
	}

	private String getCatalogName() {

		return isMySql()? tableName.getDatabaseName() : null;
	}

	private String getSchemaName() {

		return isMySql()? null : tableName.getDatabaseName();
	}

	private boolean isMySql() {

		return DbConnections.isServerType(DbServerType.MYSQL);
	}
}
