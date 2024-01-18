package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.uuid.AGUuid;
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
public interface IProgramExecutionsQuery extends IDbQuery<IProgramExecutionsQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryColumn<IRow, Long> ID_COLUMN = new DbQueryColumn<>(IRow::getId, "id", SqlValueTypes.LONG);
	IDbQueryTableColumn<IRow, AGProgram> PROGRAM_COLUMN = new DbQueryTableStubColumn<>(IRow::getProgram, "program", AGProgram.TABLE);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		Long getId();
		AGProgram getProgram();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IProgramExecutionsQuery createQuery();
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(2);

			public Factory() {

				this.columns.add(ID_COLUMN);
				this.columns.add(PROGRAM_COLUMN);
			}

			@Override
			public IProgramExecutionsQuery createQuery() {

				return new Query();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Query extends AbstractDbQuery<IRow> implements IProgramExecutionsQuery {

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
					addIdentifier("programExecution", AGProgramExecution.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("id");
					SELECT(PROGRAM_COLUMN);
					addIdentifier("program", AGProgram.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("program");
					FROM();
					addIdentifier(AGProgramExecution.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("programExecution");
					JOIN(null);
					addIdentifier(AGUuid.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("programUuid");
					ON();
					addIdentifier("programExecution", AGProgramExecution.PROGRAM_UUID);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("programUuid", AGUuid.ID);
					JOIN(null);
					addIdentifier(AGProgram.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("program");
					ON();
					addIdentifier("program", AGProgram.PROGRAM_UUID);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("programUuid", AGUuid.ID);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final Long id;
			private final AGProgram program;

			private Row(IProgramExecutionsQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.id = ID_COLUMN.loadValue(select, resultSet);
				this.program = PROGRAM_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public Long getId() {

				return this.id;
			}

			@Override
			public AGProgram getProgram() {

				return this.program;
			}
		}
	}
}

