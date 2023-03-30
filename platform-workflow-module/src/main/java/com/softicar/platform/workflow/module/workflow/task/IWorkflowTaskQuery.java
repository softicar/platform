package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.transaction.AGTransaction;
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
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.ArrayList;
import java.util.List;

@Generated
@SuppressWarnings("all")
public interface IWorkflowTaskQuery extends IDbQuery<IWorkflowTaskQuery.IRow> {

	// -------------------------------- CONSTANTS -------------------------------- //

	IDbQueryTableColumn<IRow, AGWorkflowItem> ITEM_COLUMN = new DbQueryTableStubColumn<>(IRow::getItem, "item", AGWorkflowItem.TABLE);
	IDbQueryTableColumn<IRow, AGWorkflowTask> TASK_COLUMN = new DbQueryTableStubColumn<>(IRow::getTask, "task", AGWorkflowTask.TABLE);
	IDbQueryTableColumn<IRow, AGWorkflowNode> WORKFLOW_NODE_COLUMN = new DbQueryTableStubColumn<>(IRow::getWorkflowNode, "workflowNode", AGWorkflowNode.TABLE);
	IDbQueryTableColumn<IRow, AGUser> DELEGATED_BY_COLUMN = new DbQueryTableStubColumn<>(IRow::getDelegatedBy, "delegatedBy", AGUser.TABLE);
	IDbQueryColumn<IRow, DayTime> CREATED_AT_COLUMN = new DbQueryColumn<>(IRow::getCreatedAt, "createdAt", SqlValueTypes.DAY_TIME);
	IFactory FACTORY = new Implementation.Factory();

	// -------------------------------- INTERFACES -------------------------------- //

	interface IRow extends IDbQueryRow<IRow> {

		AGWorkflowItem getItem();
		AGWorkflowTask getTask();
		AGWorkflowNode getWorkflowNode();
		AGUser getDelegatedBy();
		DayTime getCreatedAt();
	}

	interface IFactory extends IDbQueryFactory<IRow> {

		IUserSetter createQuery();
	}

	interface IUserSetter {

		IShowMyDelegationsSetter setUser(AGUser user);
	}

	interface IShowMyDelegationsSetter {

		IWorkflowTaskQuery setShowMyDelegations(Boolean showMyDelegations);
	}

	// -------------------------------- IMPLEMENTATION -------------------------------- //

	class Implementation {

		private static class Factory implements IFactory {

			private List<IDbQueryColumn<IRow, ?>> columns = new ArrayList<>(5);

			public Factory() {

				this.columns.add(ITEM_COLUMN);
				this.columns.add(TASK_COLUMN);
				this.columns.add(WORKFLOW_NODE_COLUMN);
				this.columns.add(DELEGATED_BY_COLUMN);
				this.columns.add(CREATED_AT_COLUMN);
			}

			@Override
			public IUserSetter createQuery() {

				return new Query().new UserSetter();
			}

			@Override
			public List<IDbQueryColumn<IRow, ?>> getColumns() {

				return columns;
			}
		}

		private static class Parameters {

			private AGUser user;
			private Boolean showMyDelegations;
		}

		private static class Query extends AbstractDbQuery<IRow> implements IWorkflowTaskQuery {

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

			public class UserSetter implements IUserSetter {

				@Override
				public final IShowMyDelegationsSetter setUser(AGUser user) {

					Query.this.parameters.user = user;
					return Query.this.new ShowMyDelegationsSetter();
				}
			}

			public class ShowMyDelegationsSetter implements IShowMyDelegationsSetter {

				@Override
				public final IWorkflowTaskQuery setShowMyDelegations(Boolean showMyDelegations) {

					Query.this.parameters.showMyDelegations = showMyDelegations;
					return Query.this;
				}
			}

			private class QuerySqlBuilder extends AbstractDbQuerySqlBuilder {

				public void buildOriginalSelect() {

					SELECT(ITEM_COLUMN);
					addIdentifier("item", AGWorkflowItem.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("item");
					SELECT(TASK_COLUMN);
					addIdentifier("task", AGWorkflowTask.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("task");
					SELECT(WORKFLOW_NODE_COLUMN);
					addIdentifier("workflowNode", AGWorkflowNode.ID);
					addToken(SqlKeyword.AS);
					addIdentifier("workflowNode");
					SELECT(DELEGATED_BY_COLUMN);
					addIdentifier("delegation", AGWorkflowTaskDelegation.DELEGATED_BY);
					addToken(SqlKeyword.AS);
					addIdentifier("delegatedBy");
					SELECT(CREATED_AT_COLUMN);
					addIdentifier("transaction", AGTransaction.AT);
					addToken(SqlKeyword.AS);
					addIdentifier("createdAt");
					FROM();
					addIdentifier(AGWorkflowTask.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("task");
					JOIN(null);
					addIdentifier(AGWorkflowItem.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("item");
					ON();
					addIdentifier("task", AGWorkflowTask.WORKFLOW_ITEM);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("item", AGWorkflowItem.ID);
					JOIN(null);
					addIdentifier(AGUser.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("user");
					ON();
					addIdentifier("task", AGWorkflowTask.USER);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("user", AGUser.ID);
					JOIN(null);
					addIdentifier(AGWorkflow.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("workflow");
					ON();
					addIdentifier("item", AGWorkflowItem.WORKFLOW);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("workflow", AGWorkflow.ID);
					JOIN(null);
					addIdentifier(AGWorkflowNode.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("workflowNode");
					ON();
					addIdentifier("item", AGWorkflowItem.WORKFLOW_NODE);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("workflowNode", AGWorkflowNode.ID);
					JOIN(null);
					addIdentifier(AGWorkflowVersion.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("workflowVersion");
					ON();
					addIdentifier("workflowNode", AGWorkflowNode.WORKFLOW_VERSION);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("workflowVersion", AGWorkflowVersion.ID);
					JOIN(SqlKeyword.LEFT);
					addIdentifier(AGTransaction.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("transaction");
					ON();
					addIdentifier("task", AGWorkflowTask.TRANSACTION);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("transaction", AGTransaction.ID);
					JOIN(SqlKeyword.LEFT);
					addIdentifier(AGWorkflowTaskDelegation.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("delegation");
					ON();
					addIdentifier("delegation", AGWorkflowTaskDelegation.WORKFLOW_TASK);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("task", AGWorkflowTask.ID);
					addToken(SqlKeyword.AND);
					addIdentifier("delegation", AGWorkflowTaskDelegation.ACTIVE);
					JOIN(SqlKeyword.LEFT);
					addIdentifier(AGWorkflowTransitionExecution.TABLE);
					addToken(SqlKeyword.AS);
					addIdentifier("execution");
					ON();
					addIdentifier("execution", AGWorkflowTransitionExecution.WORKFLOW_TASK);
					addToken(SqlSymbol.EQUAL);
					addIdentifier("task", AGWorkflowTask.ID);
					GROUP_BY();
					addIdentifier("item", AGWorkflowItem.ID);
					GROUP_BY();
					addIdentifier("task", AGWorkflowTask.ID);
					WHERE();
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("task", AGWorkflowTask.USER);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.user);
					addToken(SqlKeyword.AND);
					addToken(SqlKeyword.CASE);
					addToken(SqlKeyword.WHEN);
					addParameter(parameters.showMyDelegations);
					addToken(SqlKeyword.THEN);
					addLiteral(true);
					addToken(SqlKeyword.ELSE);
					addIdentifier("delegation", AGWorkflowTaskDelegation.WORKFLOW_TASK);
					addToken(SqlKeyword.IS);
					addToken(SqlKeyword.NULL);
					addToken(SqlKeyword.END);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlKeyword.OR);
					addToken(SqlSymbol.LEFT_PARENTHESIS);
					addIdentifier("delegation", AGWorkflowTaskDelegation.TARGET_USER);
					addToken(SqlSymbol.EQUAL);
					addParameter(parameters.user);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					addToken(SqlSymbol.RIGHT_PARENTHESIS);
					WHERE();
					addToken(SqlKeyword.NOT);
					addIdentifier("task", AGWorkflowTask.CLOSED);
					WHERE();
					addIdentifier("task", AGWorkflowTask.NOTIFY);
					WHERE();
					addIdentifier("execution", AGWorkflowTransitionExecution.ID);
					addToken(SqlKeyword.IS);
					addToken(SqlKeyword.NULL);
					ORDER_BY();
					addIdentifier("createdAt");
					addToken(SqlKeyword.DESC);
				}
			}
		}

		private static class Row extends AbstractDbQueryRow<IRow> implements IRow {

			private final AGWorkflowItem item;
			private final AGWorkflowTask task;
			private final AGWorkflowNode workflowNode;
			private final AGUser delegatedBy;
			private final DayTime createdAt;

			private Row(IWorkflowTaskQuery query, IDbSqlSelect select, DbResultSet resultSet) {

				super(query);

				this.item = ITEM_COLUMN.loadValue(select, resultSet);
				this.task = TASK_COLUMN.loadValue(select, resultSet);
				this.workflowNode = WORKFLOW_NODE_COLUMN.loadValue(select, resultSet);
				this.delegatedBy = DELEGATED_BY_COLUMN.loadValue(select, resultSet);
				this.createdAt = CREATED_AT_COLUMN.loadValue(select, resultSet);
			}

			@Override
			public Row getThis() {

				return this;
			}

			@Override
			public AGWorkflowItem getItem() {

				return this.item;
			}

			@Override
			public AGWorkflowTask getTask() {

				return this.task;
			}

			@Override
			public AGWorkflowNode getWorkflowNode() {

				return this.workflowNode;
			}

			@Override
			public AGUser getDelegatedBy() {

				return this.delegatedBy;
			}

			@Override
			public DayTime getCreatedAt() {

				return this.createdAt;
			}
		}
	}
}

