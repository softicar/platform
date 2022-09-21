package com.softicar.platform.db.sql;

import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a boolean operator that combines several boolean expression into
 * one new boolean expression.
 *
 * @param <T>
 *            the type of the AG-class of the boolean expressions
 * @author Oliver Richers
 */
public class SqlBooleanOperator<T> extends SqlBooleanExpressionBase<T> {

	private final Type type;
	private final List<ISqlBooleanExpression<T>> expressions;

	public SqlBooleanOperator(Type type, List<ISqlBooleanExpression<T>> expressions) {

		this.type = type;
		this.expressions = expressions;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.fixTable();
		if (expressions.isEmpty()) {
			builder.addText(type.getEmptySemantic());
		} else {
			builder.addText("(");
			for (int i = 0; i < expressions.size(); ++i) {
				if (i > 0) {
					builder.addText(type.getText());
				}

				expressions.get(i).build(builder);
			}
			builder.addText(")");
		}
		builder.unfixTable();
	}

	public static enum Type {

		AND(" AND ", "TRUE"),
		OR(" OR ", "FALSE");

		private final String text;
		private final String emptySemantic;

		private Type(String text, String emptySemantic) {

			this.text = text;
			this.emptySemantic = emptySemantic;
		}

		public <S> SqlBooleanOperator<S> get(ISqlBooleanExpression<S> e1, ISqlBooleanExpression<S> e2) {

			return new SqlBooleanOperator<>(this, Arrays.asList(e1, e2));
		}

		public String getText() {

			return text;
		}

		public String getEmptySemantic() {

			return emptySemantic;
		}
	}
}
