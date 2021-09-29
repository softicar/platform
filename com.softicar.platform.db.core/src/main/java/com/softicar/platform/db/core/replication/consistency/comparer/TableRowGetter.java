package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.math.Range;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.replication.consistency.comparer.QueryBoundaryExpressionGenerator.Mode;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.definition.DbTableDefinition;

class TableRowGetter implements AutoCloseable {

	private static final int DEFAULT_LIMIT = 1000;
	private final DbTableDefinition tableDefinition;
	private final IDbConnection connection;
	private final Range<TableKeyRow> range;
	private int limit;
	private DbResultSet resultSet;
	private int resultSetRowCount;
	private TableRow lastTableRow;
	private TableRow tableRow;
	private boolean done;

	public TableRowGetter(DbTableDefinition tableDefinition, IDbConnection connection, Range<TableKeyRow> range) {

		this.tableDefinition = tableDefinition;
		this.connection = connection;
		this.range = range;
		this.limit = DEFAULT_LIMIT;
		this.resultSet = null;
		this.resultSetRowCount = 0;
		this.lastTableRow = null;
		this.tableRow = null;
		this.done = false;
	}

	@Override
	public void close() {

		closeResultSet();
	}

	public TableRowGetter setLimit(int limit) {

		this.limit = limit;
		return this;
	}

	public TableRow getTableRow() {

		return tableRow;
	}

	public void nextTableRow() {

		if (tableRow != null) {
			this.lastTableRow = tableRow;
		}

		this.tableRow = loadTableRow();
	}

	private TableRow loadTableRow() {

		while (!done) {
			if (resultSet == null) {
				this.resultSet = loadResultSet();
				this.resultSetRowCount = 0;
			} else if (resultSet.next()) {
				this.resultSetRowCount += 1;
				return new TableRow(tableDefinition, resultSet);
			} else {
				closeResultSet();
				if (resultSetRowCount < limit) {
					this.done = true;
				}
			}
		}
		return null;
	}

	private DbResultSet loadResultSet() {

		return connection.executeQuery(getQuery());
	}

	private DbStatement getQuery() {

		return new DbStatement()
			.addText("SELECT * FROM %s", tableDefinition.getTableName())
			.addStatement(getWhereClause())
			.addText(" ORDER BY %s", getImplodedPrimaryKeyColumnNames())
			.addText(" LIMIT %s", limit);
	}

	private DbStatement getWhereClause() {

		DbStatement statement = new DbStatement("WHERE 1");
		if (range != null) {
			DbStatement lowerBound = new QueryBoundaryExpressionGenerator(range.getMin(), Mode.INCLUSIVE_LOWER_BOUND).generate();
			DbStatement upperBound = new QueryBoundaryExpressionGenerator(range.getMax(), Mode.INCLUSIVE_UPPER_BOUND).generate();
			statement.addText(" AND (").addStatement(lowerBound).addText(")");
			statement.addText(" AND (").addStatement(upperBound).addText(")");
		}
		if (lastTableRow != null) {
			DbStatement boundExpression = new QueryBoundaryExpressionGenerator(lastTableRow.getPrimaryKeyRow(), Mode.EXCLUSIVE_LOWER_BOUND).generate();
			statement.addText(" AND (").addStatement(boundExpression).addText(")");
		}
		return statement;
	}

	private String getImplodedPrimaryKeyColumnNames() {

		return Imploder.implode(tableDefinition.getPrimaryKey().getColumns(), ", ", "`", "`");
	}

	private void closeResultSet() {

		if (resultSet != null) {
			resultSet.close();
			this.resultSet = null;
		}
	}
}
