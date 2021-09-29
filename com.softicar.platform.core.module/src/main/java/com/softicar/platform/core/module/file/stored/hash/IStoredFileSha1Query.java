package com.softicar.platform.core.module.file.stored.hash;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
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
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IStoredFileSha1Query extends IDbQuery<IStoredFileSha1Query.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGStoredFileSha1> SHA_1_COLUMN = new DbQueryTableStubColumn<>(IRow::getSha1, "sha1", AGStoredFileSha1.TABLE);
	IDbQueryTableColumn<IRow, AGStoredFile> FILE_COLUMN = new DbQueryTableStubColumn<>(IRow::getFile, "file", AGStoredFile.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGStoredFileSha1 getSha1();
		AGStoredFile getFile();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IFilesSetter createQuery();
	}

	interface IFilesSetter {

		IStoredFileSha1Query setFiles(AGStoredFile... files);
		IStoredFileSha1Query setFiles(Collection<? extends AGStoredFile> files);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(2);

			public Factory() {

				this.columns.add(SHA_1_COLUMN);
				this.columns.add(FILE_COLUMN);
			}

			@Override
			public IFilesSetter createQuery() {

				return new Query().new FilesSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private List<AGStoredFile> files = new ArrayList<>();
		}

		private static class Query extends AbstractDbQuery<IRow> implements IStoredFileSha1Query {

			private final Parameters parameters = new Parameters();

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

			public class FilesSetter implements IFilesSetter {

				@Override
				@SafeVarargs
				public final IStoredFileSha1Query setFiles(AGStoredFile... files) {

					return this.setFiles(Arrays.asList(files));
				}

				@Override
				public final IStoredFileSha1Query setFiles(Collection<? extends AGStoredFile> files) {

					Query.this.parameters.files.addAll(files);
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(SHA_1_COLUMN);
					addIdentifier("sha1", AGStoredFileSha1.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("sha1");
					SELECT(FILE_COLUMN);
					addIdentifier("file", AGStoredFile.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("file");
					FROM();
					addIdentifier(AGStoredFile.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("file");
					JOIN(null);
					addIdentifier(AGStoredFileSha1.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("sha1");
					ON();
					addIdentifier("file", AGStoredFile.SHA_1);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("sha1", AGStoredFileSha1.ID);
					WHERE();
					if(parameters.files.isEmpty()) {
						addToken(SqlKeyword.FALSE);
					} else {
						addIdentifier("file", AGStoredFile.ID);
						addToken(SqlKeyword.IN);
						addToken(SqlSymbol.LEFT_PARENTHESIS);
						addParameters(parameters.files);
						addToken(SqlSymbol.RIGHT_PARENTHESIS);
					}
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGStoredFileSha1 sha1;
			private final AGStoredFile file;

			private Row(IStoredFileSha1Query query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.sha1 = SHA_1_COLUMN.loadValue(select, resultSet);
				this.file = FILE_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGStoredFileSha1 getSha1() {

				return this.sha1;
			}

			@Override
			public AGStoredFile getFile() {

				return this.file;
			}
		}
	}
}

