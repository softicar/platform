package com.softicar.platform.core.module.permission.assignment.matrix.user;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
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

	IDbQueryTableColumn<IRow, AGModuleInstancePermissionAssignment> ASSIGNMENT_COLUMN = new DbQueryTableColumn<>(IRow::getAssignment, "assignment", AGModuleInstancePermissionAssignment.TABLE);
	IDbQueryTableColumn<IRow, AGModuleInstanceBase> INSTANCE_BASE_COLUMN = new DbQueryTableColumn<>(IRow::getInstanceBase, "instanceBase", AGModuleInstanceBase.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- METHODS -------------------------------- //

	IModulePermissionOwnershipQuery setOnlyActive(Boolean onlyActive);
	IModulePermissionOwnershipQuery setUser(AGUser user);
	IModulePermissionOwnershipQuery setModuleUuid(AGUuid moduleUuid);

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGModuleInstancePermissionAssignment getAssignment();
		AGModuleInstanceBase getInstanceBase();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IModulePermissionOwnershipQuery createQuery();
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(2);

			public Factory() {

				this.columns.add(ASSIGNMENT_COLUMN);
				this.columns.add(INSTANCE_BASE_COLUMN);
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

					SELECT(ASSIGNMENT_COLUMN);
					addTableColumns("assignment", AGModuleInstancePermissionAssignment.TABLE, "assignment");
					SELECT(INSTANCE_BASE_COLUMN);
					addTableColumns("instanceBase", AGModuleInstanceBase.TABLE, "instanceBase");
					FROM();
					addIdentifier(AGModuleInstancePermissionAssignment.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("assignment");
					JOIN(null);
					addIdentifier(AGModuleInstanceBase.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("instanceBase");
					ON();
					addIdentifier("assignment", AGModuleInstancePermissionAssignment.MODULE_INSTANCE_BASE);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("instanceBase", AGModuleInstanceBase.ID);
					if(parameters.onlyActive == null || parameters.onlyActive) {
						WHERE();
						addIdentifier("assignment", AGModuleInstancePermissionAssignment.ACTIVE);
						WHERE();
						addIdentifier("instanceBase", AGModuleInstanceBase.ACTIVE);
					}

					if(parameters.user != null) {
						WHERE();
						addIdentifier("assignment", AGModuleInstancePermissionAssignment.USER);
						addToken(SqlSymbol.EQUAL);
						addParameter(parameters.user);
					}

					if(parameters.moduleUuid != null) {
						WHERE();
						addIdentifier("instanceBase", AGModuleInstanceBase.MODULE_UUID);
						addToken(SqlSymbol.EQUAL);
						addParameter(parameters.moduleUuid);
					}
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGModuleInstancePermissionAssignment assignment;
			private final AGModuleInstanceBase instanceBase;

			private Row(IModulePermissionOwnershipQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.assignment = ASSIGNMENT_COLUMN.loadValue(select, resultSet);
				this.instanceBase = INSTANCE_BASE_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGModuleInstancePermissionAssignment getAssignment() {

				return this.assignment;
			}

			@Override
			public AGModuleInstanceBase getInstanceBase() {

				return this.instanceBase;
			}
		}
	}
}

