package com.softicar.platform.core.module.user.activity;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.ajax.event.AGAjaxEvent;
import com.softicar.platform.core.module.ajax.event.AGAjaxEventType;
import com.softicar.platform.core.module.user.AGUser;
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
import com.softicar.platform.db.runtime.select.DbSqlRawToken;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IUserActivityQuery extends IDbQuery<IUserActivityQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryColumn<IRow, Integer> ID_COLUMN = new DbQueryColumn<>(IRow::getId, "id", SqlValueTypes.INTEGER);
	IDbQueryColumn<IRow, String> LOGIN_NAME_COLUMN = new DbQueryColumn<>(IRow::getLoginName, "loginName", SqlValueTypes.STRING);
	IDbQueryTableColumn<IRow, AGUser> USER_NAME_COLUMN = new DbQueryTableStubColumn<>(IRow::getUserName, "userName", AGUser.TABLE);
	IDbQueryColumn<IRow, String> EMAIL_ADDRESS_COLUMN = new DbQueryColumn<>(IRow::getEmailAddress, "emailAddress", SqlValueTypes.STRING);
	IDbQueryColumn<IRow, DayTime> LAST_PAGE_CREATION_COLUMN = new DbQueryColumn<>(IRow::getLastPageCreation, "lastPageCreation", SqlValueTypes.DAY_TIME);
	IDbQueryColumn<IRow, DayTime> DAYS_AGO_COLUMN = new DbQueryColumn<>(IRow::getDaysAgo, "daysAgo", SqlValueTypes.DAY_TIME);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		Integer getId();
		String getLoginName();
		AGUser getUserName();
		String getEmailAddress();
		DayTime getLastPageCreation();
		DayTime getDaysAgo();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IThresholdSetter createQuery();
	}

	interface IThresholdSetter {

		ITypeSetter setThreshold(DayTime threshold);
	}

	interface ITypeSetter {

		IUserActivityQuery setType(AGAjaxEventType type);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(6);

			public Factory() {

				this.columns.add(ID_COLUMN);
				this.columns.add(LOGIN_NAME_COLUMN);
				this.columns.add(USER_NAME_COLUMN);
				this.columns.add(EMAIL_ADDRESS_COLUMN);
				this.columns.add(LAST_PAGE_CREATION_COLUMN);
				this.columns.add(DAYS_AGO_COLUMN);
			}

			@Override
			public IThresholdSetter createQuery() {

				return new Query().new ThresholdSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private DayTime threshold;
			private AGAjaxEventType type;
		}

		private static class Query extends AbstractDbQuery<IRow> implements IUserActivityQuery {

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

			public class ThresholdSetter implements IThresholdSetter {

				@Override
				public final ITypeSetter setThreshold(DayTime threshold) {

					Query.this.parameters.threshold = threshold;
					return Query.this.new TypeSetter();
				}
			}

			public class TypeSetter implements ITypeSetter {

				@Override
				public final IUserActivityQuery setType(AGAjaxEventType type) {

					Query.this.parameters.type = type;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(ID_COLUMN);
					addIdentifier("user", AGUser.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("id");
					SELECT(LOGIN_NAME_COLUMN);
					addIdentifier("user", AGUser.LOGIN_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("loginName");
					SELECT(USER_NAME_COLUMN);
					addIdentifier("user", AGUser.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("userName");
					SELECT(EMAIL_ADDRESS_COLUMN);
					addIdentifier("user", AGUser.EMAIL_ADDRESS);
					addToken(SqlKeyword.AS);
					addIdentifier("emailAddress");
					SELECT(LAST_PAGE_CREATION_COLUMN);
					addIdentifier("lastAccess", "date");
					addToken(SqlKeyword.AS);
					addIdentifier("lastPageCreation");
					SELECT(DAYS_AGO_COLUMN);
					addIdentifier("lastAccess", "date");
					addToken(SqlKeyword.AS);
					addIdentifier("daysAgo");
					FROM();
					addIdentifier(AGUser.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("user");
					JOIN(SqlKeyword.LEFT);
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					startSubSelect();
					SELECT();
					addIdentifier("event", AGAjaxEvent.USER);
					addToken(SqlKeyword.AS);
					addIdentifier("user");
					SELECT();
					addToken(new DbSqlRawToken("MAX"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("event", AGAjaxEvent.EVENT_DATE);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AS);
					addIdentifier("date");
					FROM();
					addIdentifier(AGAjaxEvent.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("event");
					WHERE();
					addIdentifier("event", AGAjaxEvent.TYPE);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.type);
					GROUP_BY();
					addIdentifier("event", AGAjaxEvent.USER);
					finishSubSelect();
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AS);
					addIdentifier("lastAccess");
					ON();
					addIdentifier("lastAccess", "user");
					addToken(SqlSymbol.EQUAL);
					addIdentifier("user", AGUser.ID);
					addToken(SqlKeyword.AND);
					addIdentifier("lastAccess", "date");
					addToken(SqlSymbol.LESS);
					addParameter(parameters.threshold);
					WHERE();
					addIdentifier("user", AGUser.ACTIVE);
					ORDER_BY();
					addIdentifier("lastPageCreation");
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final Integer id;
			private final String loginName;
			private final AGUser userName;
			private final String emailAddress;
			private final DayTime lastPageCreation;
			private final DayTime daysAgo;

			private Row(IUserActivityQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.id = ID_COLUMN.loadValue(select, resultSet);
				this.loginName = LOGIN_NAME_COLUMN.loadValue(select, resultSet);
				this.userName = USER_NAME_COLUMN.loadValue(select, resultSet);
				this.emailAddress = EMAIL_ADDRESS_COLUMN.loadValue(select, resultSet);
				this.lastPageCreation = LAST_PAGE_CREATION_COLUMN.loadValue(select, resultSet);
				this.daysAgo = DAYS_AGO_COLUMN.loadValue(select, resultSet);
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
			public String getLoginName() {

				return this.loginName;
			}

			@Override
			public AGUser getUserName() {

				return this.userName;
			}

			@Override
			public String getEmailAddress() {

				return this.emailAddress;
			}

			@Override
			public DayTime getLastPageCreation() {

				return this.lastPageCreation;
			}

			@Override
			public DayTime getDaysAgo() {

				return this.daysAgo;
			}
		}
	}
}

