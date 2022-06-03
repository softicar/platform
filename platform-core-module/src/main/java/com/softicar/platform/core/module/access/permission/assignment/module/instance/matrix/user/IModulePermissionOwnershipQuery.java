package com.softicar.platform.core.module.access.permission.assignment.module.instance.matrix.user;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.permission.assignment.module.instance.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
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
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IModulePermissionOwnershipQuery extends IDbQuery<IModulePermissionOwnershipQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGModuleInstancePermissionAssignment> OWNERSHIP_COLUMN = new DbQueryTableColumn<>(IRow::getOwnership, "ownership", AGModuleInstancePermissionAssignment.TABLE);
	IDbQueryTableColumn<IRow, AGModuleInstance> INSTANCE_COLUMN = new DbQueryTableColumn<>(IRow::getInstance, "instance", AGModuleInstance.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- METHODS -------------------------------- //

	IModulePermissionOwnershipQuery setOnlyActive(Boolean onlyActive);
	IModulePermissionOwnershipQuery setUser(AGUser user);
	IModulePermissionOwnershipQuery setModuleUuid(AGUuid moduleUuid);

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGModuleInstancePermissionAssignment getOwnership();
		AGModuleInstance getInstance();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IModulePermissionOwnershipQuery createQuery();
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(2);

			public Factory() {

				this.columns.add(OWNERSHIP_COLUMN);
				this.columns.add(INSTANCE_COLUMN);
			}

			@Override
			public IModulePermissionOwnershipQuery createQuery() {

				return new Query();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private Boolean onlyActive;
			private AGUser user;
			private AGUuid moduleUuid;
		}

		private static class Query extends AbstractDbQuery<IRow> implements IModulePermissionOwnershipQuery {

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

			public IModulePermissionOwnershipQuery setOnlyActive(Boolean onlyActive) {

				this.parameters.onlyActive = onlyActive;
				return this;
			}

			public IModulePermissionOwnershipQuery setUser(AGUser user) {

				this.parameters.user = user;
				return this;
			}

			public IModulePermissionOwnershipQuery setModuleUuid(AGUuid moduleUuid) {

				this.parameters.moduleUuid = moduleUuid;
				return this;
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(OWNERSHIP_COLUMN);
					addTableColumns("ownership", AGModuleInstancePermissionAssignment.TABLE, "ownership");
					SELECT(INSTANCE_COLUMN);
					addTableColumns("instance", AGModuleInstance.TABLE, "instance");
					FROM();
					addIdentifier(AGModuleInstancePermissionAssignment.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("ownership");
					JOIN(null);
					addIdentifier(AGModuleInstance.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("instance");
					ON();
					addIdentifier("ownership", AGModuleInstancePermissionAssignment.MODULE_INSTANCE);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("instance", AGModuleInstance.ID);
					if(parameters.onlyActive == null || parameters.onlyActive) {
						WHERE();
						addIdentifier("ownership", AGModuleInstancePermissionAssignment.ACTIVE);
						WHERE();
						addIdentifier("instance", AGModuleInstance.ACTIVE);
					}

					if(parameters.user != null) {
						WHERE();
						addIdentifier("ownership", AGModuleInstancePermissionAssignment.USER);
						addToken(SqlSymbol.EQUAL);
						addParameter(parameters.user);
					}

					if(parameters.moduleUuid != null) {
						WHERE();
						addIdentifier("instance", AGModuleInstance.MODULE_UUID);
						addToken(SqlSymbol.EQUAL);
						addParameter(parameters.moduleUuid);
					}
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGModuleInstancePermissionAssignment ownership;
			private final AGModuleInstance instance;

			private Row(IModulePermissionOwnershipQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.ownership = OWNERSHIP_COLUMN.loadValue(select, resultSet);
				this.instance = INSTANCE_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGModuleInstancePermissionAssignment getOwnership() {

				return this.ownership;
			}

			@Override
			public AGModuleInstance getInstance() {

				return this.instance;
			}
		}
	}
}

