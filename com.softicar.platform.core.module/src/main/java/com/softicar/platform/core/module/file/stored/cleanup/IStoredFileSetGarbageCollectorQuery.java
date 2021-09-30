package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSetItem;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryTableColumn;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.IDbQueryFactory;
import com.softicar.platform.db.runtime.query.IDbQueryRow;
import com.softicar.platform.db.runtime.query.IDbQueryTableColumn;
import com.softicar.platform.db.runtime.query.builder.AbstractDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.select.DbSqlRawToken;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IStoredFileSetGarbageCollectorQuery extends IDbQuery<IStoredFileSetGarbageCollectorQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGStoredFileSet> FILE_SET_COLUMN = new DbQueryTableColumn<>(IRow::getFileSet, "fileSet", AGStoredFileSet.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGStoredFileSet getFileSet();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IRemoveAtThresholdSetter createQuery();
	}

	interface IRemoveAtThresholdSetter {

		IStoredFileSetGarbageCollectorQuery setRemoveAtThreshold(DayTime removeAtThreshold);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(1);

			public Factory() {

				this.columns.add(FILE_SET_COLUMN);
			}

			@Override
			public IRemoveAtThresholdSetter createQuery() {

				return new Query().new RemoveAtThresholdSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private DayTime removeAtThreshold;
		}

		private static class Query extends AbstractDbQuery<IRow> implements IStoredFileSetGarbageCollectorQuery {

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

			public class RemoveAtThresholdSetter implements IRemoveAtThresholdSetter {

				@Override
				public final IStoredFileSetGarbageCollectorQuery setRemoveAtThreshold(DayTime removeAtThreshold) {

					Query.this.parameters.removeAtThreshold = removeAtThreshold;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(FILE_SET_COLUMN);
					addTableColumns("fileSet", AGStoredFileSet.TABLE, "fileSet");
					FROM();
					addIdentifier(AGStoredFileSet.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("fileSet");
					JOIN(null);
					addIdentifier(AGStoredFileSetItem.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("item");
					ON();
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("item", AGStoredFileSetItem.FILE_SET);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("fileSet", AGStoredFileSet.ID);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					JOIN(null);
					addIdentifier(AGStoredFile.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("file");
					ON();
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("item", AGStoredFileSetItem.FILE);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("file", AGStoredFile.ID);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					GROUP_BY();
					addIdentifier("fileSet", AGStoredFileSet.ID);
					HAVING();
					addToken(new DbSqlRawToken("COUNT"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("file", AGStoredFile.ID);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlSymbol.EQUAL);
					addToken(new DbSqlRawToken("COUNT"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("file", AGStoredFile.REMOVE_AT);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AND);
					addToken(new DbSqlRawToken("MAX"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("file", AGStoredFile.REMOVE_AT);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlSymbol.LESS_EQUAL);
					addParameter(parameters.removeAtThreshold);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGStoredFileSet fileSet;

			private Row(IStoredFileSetGarbageCollectorQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.fileSet = FILE_SET_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGStoredFileSet getFileSet() {

				return this.fileSet;
			}
		}
	}
}

