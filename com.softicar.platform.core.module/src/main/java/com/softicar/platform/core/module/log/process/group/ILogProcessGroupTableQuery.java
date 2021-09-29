package com.softicar.platform.core.module.log.process.group;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.level.AGLogLevel;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryColumn;
import com.softicar.platform.db.runtime.query.DbQueryTableColumn;
import com.softicar.platform.db.runtime.query.DbQueryTableStubColumn;
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
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface ILogProcessGroupTableQuery extends IDbQuery<ILogProcessGroupTableQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGLogProcess> ACTION_COLUMN = new DbQueryTableColumn<>(IRow::getAction, "action", AGLogProcess.TABLE);
	IDbQueryColumn<IRow, String> CLASS_NAME_COLUMN = new DbQueryColumn<>(IRow::getClassName, "className", SqlValueTypes.STRING, CoreI18n.PROCESS_NAME);
	IDbQueryTableColumn<IRow, AGLogLevel> WORST_LEVEL_COLUMN = new DbQueryTableStubColumn<>(IRow::getWorstLevel, "worstLevel", AGLogLevel.TABLE, CoreI18n.WORST_LOG_LEVEL);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGLogProcess getAction();
		String getClassName();
		AGLogLevel getWorstLevel();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		ILogProcessGroupTableQuery createQuery();
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(3);

			public Factory() {

				this.columns.add(ACTION_COLUMN);
				this.columns.add(CLASS_NAME_COLUMN);
				this.columns.add(WORST_LEVEL_COLUMN);
			}

			@Override
			public ILogProcessGroupTableQuery createQuery() {

				return new Query();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Query extends AbstractDbQuery<IRow> implements ILogProcessGroupTableQuery {

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

					SELECT(ACTION_COLUMN);
					addTableColumns("logProcess", AGLogProcess.TABLE, "action");
					SELECT(CLASS_NAME_COLUMN);
					addIdentifier("logProcess", AGLogProcess.CLASS_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("className");
					SELECT(WORST_LEVEL_COLUMN);
					addIdentifier("logProcess", AGLogProcess.WORST_LEVEL);
					addToken(SqlKeyword.AS);
					addIdentifier("worstLevel");
					FROM();
					addIdentifier(AGLogProcess.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("logProcess");
					JOIN(null);
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					startSubSelect();
					SELECT();
					addToken(new DbSqlRawToken("MAX"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("logProcess2", AGLogProcess.ID);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AS);
					addIdentifier("id");
					FROM();
					addIdentifier(AGLogProcess.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("logProcess2");
					GROUP_BY();
					addIdentifier("logProcess2", AGLogProcess.CLASS_NAME);
					finishSubSelect();
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AS);
					addIdentifier("logProcess2");
					ON();
					addIdentifier("logProcess2", "id");
					addToken(SqlSymbol.EQUAL);
					addIdentifier("logProcess", AGLogProcess.ID);
					ORDER_BY();
					addIdentifier("logProcess", AGLogProcess.START_TIME);
					addToken(SqlKeyword.DESC);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGLogProcess action;
			private final String className;
			private final AGLogLevel worstLevel;

			private Row(ILogProcessGroupTableQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.action = ACTION_COLUMN.loadValue(select, resultSet);
				this.className = CLASS_NAME_COLUMN.loadValue(select, resultSet);
				this.worstLevel = WORST_LEVEL_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGLogProcess getAction() {

				return this.action;
			}

			@Override
			public String getClassName() {

				return this.className;
			}

			@Override
			public AGLogLevel getWorstLevel() {

				return this.worstLevel;
			}
		}
	}
}

