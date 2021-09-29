package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.DbStatements;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.sql.ISqlStatement;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlClauseParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class SqlSelectCore {

	private final List<TableEntry> tableEntries = new ArrayList<>();
	private final List<ExpressionEntry> select = new ArrayList<>();
	private final List<ExpressionEntry> where = new ArrayList<>();
	private final List<ExpressionEntry> having = new ArrayList<>();
	private final List<ExpressionEntry> groupBy = new ArrayList<>();
	private final List<ExpressionEntry> orderBy = new ArrayList<>();
	private OrderDirection currentDirection = OrderDirection.ASCENDING;
	private SqlSelectLock lock = null;
	private PartType currentPartType = PartType.JOIN;
	private TableEntry currentTableEntry;

	public static enum JoinType {
		FROM,
		JOIN,
		LEFT_JOIN
	}

	public static enum PartType {
		SELECT,
		JOIN,
		WHERE,
		HAVING,
		GROUP_BY,
		ORDER_BY
	}

	public int getTableCount() {

		return tableEntries.size();
	}

	public Set<ISqlTable<?>> getTables() {

		return tableEntries//
			.stream()
			.map(entry -> entry.getTable())
			.collect(Collectors.toSet());
	}

	public void join(ISqlTable<?> table, JoinType joinType) {

		this.currentTableEntry = new TableEntry(tableEntries.size(), table, joinType);
		tableEntries.add(currentTableEntry);
		setPartType(PartType.JOIN);
	}

	public void setPartType(PartType partType) {

		this.currentPartType = partType;
	}

	public void setOrderDirection(OrderDirection direction) {

		this.currentDirection = direction;
	}

	public void setLock(SqlSelectLock lock) {

		this.lock = lock;
	}

	public <V> void addLiteralExpression(ISqlExpression0<V> expression) {

		addExpression(Collections.emptyList(), expression);
	}

	public void addExpressionOnLastTable(PartType partType, ISqlExpression1<?, ?> expression) {

		setPartType(partType);
		addExpression(Collections.singletonList(tableEntries.size() - 1), expression);
	}

	public <V> void addExpression(List<Integer> selectedTables, ISqlExpression<V> expression) {

		ExpressionEntry expressionEntry = new ExpressionEntry(expression, tableEntries, selectedTables);

		switch (currentPartType) {
		case SELECT:
			select.add(expressionEntry);
			break;
		case JOIN:
			currentTableEntry.addCondition(expressionEntry);
			break;
		case WHERE:
			where.add(expressionEntry);
			break;
		case HAVING:
			having.add(expressionEntry);
			break;
		case GROUP_BY:
			groupBy.add(expressionEntry);
			break;
		case ORDER_BY:
			if (currentDirection == OrderDirection.ASCENDING) {
				orderBy.add(expressionEntry);
			} else {
				orderBy.add(new ExpressionEntry(new SqlOrderDescendingAdapter<>(expression), tableEntries, selectedTables));
			}
			break;
		}
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		for (TableEntry entry: tableEntries) {
			builder.append(entry.toString()).append("\n");
		}
		builder.append("SELECT\n");
		for (ExpressionEntry entry: select) {
			builder.append(entry.toString()).append("\n");
		}
		builder.append("WHERE\n");
		for (ExpressionEntry entry: where) {
			builder.append(entry.toString()).append("\n");
		}
		return builder.toString();
	}

	PartBuilder _build(int offset, int limit) {

		PartBuilder builder = new PartBuilder();

		builder.addText("SELECT");
		for (int i = 0; i < select.size(); ++i) {
			builder.addText(i == 0? " " : ", ");
			select.get(i).build(builder);
		}
		for (TableEntry tableEntry: tableEntries) {
			tableEntry.build(builder);
		}
		for (int i = 0; i < where.size(); ++i) {
			builder.addText(i == 0? " WHERE " : " AND ");
			where.get(i).build(builder);
		}
		for (int i = 0; i < groupBy.size(); ++i) {
			builder.addText(i == 0? " GROUP BY " : ", ");
			groupBy.get(i).build(builder);
		}
		for (int i = 0; i < having.size(); ++i) {
			builder.addText(i == 0? " HAVING " : " AND ");
			having.get(i).build(builder);
		}
		for (int i = 0; i < orderBy.size(); ++i) {
			builder.addText(i == 0? " ORDER BY " : ", ");
			orderBy.get(i).build(builder);
		}
		if (limit > 0) {
			if (offset > 0) {
				builder.addText(" LIMIT %s, %s", offset, limit);
			} else {
				builder.addText(" LIMIT %s", limit);
			}
		}
		if (lock != null) {
			builder.addText(" " + lock.getText());
		}

		return builder;
	}

	final class PartBuilder implements ISqlClauseBuilder, ISqlStatement {

		private final IDbServerQuirks serverQuirks;
		private final StringBuilder text = new StringBuilder();
		private final List<Object> parameters = new ArrayList<>();
		private List<TableEntry> selectedTableEntries;
		private int tableIndex;
		private int fixedTable;

		public PartBuilder() {

			this.serverQuirks = DbConnections.getServerQuirks();
		}

		@Override
		public String getQuotedName(ISqlField<?, ?> field) {

			return serverQuirks.getQuotedIdentifier(field.getName());
		}

		@Override
		public PartBuilder fixTable() {

			fixedTable++;
			return this;
		}

		@Override
		public PartBuilder unfixTable() {

			fixedTable--;
			return this;
		}

		@Override
		public PartBuilder addField(ISqlField<?, ?> field) {

			this.text//
				.append("t")
				.append(selectedTableEntries.get(tableIndex).getIndex())
				.append('.')
				.append(getQuotedName(field));
			if (fixedTable == 0) {
				++tableIndex;
			}
			return this;
		}

		@Override
		public PartBuilder addParameter(Object parameter) {

			SqlClauseParameters.addToParameterList(this.parameters, parameter);
			return addText("?");
		}

		@Override
		public PartBuilder addParameterPack(Iterable<?> parameters) {

			for (Object parameter: parameters) {
				SqlClauseParameters.addToParameterList(this.parameters, parameter);
			}
			return addText(DbStatements.createQuestionMarkList(parameters));
		}

		@Override
		public PartBuilder addText(String text) {

			this.text.append(text);
			return this;
		}

		@Override
		public PartBuilder addText(String text, Object...args) {

			return addText(String.format(text, args));
		}

		void setSelectedTableEntries(ArrayList<TableEntry> tableEntries) {

			tableIndex = 0;
			selectedTableEntries = tableEntries;
		}

		@Override
		public String getText() {

			return text.toString();
		}

		@Override
		public List<Object> getParameters() {

			return parameters;
		}
	}

	private final static class TableEntry {

		private final List<ExpressionEntry> conditions = new ArrayList<>();
		private final int index;
		private final ISqlTable<?> table;
		private final JoinType joinType;

		@Override
		public String toString() {

			StringBuilder builder = new StringBuilder();
			builder.append(joinType.toString() + " " + table.getFullName().getQuoted());
			builder.append(" [" + Imploder.implode(conditions, ", ") + "]");
			return builder.toString();
		}

		TableEntry(int index, ISqlTable<?> table, JoinType joinType) {

			this.index = index;
			this.table = table;
			this.joinType = joinType;
		}

		void addCondition(ExpressionEntry condition) {

			this.conditions.add(condition);
		}

		void build(PartBuilder builder) {

			switch (joinType) {
			case FROM:
				builder.addText(" FROM ");
				break;
			case JOIN:
				builder.addText(" JOIN ");
				break;
			case LEFT_JOIN:
				builder.addText(" LEFT JOIN ");
				break;
			}

			builder.addText(table.getFullName().getQuoted()).addText(" t" + index);

			for (int i = 0; i < conditions.size(); ++i) {
				builder.addText(i == 0? " ON " : " AND ");
				conditions.get(i).build(builder);
			}
		}

		int getIndex() {

			return index;
		}

		public ISqlTable<?> getTable() {

			return table;
		}
	}

	private final static class ExpressionEntry {

		private final ISqlClauseBuildable expression;
		private final ArrayList<TableEntry> selectedTableEntries;

		@Override
		public String toString() {

			StringBuilder builder = new StringBuilder();
			ArrayList<String> tables = new ArrayList<>();
			for (TableEntry entry: selectedTableEntries) {
				tables.add("" + entry.getIndex());
			}
			builder.append("Exp(" + Imploder.implode(tables, ", ") + ")");
			return builder.toString();
		}

		ExpressionEntry(ISqlClauseBuildable expression, List<TableEntry> allTableEntries, List<Integer> selectedTables) {

			this.expression = expression;
			selectedTableEntries = new ArrayList<>(selectedTables.size());
			for (Integer tableIndex: selectedTables) {
				selectedTableEntries.add(allTableEntries.get(tableIndex));
			}
		}

		void build(PartBuilder builder) {

			builder.setSelectedTableEntries(selectedTableEntries);
			expression.build(builder);
		}
	}
}
