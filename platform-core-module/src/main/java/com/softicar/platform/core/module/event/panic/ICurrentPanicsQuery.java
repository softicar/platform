package com.softicar.platform.core.module.event.panic;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.level.AGLogLevel;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryColumn;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.IDbQueryFactory;
import com.softicar.platform.db.runtime.query.IDbQueryRow;
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
public interface ICurrentPanicsQuery extends IDbQuery<ICurrentPanicsQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryColumn<IRow, Integer> ID_COLUMN = new DbQueryColumn<>(IRow::getId, "id", SqlValueTypes.INTEGER, CoreI18n.PROCESS_ID);
	IDbQueryColumn<IRow, String> CLASS_NAME_COLUMN = new DbQueryColumn<>(IRow::getClassName, "className", SqlValueTypes.STRING, CoreI18n.PROCESS_CLASS_NAME);
	IDbQueryColumn<IRow, Integer> COUNT_COLUMN = new DbQueryColumn<>(IRow::getCount, "count", SqlValueTypes.INTEGER, CoreI18n.COUNT);
	IDbQueryColumn<IRow, DayTime> LOG_TIME_COLUMN = new DbQueryColumn<>(IRow::getLogTime, "logTime", SqlValueTypes.DAY_TIME, CoreI18n.DATE);
	IDbQueryColumn<IRow, String> LOG_TEXT_COLUMN = new DbQueryColumn<>(IRow::getLogText, "logText", SqlValueTypes.STRING, CoreI18n.TEXT);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		Integer getId();
		String getClassName();
		Integer getCount();
		DayTime getLogTime();
		String getLogText();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		ILevelSetter createQuery();
	}

	interface ILevelSetter {

		ICurrentPanicsQuery setLevel(AGLogLevel level);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(5);

			public Factory() {

				this.columns.add(ID_COLUMN);
				this.columns.add(CLASS_NAME_COLUMN);
				this.columns.add(COUNT_COLUMN);
				this.columns.add(LOG_TIME_COLUMN);
				this.columns.add(LOG_TEXT_COLUMN);
			}

			@Override
			public ILevelSetter createQuery() {

				return new Query().new LevelSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private AGLogLevel level;
		}

		private static class Query extends AbstractDbQuery<IRow> implements ICurrentPanicsQuery {

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

			public class LevelSetter implements ILevelSetter {

				@Override
				public final ICurrentPanicsQuery setLevel(AGLogLevel level) {

					Query.this.parameters.level = level;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(ID_COLUMN);
					addIdentifier("process", AGLogProcess.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("id");
					SELECT(CLASS_NAME_COLUMN);
					addIdentifier("process", AGLogProcess.CLASS_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("className");
					SELECT(COUNT_COLUMN);
					addToken(new DbSqlRawToken("COUNT"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("process", AGLogProcess.ID);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AS);
					addIdentifier("count");
					SELECT(LOG_TIME_COLUMN);
					addIdentifier("message", AGLogMessage.LOG_TIME);
					addToken(SqlKeyword.AS);
					addIdentifier("logTime");
					SELECT(LOG_TEXT_COLUMN);
					addIdentifier("message", AGLogMessage.LOG_TEXT);
					addToken(SqlKeyword.AS);
					addIdentifier("logText");
					FROM();
					addIdentifier(AGLogMessage.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("message");
					JOIN(null);
					addIdentifier(AGLogProcess.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("process");
					ON();
					addIdentifier("message", AGLogMessage.PROCESS);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("process", AGLogProcess.ID);
					WHERE();
					addIdentifier("message", AGLogMessage.LEVEL);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.level);
					WHERE();
					addIdentifier("message", AGLogMessage.NOTIFICATION_TIME);
					addToken(SqlKeyword.IS);
					addToken(SqlKeyword.NULL);
					ORDER_BY();
					addIdentifier("count");
					addToken(SqlKeyword.DESC);
					ORDER_BY();
					addIdentifier("message", AGLogMessage.LOG_TIME);
					GROUP_BY();
					addIdentifier("process", AGLogProcess.ID);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final Integer id;
			private final String className;
			private final Integer count;
			private final DayTime logTime;
			private final String logText;

			private Row(ICurrentPanicsQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.id = ID_COLUMN.loadValue(select, resultSet);
				this.className = CLASS_NAME_COLUMN.loadValue(select, resultSet);
				this.count = COUNT_COLUMN.loadValue(select, resultSet);
				this.logTime = LOG_TIME_COLUMN.loadValue(select, resultSet);
				this.logText = LOG_TEXT_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
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
			public Integer getCount() {

				return this.count;
			}

			@Override
			public DayTime getLogTime() {

				return this.logTime;
			}

			@Override
			public String getLogText() {

				return this.logText;
			}
		}
	}
}

