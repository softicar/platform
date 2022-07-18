package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.query.AbstractDbQuery;
import com.softicar.platform.db.runtime.query.AbstractDbQueryRow;
import com.softicar.platform.db.runtime.query.DbQueryColumn;
import com.softicar.platform.db.runtime.query.IDbQuery;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.IDbQueryFactory;
import com.softicar.platform.db.runtime.query.IDbQueryRow;
import com.softicar.platform.db.runtime.query.builder.AbstractDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IUserSpecificTableConfigurationPersistenceTestQuery extends IDbQuery<IUserSpecificTableConfigurationPersistenceTestQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryColumn<IRow, String> LOGIN_NAME_COLUMN = new DbQueryColumn<>(IRow::getLoginName, "loginName", SqlValueTypes.STRING);
	IDbQueryColumn<IRow, String> EMAIL_ADDRESS_COLUMN = new DbQueryColumn<>(IRow::getEmailAddress, "emailAddress", SqlValueTypes.STRING);
	IDbQueryColumn<IRow, String> LAST_NAME_COLUMN = new DbQueryColumn<>(IRow::getLastName, "lastName", SqlValueTypes.STRING);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		String getLoginName();
		String getEmailAddress();
		String getLastName();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		ISelectLastNameSetter createQuery();
	}

	interface ISelectLastNameSetter {

		IUserSpecificTableConfigurationPersistenceTestQuery setSelectLastName(Boolean selectLastName);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(3);

			public Factory() {

				this.columns.add(LOGIN_NAME_COLUMN);
				this.columns.add(EMAIL_ADDRESS_COLUMN);
				this.columns.add(LAST_NAME_COLUMN);
			}

			@Override
			public ISelectLastNameSetter createQuery() {

				return new Query().new SelectLastNameSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private Boolean selectLastName;
		}

		private static class Query extends AbstractDbQuery<IRow> implements IUserSpecificTableConfigurationPersistenceTestQuery {

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

			public class SelectLastNameSetter implements ISelectLastNameSetter {

				@Override
				public final IUserSpecificTableConfigurationPersistenceTestQuery setSelectLastName(Boolean selectLastName) {

					Query.this.parameters.selectLastName = selectLastName;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(LOGIN_NAME_COLUMN);
					addIdentifier("user", AGUser.LOGIN_NAME);
					addToken(SqlKeyword.AS);
					addIdentifier("loginName");
					SELECT(EMAIL_ADDRESS_COLUMN);
					addIdentifier("user", AGUser.EMAIL_ADDRESS);
					addToken(SqlKeyword.AS);
					addIdentifier("emailAddress");
					if(parameters.selectLastName) {
						SELECT(LAST_NAME_COLUMN);
						addIdentifier("user", AGUser.LAST_NAME);
						addToken(SqlKeyword.AS);
						addIdentifier("lastName");
					}

					FROM();
					addIdentifier(AGUser.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("user");
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final String loginName;
			private final String emailAddress;
			private final String lastName;

			private Row(IUserSpecificTableConfigurationPersistenceTestQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.loginName = LOGIN_NAME_COLUMN.loadValue(select, resultSet);
				this.emailAddress = EMAIL_ADDRESS_COLUMN.loadValue(select, resultSet);
				this.lastName = LAST_NAME_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public String getLoginName() {

				return this.loginName;
			}

			@Override
			public String getEmailAddress() {

				return this.emailAddress;
			}

			@Override
			public String getLastName() {

				return this.lastName;
			}
		}
	}
}

