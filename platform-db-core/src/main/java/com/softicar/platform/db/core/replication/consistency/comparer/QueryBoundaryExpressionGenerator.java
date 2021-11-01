package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a query boundary expression for a given {@link TableKeyRow}.
 * <p>
 * For database tables with multi-column primary key, a {@link TableKeyRow} will
 * have more than one column value pair. At least on MySQL 5.6, a naive
 * multi-column boundary expression like
 *
 * <pre>
 * {@code (a,b) > (5,7)}
 * </pre>
 *
 * will perform badly. Instead we need to generate an expression like this:
 *
 * <pre>
 * {@code (a > 5) OR (a = 5 AND b > 7)}
 * </pre>
 *
 * This class generates such an expression for a given {@link TableKeyRow}.
 *
 * @author Oliver Richers
 */
public class QueryBoundaryExpressionGenerator {

	private final TableKeyRow tableKeyRow;
	private final Mode mode;

	public QueryBoundaryExpressionGenerator(TableKeyRow tableKeyRow, Mode mode) {

		this.tableKeyRow = tableKeyRow;
		this.mode = mode;
	}

	public DbStatement generate() {

		List<String> remainingColumns = new ArrayList<>(tableKeyRow.getColumns());
		List<String> remainingValues = new ArrayList<>(tableKeyRow.getValues());

		DbStatement expression = new DbStatement();
		while (!remainingColumns.isEmpty()) {
			if (!expression.isEmpty()) {
				expression.addText(" OR ");
			}

			expression.addText("(");
			for (int i = 0; i < remainingColumns.size(); i++) {
				boolean last = i == remainingColumns.size() - 1;
				if (last) {
					expression.addText("%s %s ?", remainingColumns.get(i), getOperator(remainingColumns));
					expression.addParameter(remainingValues.get(i));
				} else {
					expression.addText("%s = ? AND ", remainingColumns.get(i));
					expression.addParameter(remainingValues.get(i));
				}
			}
			expression.addText(")");

			remainingColumns.remove(remainingColumns.size() - 1);
			remainingValues.remove(remainingValues.size() - 1);
		}
		return expression;
	}

	private String getOperator(List<String> remainingColumns) {

		boolean fullExpression = remainingColumns.size() == tableKeyRow.getColumns().size();
		switch (mode) {
		case EXCLUSIVE_LOWER_BOUND:
			return ">";
		case EXCLUSIVE_UPPER_BOUND:
			return "<";
		case INCLUSIVE_LOWER_BOUND:
			return fullExpression? ">=" : ">";
		case INCLUSIVE_UPPER_BOUND:
			return fullExpression? "<=" : "<";
		}
		throw new SofticarUnknownEnumConstantException(mode);
	}

	public static enum Mode {
		EXCLUSIVE_LOWER_BOUND,
		EXCLUSIVE_UPPER_BOUND,
		INCLUSIVE_LOWER_BOUND,
		INCLUSIVE_UPPER_BOUND,
	}
}
