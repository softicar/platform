package com.softicar.platform.core.module.user.notification;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryColumn;
import com.softicar.platform.db.runtime.query.DbQueryTableColumn;
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
public interface INotificationTargetUsersQuery extends IDbQuery<INotificationTargetUsersQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGUser> USER_COLUMN = new DbQueryTableColumn<>(IRow::getUser, "user", AGUser.TABLE);
	IDbQueryColumn<IRow, Integer> ID_COLUMN = new DbQueryColumn<>(IRow::getId, "id", SqlValueTypes.INTEGER, CoreI18n.ID);
	IDbQueryColumn<IRow, String> FIRST_NAME_COLUMN = new DbQueryColumn<>(IRow::getFirstName, "firstName", SqlValueTypes.STRING, CoreI18n.FIRST_NAME);
	IDbQueryColumn<IRow, String> LAST_NAME_COLUMN = new DbQueryColumn<>(IRow::getLastName, "lastName", SqlValueTypes.STRING, CoreI18n.LAST_NAME);
	IDbQueryColumn<IRow, String> LOGIN_NAME_COLUMN = new DbQueryColumn<>(IRow::getLoginName, "loginName", SqlValueTypes.STRING, CoreI18n.LOGIN_NAME);
	IDbQueryColumn<IRow, String> EMAIL_ADDRESS_COLUMN = new DbQueryColumn<>(IRow::getEmailAddress, "emailAddress", SqlValueTypes.STRING, CoreI18n.EMAIL_ADDRESS);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGUser getUser();
		Integer getId();
		String getFirstName();
		String getLastName();
		String getLoginName();
		String getEmailAddress();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IActiveSetter createQuery();
	}

	interface IActiveSetter {

		ISystemUserSetter setActive(Boolean active);
	}

	interface ISystemUserSetter {

		INotificationTargetUsersQuery setSystemUser(Boolean systemUser);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(6);

			public Factory() {

				this.columns.add(USER_COLUMN);
				this.columns.add(ID_COLUMN);
				this.columns.add(FIRST_NAME_COLUMN);
				this.columns.add(LAST_NAME_COLUMN);
				this.columns.add(LOGIN_NAME_COLUMN);
				this.columns.add(EMAIL_ADDRESS_COLUMN);
			}

			@Override
			public IActiveSetter createQuery() {

				return new Query().new ActiveSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private Boolean active;
			private Boolean systemUser;
		}

		private static class Query extends AbstractDbQuery<IRow> implements INotificationTargetUsersQuery {

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

			public class ActiveSetter implements IActiveSetter {

				@Override
				public final ISystemUserSetter setActive(Boolean active) {

					Query.this.parameters.active = active;
					return Query.this.new SystemUserSetter();
				}
			}

			public class SystemUserSetter implements ISystemUserSetter {

				@Override
				public final INotificationTargetUsersQuery setSystemUser(Boolean systemUser) {

					Query.this.parameters.systemUser = systemUser;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(USER_COLUMN);
					addTableColumns("user", AGUser.TABLE, "user");
					SELECT(ID_COLUMN);
					addIdentifier("user", AGUser.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("id");
					SELECT(FIRST_NAME_COLUMN);
					addIdentifier("user", AGUser.FIRST_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("firstName");
					SELECT(LAST_NAME_COLUMN);
					addIdentifier("user", AGUser.LAST_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("lastName");
					SELECT(LOGIN_NAME_COLUMN);
					addIdentifier("user", AGUser.LOGIN_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("loginName");
					SELECT(EMAIL_ADDRESS_COLUMN);
					addIdentifier("user", AGUser.EMAIL_ADDRESS);
					addToken(SqlKeyword.AS);
					addIdentifier("emailAddress");
					FROM();
					addIdentifier(AGUser.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("user");
					WHERE();
					addIdentifier("user", AGUser.EMAIL_ADDRESS);
					addToken(SqlKeyword.LIKE);
					addLiteral("%@%");
					WHERE();
					addIdentifier("user", AGUser.ACTIVE);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.active);
					WHERE();
					addIdentifier("user", AGUser.SYSTEM_USER);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.systemUser);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGUser user;
			private final Integer id;
			private final String firstName;
			private final String lastName;
			private final String loginName;
			private final String emailAddress;

			private Row(INotificationTargetUsersQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.user = USER_COLUMN.loadValue(select, resultSet);
				this.id = ID_COLUMN.loadValue(select, resultSet);
				this.firstName = FIRST_NAME_COLUMN.loadValue(select, resultSet);
				this.lastName = LAST_NAME_COLUMN.loadValue(select, resultSet);
				this.loginName = LOGIN_NAME_COLUMN.loadValue(select, resultSet);
				this.emailAddress = EMAIL_ADDRESS_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGUser getUser() {

				return this.user;
			}

			@Override
			public Integer getId() {

				return this.id;
			}

			@Override
			public String getFirstName() {

				return this.firstName;
			}

			@Override
			public String getLastName() {

				return this.lastName;
			}

			@Override
			public String getLoginName() {

				return this.loginName;
			}

			@Override
			public String getEmailAddress() {

				return this.emailAddress;
			}
		}
	}
}

