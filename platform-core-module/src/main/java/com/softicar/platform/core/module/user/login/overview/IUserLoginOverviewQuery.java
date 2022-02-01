package com.softicar.platform.core.module.user.login.overview;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
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
public interface IUserLoginOverviewQuery extends IDbQuery<IUserLoginOverviewQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryColumn<IRow, Integer> ID_COLUMN = new DbQueryColumn<>(IRow::getId, "id", SqlValueTypes.INTEGER);
	IDbQueryColumn<IRow, String> LOGIN_NAME_COLUMN = new DbQueryColumn<>(IRow::getLoginName, "loginName", SqlValueTypes.STRING);
	IDbQueryTableColumn<IRow, AGUser> USER_NAME_COLUMN = new DbQueryTableStubColumn<>(IRow::getUserName, "userName", AGUser.TABLE);
	IDbQueryColumn<IRow, String> EMAIL_ADDRESS_COLUMN = new DbQueryColumn<>(IRow::getEmailAddress, "emailAddress", SqlValueTypes.STRING);
	IDbQueryColumn<IRow, DayTime> LAST_LOGIN_AT_COLUMN = new DbQueryColumn<>(IRow::getLastLoginAt, "lastLoginAt", SqlValueTypes.DAY_TIME);
	IDbQueryColumn<IRow, DayTime> DAYS_PASSED_COLUMN = new DbQueryColumn<>(IRow::getDaysPassed, "daysPassed", SqlValueTypes.DAY_TIME);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		Integer getId();
		String getLoginName();
		AGUser getUserName();
		String getEmailAddress();
		DayTime getLastLoginAt();
		DayTime getDaysPassed();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IUserLoginOverviewQuery createQuery();
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
				this.columns.add(LAST_LOGIN_AT_COLUMN);
				this.columns.add(DAYS_PASSED_COLUMN);
			}

			@Override
			public IUserLoginOverviewQuery createQuery() {

				return new Query();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Query extends AbstractDbQuery<IRow> implements IUserLoginOverviewQuery {

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
					SELECT(LAST_LOGIN_AT_COLUMN);
					addToken(new DbSqlRawToken("MAX"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("loginLog", AGUserLoginLog.LOGIN_AT);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AS);
					addIdentifier("lastLoginAt");
					SELECT(DAYS_PASSED_COLUMN);
					addToken(new DbSqlRawToken("MAX"));
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("loginLog", AGUserLoginLog.LOGIN_AT);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.AS);
					addIdentifier("daysPassed");
					FROM();
					addIdentifier(AGUser.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("user");
					JOIN(SqlKeyword.LEFT);
					addIdentifier(AGUserLoginLog.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("loginLog");
					ON();
					addIdentifier("loginLog", AGUserLoginLog.USER);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("user", AGUser.ID);
					WHERE();
					addIdentifier("user", AGUser.ACTIVE);
					WHERE();
					addToken(SqlKeyword.NOT);
					addIdentifier("user", AGUser.SYSTEM_USER);
					GROUP_BY();
					addIdentifier("user", AGUser.ID);
					ORDER_BY();
					addIdentifier("lastLoginAt");
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final Integer id;
			private final String loginName;
			private final AGUser userName;
			private final String emailAddress;
			private final DayTime lastLoginAt;
			private final DayTime daysPassed;

			private Row(IUserLoginOverviewQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.id = ID_COLUMN.loadValue(select, resultSet);
				this.loginName = LOGIN_NAME_COLUMN.loadValue(select, resultSet);
				this.userName = USER_NAME_COLUMN.loadValue(select, resultSet);
				this.emailAddress = EMAIL_ADDRESS_COLUMN.loadValue(select, resultSet);
				this.lastLoginAt = LAST_LOGIN_AT_COLUMN.loadValue(select, resultSet);
				this.daysPassed = DAYS_PASSED_COLUMN.loadValue(select, resultSet);
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
			public DayTime getLastLoginAt() {

				return this.lastLoginAt;
			}

			@Override
			public DayTime getDaysPassed() {

				return this.daysPassed;
			}
		}
	}
}

