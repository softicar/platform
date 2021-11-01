package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.chunk.AGStoredFileChunk;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryTableStubColumn;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.IDbQueryFactory;
import com.softicar.platform.db.runtime.query.IDbQueryRow;
import com.softicar.platform.db.runtime.query.IDbQueryTableColumn;
import com.softicar.platform.db.runtime.query.builder.AbstractDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IStoredFileIdFromChunksQuery extends IDbQuery<IStoredFileIdFromChunksQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGStoredFile> FILE_COLUMN = new DbQueryTableStubColumn<>(IRow::getFile, "file", AGStoredFile.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGStoredFile getFile();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IStoredFileIdFromChunksQuery createQuery();
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(1);

			public Factory() {

				this.columns.add(FILE_COLUMN);
			}

			@Override
			public IStoredFileIdFromChunksQuery createQuery() {

				return new Query();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Query extends AbstractDbQuery<IRow> implements IStoredFileIdFromChunksQuery {

			@Override
			public IRow createRow(IDbSqlSelect select, DbResultSet resultSet) {

				return new Row(this, select, resultSet);
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return FACTORY.getColumns();
			}

			@Override
			public QuerySqlBuilder createSqlBuilder() {

				return new QuerySqlBuilder();
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(FILE_COLUMN);
					addIdentifier("chunk", AGStoredFileChunk.FILE);
					addToken(SqlKeyword.AS);
					addIdentifier("file");
					FROM();
					addIdentifier(AGStoredFileChunk.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("chunk");
					GROUP_BY();
					addIdentifier("chunk", AGStoredFileChunk.ID);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGStoredFile file;

			private Row(IStoredFileIdFromChunksQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.file = FILE_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGStoredFile getFile() {

				return this.file;
			}
		}
	}
}

