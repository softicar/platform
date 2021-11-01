package com.softicar.platform.core.module.log.process;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.level.AGLogLevel;
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
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface ILogProcessTableQuery extends IDbQuery<ILogProcessTableQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGLogProcess> ACTION_COLUMN = new DbQueryTableColumn<>(IRow::getAction, "action", AGLogProcess.TABLE);
	IDbQueryColumn<IRow, Integer> ID_COLUMN = new DbQueryColumn<>(IRow::getId, "id", SqlValueTypes.INTEGER, CoreI18n.PROCESS_ID);
	IDbQueryColumn<IRow, String> CLASS_NAME_COLUMN = new DbQueryColumn<>(IRow::getClassName, "className", SqlValueTypes.STRING, CoreI18n.PROCESS_NAME);
	IDbQueryTableColumn<IRow, AGLogLevel> WORST_LEVEL_COLUMN = new DbQueryTableStubColumn<>(IRow::getWorstLevel, "worstLevel", AGLogLevel.TABLE, CoreI18n.WORST_LOG_LEVEL);
	IDbQueryColumn<IRow, DayTime> START_TIME_COLUMN = new DbQueryColumn<>(IRow::getStartTime, "startTime", SqlValueTypes.DAY_TIME, CoreI18n.START_TIME);
	IDbQueryColumn<IRow, String> SERVER_IP_COLUMN = new DbQueryColumn<>(IRow::getServerIp, "serverIp", SqlValueTypes.STRING, CoreI18n.MACHINE_IP);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGLogProcess getAction();
		Integer getId();
		String getClassName();
		AGLogLevel getWorstLevel();
		DayTime getStartTime();
		String getServerIp();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IClassNameSetter createQuery();
	}

	interface IClassNameSetter {

		ILogProcessTableQuery setClassName(String className);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(6);

			public Factory() {

				this.columns.add(ACTION_COLUMN);
				this.columns.add(ID_COLUMN);
				this.columns.add(CLASS_NAME_COLUMN);
				this.columns.add(WORST_LEVEL_COLUMN);
				this.columns.add(START_TIME_COLUMN);
				this.columns.add(SERVER_IP_COLUMN);
			}

			@Override
			public IClassNameSetter createQuery() {

				return new Query().new ClassNameSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private String className;
		}

		private static class Query extends AbstractDbQuery<IRow> implements ILogProcessTableQuery {

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

			public class ClassNameSetter implements IClassNameSetter {

				@Override
				public final ILogProcessTableQuery setClassName(String className) {

					Query.this.parameters.className = className;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(ACTION_COLUMN);
					addTableColumns("logProcess", AGLogProcess.TABLE, "action");
					SELECT(ID_COLUMN);
					addIdentifier("logProcess", AGLogProcess.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("id");
					SELECT(CLASS_NAME_COLUMN);
					addIdentifier("logProcess", AGLogProcess.CLASS_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("className");
					SELECT(WORST_LEVEL_COLUMN);
					addIdentifier("logProcess", AGLogProcess.WORST_LEVEL);
					addToken(SqlKeyword.AS);
					addIdentifier("worstLevel");
					SELECT(START_TIME_COLUMN);
					addIdentifier("logProcess", AGLogProcess.START_TIME);
					addToken(SqlKeyword.AS);
					addIdentifier("startTime");
					SELECT(SERVER_IP_COLUMN);
					addIdentifier("logProcess", AGLogProcess.SERVER_IP);
					addToken(SqlKeyword.AS);
					addIdentifier("serverIp");
					FROM();
					addIdentifier(AGLogProcess.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("logProcess");
					WHERE();
					addIdentifier("logProcess", AGLogProcess.CLASS_NAME);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.className);
					ORDER_BY();
					addIdentifier("logProcess", AGLogProcess.START_TIME);
					addToken(SqlKeyword.DESC);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGLogProcess action;
			private final Integer id;
			private final String className;
			private final AGLogLevel worstLevel;
			private final DayTime startTime;
			private final String serverIp;

			private Row(ILogProcessTableQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.action = ACTION_COLUMN.loadValue(select, resultSet);
				this.id = ID_COLUMN.loadValue(select, resultSet);
				this.className = CLASS_NAME_COLUMN.loadValue(select, resultSet);
				this.worstLevel = WORST_LEVEL_COLUMN.loadValue(select, resultSet);
				this.startTime = START_TIME_COLUMN.loadValue(select, resultSet);
				this.serverIp = SERVER_IP_COLUMN.loadValue(select, resultSet);
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
			public Integer getId() {

				return this.id;
			}

			@Override
			public String getClassName() {

				return this.className;
			}

			@Override
			public AGLogLevel getWorstLevel() {

				return this.worstLevel;
			}

			@Override
			public DayTime getStartTime() {

				return this.startTime;
			}

			@Override
			public String getServerIp() {

				return this.serverIp;
			}
		}
	}
}

