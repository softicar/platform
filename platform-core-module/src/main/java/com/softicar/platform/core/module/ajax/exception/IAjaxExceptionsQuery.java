package com.softicar.platform.core.module.ajax.exception;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
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
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IAjaxExceptionsQuery extends IDbQuery<IAjaxExceptionsQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryColumn<IRow, DayTime> DATE_COLUMN = new DbQueryColumn<>(IRow::getDate, "date", SqlValueTypes.DAY_TIME);
	IDbQueryTableColumn<IRow, AGUser> USER_COLUMN = new DbQueryTableStubColumn<>(IRow::getUser, "user", AGUser.TABLE);
	IDbQueryColumn<IRow, String> TYPE_COLUMN = new DbQueryColumn<>(IRow::getType, "type", SqlValueTypes.STRING);
	IDbQueryColumn<IRow, String> TEXT_COLUMN = new DbQueryColumn<>(IRow::getText, "text", SqlValueTypes.STRING);
	IDbQueryTableColumn<IRow, AGAjaxException> AJAX_EXCEPTION_COLUMN = new DbQueryTableColumn<>(IRow::getAjaxException, "ajaxException", AGAjaxException.TABLE);
	IDbQueryTableColumn<IRow, AGAjaxSession> SESSION_COLUMN = new DbQueryTableStubColumn<>(IRow::getSession, "session", AGAjaxSession.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		DayTime getDate();
		AGUser getUser();
		String getType();
		String getText();
		AGAjaxException getAjaxException();
		AGAjaxSession getSession();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IAjaxExceptionsQuery createQuery();
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(6);

			public Factory() {

				this.columns.add(DATE_COLUMN);
				this.columns.add(USER_COLUMN);
				this.columns.add(TYPE_COLUMN);
				this.columns.add(TEXT_COLUMN);
				this.columns.add(AJAX_EXCEPTION_COLUMN);
				this.columns.add(SESSION_COLUMN);
			}

			@Override
			public IAjaxExceptionsQuery createQuery() {

				return new Query();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Query extends AbstractDbQuery<IRow> implements IAjaxExceptionsQuery {

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

					SELECT(DATE_COLUMN);
					addIdentifier("exception", AGAjaxException.EXCEPTION_DATE);
					addToken(SqlKeyword.AS);
					addIdentifier("date");
					SELECT(USER_COLUMN);
					addIdentifier("exception", AGAjaxException.USER);
					addToken(SqlKeyword.AS);
					addIdentifier("user");
					SELECT(TYPE_COLUMN);
					addIdentifier("exception", AGAjaxException.EXCEPTION_TYPE);
					addToken(SqlKeyword.AS);
					addIdentifier("type");
					SELECT(TEXT_COLUMN);
					addIdentifier("exception", AGAjaxException.EXCEPTION_TEXT);
					addToken(SqlKeyword.AS);
					addIdentifier("text");
					SELECT(AJAX_EXCEPTION_COLUMN);
					addTableColumns("exception", AGAjaxException.TABLE, "ajaxException");
					SELECT(SESSION_COLUMN);
					addIdentifier("exception", AGAjaxException.SESSION);
					addToken(SqlKeyword.AS);
					addIdentifier("session");
					FROM();
					addIdentifier(AGAjaxException.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("exception");
					ORDER_BY();
					addIdentifier("exception", AGAjaxException.ID);
					addToken(SqlKeyword.DESC);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final DayTime date;
			private final AGUser user;
			private final String type;
			private final String text;
			private final AGAjaxException ajaxException;
			private final AGAjaxSession session;

			private Row(IAjaxExceptionsQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.date = DATE_COLUMN.loadValue(select, resultSet);
				this.user = USER_COLUMN.loadValue(select, resultSet);
				this.type = TYPE_COLUMN.loadValue(select, resultSet);
				this.text = TEXT_COLUMN.loadValue(select, resultSet);
				this.ajaxException = AJAX_EXCEPTION_COLUMN.loadValue(select, resultSet);
				this.session = SESSION_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public DayTime getDate() {

				return this.date;
			}

			@Override
			public AGUser getUser() {

				return this.user;
			}

			@Override
			public String getType() {

				return this.type;
			}

			@Override
			public String getText() {

				return this.text;
			}

			@Override
			public AGAjaxException getAjaxException() {

				return this.ajaxException;
			}

			@Override
			public AGAjaxSession getSession() {

				return this.session;
			}
		}
	}
}

