package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;

/**
 * Builder for the right-hand side of the VALUES clause of an INSERT statement.
 * <p>
 * The right-hand side of the VALUES clause lists the values to be assigned to
 * the fields, which are defined on the left-hand side of the VALUES keyword
 * using the {@link SqlInsertIntoClauseBuilder}.
 * <p>
 * This class supports single-row inserts as well as multi-row inserts.
 *
 * @author Oliver Richers
 * @see SqlInsertIntoClauseBuilder
 */
public class SqlInsertValuesClauseBuilder extends SqlClauseBuilder {

	private int rowCount;

	public SqlInsertValuesClauseBuilder(SqlStatementBuilder statementBuilder) {

		super(statementBuilder);

		rowCount = 0;
	}

	public int getRowCount() {

		return rowCount;
	}

	/**
	 * Adds the value to the right-hand side of the VALUES clause.
	 * <p>
	 * If this is the first value for a value list, this method prepends an
	 * opening parenthesis, otherwise a comma is prepended.
	 * <p>
	 * For multi-row inserts, after a call to {@link #finishValueList()}, a
	 * comma is automatically added between the value lists.
	 *
	 * @param value
	 *            the value (may be null)
	 */
	public <V> void addValueToList(V value) {

		if (isEmpty()) {
			// start of first value list
			addText("(");
			rowCount++;
		} else if (endsWith(')')) {
			// start of new value list
			addText(", (");
			rowCount++;
		} else {
			// new value
			addText(", ");
		}

		addParameter(value);
	}

	/**
	 * Finishes the current value list by appending a closing parenthesis.
	 * <p>
	 * If no values have been added a {@code "(DEFAULT)"} is appended.
	 * <p>
	 * Redundant calls to this method are ignored, that is, calling this method
	 * without a call to {@link #addValueToList(Object)} does nothing.
	 */
	public void finishValueList() {

		if (isEmpty()) {
			addText("(DEFAULT)");
		} else if (!endsWith(')')) {
			addText(")");
		}
	}
}
