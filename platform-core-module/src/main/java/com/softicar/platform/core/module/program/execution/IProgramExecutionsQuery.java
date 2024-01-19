package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.user.AGUser;
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

	IDbQueryColumn<IRow, Long> ID_COLUMN = new DbQueryColumn<>(IRow::getId, "id", SqlValueTypes.LONG, CoreI18n.ID);
	IDbQueryTableColumn<IRow, AGProgram> PROGRAM_COLUMN = new DbQueryTableStubColumn<>(IRow::getProgram, "program", AGProgram.TABLE, CoreI18n.PROGRAM);
	IDbQueryColumn<IRow, Long> STATUS_COLUMN = new DbQueryColumn<>(IRow::getStatus, "status", SqlValueTypes.LONG, CoreI18n.STATUS);
	IDbQueryColumn<IRow, Boolean> FAILED_COLUMN = new DbQueryColumn<>(IRow::getFailed, "failed", SqlValueTypes.BOOLEAN, CoreI18n.FAILED);
	IDbQueryColumn<IRow, Long> RUNTIME_COLUMN = new DbQueryColumn<>(IRow::getRuntime, "runtime", SqlValueTypes.LONG, CoreI18n.RUNTIME);
	IDbQueryColumn<IRow, DayTime> STARTED_AT_COLUMN = new DbQueryColumn<>(IRow::getStartedAt, "startedAt", SqlValueTypes.DAY_TIME, CoreI18n.STARTED_AT);
	IDbQueryColumn<IRow, DayTime> TERMINATED_AT_COLUMN = new DbQueryColumn<>(IRow::getTerminatedAt, "terminatedAt", SqlValueTypes.DAY_TIME, CoreI18n.TERMINATED_AT);
	IDbQueryColumn<IRow, Long> OUTPUT_COLUMN = new DbQueryColumn<>(IRow::getOutput, "output", SqlValueTypes.LONG, CoreI18n.OUTPUT);
	IDbQueryTableColumn<IRow, AGUser> QUEUED_BY_COLUMN = new DbQueryTableStubColumn<>(IRow::getQueuedBy, "queuedBy", AGUser.TABLE, CoreI18n.QUEUED_BY);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		Long getId();
		AGProgram getProgram();
		Long getStatus();
		Boolean getFailed();
		Long getRuntime();
		DayTime getStartedAt();
		DayTime getTerminatedAt();
		Long getOutput();
		AGUser getQueuedBy();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IProgramExecutionsQuery createQuery();
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(9);

			public Factory() {

				this.columns.add(ID_COLUMN);
				this.columns.add(PROGRAM_COLUMN);
				this.columns.add(STATUS_COLUMN);
				this.columns.add(FAILED_COLUMN);
				this.columns.add(RUNTIME_COLUMN);
				this.columns.add(STARTED_AT_COLUMN);
				this.columns.add(TERMINATED_AT_COLUMN);
				this.columns.add(OUTPUT_COLUMN);
				this.columns.add(QUEUED_BY_COLUMN);
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
					SELECT(STATUS_COLUMN);
					addIdentifier("programExecution", AGProgramExecution.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("status");
					SELECT(FAILED_COLUMN);
					addIdentifier("programExecution", AGProgramExecution.FAILED);
					addToken(SqlKeyword.AS);
					addIdentifier("failed");
					SELECT(RUNTIME_COLUMN);
					addIdentifier("programExecution", AGProgramExecution.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("runtime");
					SELECT(STARTED_AT_COLUMN);
					addIdentifier("programExecution", AGProgramExecution.STARTED_AT);
					addToken(SqlKeyword.AS);
					addIdentifier("startedAt");
					SELECT(TERMINATED_AT_COLUMN);
					addIdentifier("programExecution", AGProgramExecution.TERMINATED_AT);
					addToken(SqlKeyword.AS);
					addIdentifier("terminatedAt");
					SELECT(OUTPUT_COLUMN);
					addIdentifier("programExecution", AGProgramExecution.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("output");
					SELECT(QUEUED_BY_COLUMN);
					addIdentifier("programExecution", AGProgramExecution.QUEUED_BY);
					addToken(SqlKeyword.AS);
					addIdentifier("queuedBy");
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
			private final Long status;
			private final Boolean failed;
			private final Long runtime;
			private final DayTime startedAt;
			private final DayTime terminatedAt;
			private final Long output;
			private final AGUser queuedBy;

			private Row(IProgramExecutionsQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.id = ID_COLUMN.loadValue(select, resultSet);
				this.program = PROGRAM_COLUMN.loadValue(select, resultSet);
				this.status = STATUS_COLUMN.loadValue(select, resultSet);
				this.failed = FAILED_COLUMN.loadValue(select, resultSet);
				this.runtime = RUNTIME_COLUMN.loadValue(select, resultSet);
				this.startedAt = STARTED_AT_COLUMN.loadValue(select, resultSet);
				this.terminatedAt = TERMINATED_AT_COLUMN.loadValue(select, resultSet);
				this.output = OUTPUT_COLUMN.loadValue(select, resultSet);
				this.queuedBy = QUEUED_BY_COLUMN.loadValue(select, resultSet);
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

			@Override
			public Long getStatus() {

				return this.status;
			}

			@Override
			public Boolean getFailed() {

				return this.failed;
			}

			@Override
			public Long getRuntime() {

				return this.runtime;
			}

			@Override
			public DayTime getStartedAt() {

				return this.startedAt;
			}

			@Override
			public DayTime getTerminatedAt() {

				return this.terminatedAt;
			}

			@Override
			public Long getOutput() {

				return this.output;
			}

			@Override
			public AGUser getQueuedBy() {

				return this.queuedBy;
			}
		}
	}
}

