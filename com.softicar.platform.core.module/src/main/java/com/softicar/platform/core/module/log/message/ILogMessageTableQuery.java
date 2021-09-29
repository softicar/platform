package com.softicar.platform.core.module.log.message;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.level.AGLogLevel;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryColumn;
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
public interface ILogMessageTableQuery extends IDbQuery<ILogMessageTableQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGLogLevel> LEVEL_COLUMN = new DbQueryTableStubColumn<>(IRow::getLevel, "level", AGLogLevel.TABLE, CoreI18n.LOG_LEVEL);
	IDbQueryColumn<IRow, String> LOG_TEXT_COLUMN = new DbQueryColumn<>(IRow::getLogText, "logText", SqlValueTypes.STRING, CoreI18n.LOG_MESSAGE);
	IDbQueryColumn<IRow, DayTime> LOG_TIME_COLUMN = new DbQueryColumn<>(IRow::getLogTime, "logTime", SqlValueTypes.DAY_TIME, CoreI18n.LOG_TIME);
	IDbQueryColumn<IRow, DayTime> NOTIFICATION_TIME_COLUMN = new DbQueryColumn<>(IRow::getNotificationTime, "notificationTime", SqlValueTypes.DAY_TIME, CoreI18n.NOTIFICATION_TIME);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGLogLevel getLevel();
		String getLogText();
		DayTime getLogTime();
		DayTime getNotificationTime();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		ILogProcessSetter createQuery();
	}

	interface ILogProcessSetter {

		ILogMessageTableQuery setLogProcess(AGLogProcess logProcess);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(4);

			public Factory() {

				this.columns.add(LEVEL_COLUMN);
				this.columns.add(LOG_TEXT_COLUMN);
				this.columns.add(LOG_TIME_COLUMN);
				this.columns.add(NOTIFICATION_TIME_COLUMN);
			}

			@Override
			public ILogProcessSetter createQuery() {

				return new Query().new LogProcessSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private AGLogProcess logProcess;
		}

		private static class Query extends AbstractDbQuery<IRow> implements ILogMessageTableQuery {

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

			public class LogProcessSetter implements ILogProcessSetter {

				@Override
				public final ILogMessageTableQuery setLogProcess(AGLogProcess logProcess) {

					Query.this.parameters.logProcess = logProcess;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(LEVEL_COLUMN);
					addIdentifier("logMessage", AGLogMessage.LEVEL);
					addToken(SqlKeyword.AS);
					addIdentifier("level");
					SELECT(LOG_TEXT_COLUMN);
					addIdentifier("logMessage", AGLogMessage.LOG_TEXT);
					addToken(SqlKeyword.AS);
					addIdentifier("logText");
					SELECT(LOG_TIME_COLUMN);
					addIdentifier("logMessage", AGLogMessage.LOG_TIME);
					addToken(SqlKeyword.AS);
					addIdentifier("logTime");
					SELECT(NOTIFICATION_TIME_COLUMN);
					addIdentifier("logMessage", AGLogMessage.NOTIFICATION_TIME);
					addToken(SqlKeyword.AS);
					addIdentifier("notificationTime");
					FROM();
					addIdentifier(AGLogMessage.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("logMessage");
					WHERE();
					addIdentifier("logMessage", AGLogMessage.PROCESS);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.logProcess);
					ORDER_BY();
					addIdentifier("logMessage", AGLogMessage.LOG_TIME);
					addToken(SqlKeyword.DESC);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGLogLevel level;
			private final String logText;
			private final DayTime logTime;
			private final DayTime notificationTime;

			private Row(ILogMessageTableQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.level = LEVEL_COLUMN.loadValue(select, resultSet);
				this.logText = LOG_TEXT_COLUMN.loadValue(select, resultSet);
				this.logTime = LOG_TIME_COLUMN.loadValue(select, resultSet);
				this.notificationTime = NOTIFICATION_TIME_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGLogLevel getLevel() {

				return this.level;
			}

			@Override
			public String getLogText() {

				return this.logText;
			}

			@Override
			public DayTime getLogTime() {

				return this.logTime;
			}

			@Override
			public DayTime getNotificationTime() {

				return this.notificationTime;
			}
		}
	}
}

