package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;

/**
 * Builder for the left-hand side of the VALUES clause of an INSERT statement.
 * <p>
 * The left-hand side of the VALUES clause lists the fields of the table row. On
 * the right-hand side of the VALUES clause, the values of these fields will can
 * defined using the {@link SqlInsertValuesClauseBuilder}.
 * <p>
 * This class supports single-row inserts as well as multi-row inserts.
 *
 * @author Oliver Richers
 * @see SqlInsertValuesClauseBuilder
 */
public class SqlInsertIntoClauseBuilder extends SqlClauseBuilder {

	private boolean finished = false;

	public SqlInsertIntoClauseBuilder(SqlStatementBuilder statementBuilder) {

		super(statementBuilder);
	}

	/**
	 * Adds the given field to the field list.
	 * <p>
	 * For the first field, an opening parenthesis is prepended, for all
	 * following fields, a comma is prepended.
	 * <p>
	 * This method does nothing if the {@link #finishFieldList()} method was
	 * called.
	 *
	 * @param field
	 *            the field to add (never null)
	 */
	public void addFieldToList(ISqlField<?, ?> field) {

		if (!finished) {
			addText(isEmpty()? " (" : ", ");
			addField(field);
		}
	}

	/**
	 * Closes the left-hand side of the VALUES clause and marks it to be
	 * finished.
	 * <p>
	 * This method appends a closing parenthesis (if fields have been added),
	 * and the VALUES keyword.
	 * <p>
	 * Any subsequent calls to {@link #addFieldToList(ISqlField)} or
	 * {@link #finishFieldList()} will do nothing.
	 */
	public void finishFieldList() {

		if (!finished) {
			if (!isEmpty()) {
				addText(") VALUES ");
			} else {
				addText(" VALUES ");
			}
			finished = true;
		}
	}
}
