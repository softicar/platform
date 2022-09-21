package com.softicar.platform.db.sql.operations;

import com.softicar.platform.common.container.iterable.Iterables;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public final class SqlIsIn<VALUE> implements ISqlExpression<Boolean> {

	private final Type type;
	private final ISqlExpression<VALUE> expression;
	private final Iterable<? extends VALUE> values;

	public static enum Type {
		IS_IN(" IN ", "FALSE"),
		IS_NOT_IN(" NOT IN ", "TRUE");

		public <V> SqlIsIn<V> get(ISqlExpression<V> expression, Iterable<? extends V> values) {

			return new SqlIsIn<>(this, expression, values);
		}

		public void buildForEmptyList(ISqlClauseBuilder builder) {

			builder.addText(emptySemantic);
		}

		public <V> void build(ISqlClauseBuilder builder, ISqlExpression<V> expression, Iterable<? extends V> values) {

			builder.addText("(");
			expression.build(builder);
			builder.addText(text);
			builder.addParameterPack(values);
			builder.addText(")");
		}

		private Type(String text, String emptySemantic) {

			this.text = text;
			this.emptySemantic = emptySemantic;
		}

		private final String text;
		private final String emptySemantic;
	}

	@Override
	public ISqlValueType<Boolean> getValueType() {

		return SqlValueTypes.BOOLEAN;
	}

	@Override
	public int getColumnCount() {

		return 1;
	}

	protected SqlIsIn(Type type, ISqlExpression<VALUE> expression, Iterable<? extends VALUE> values) {

		this.type = type;
		this.expression = expression;
		this.values = values;

		for (VALUE value: values) {
			if (value == null) {
				throw new SofticarException("Value may not be null in %s.", getClass().getSimpleName());
			}
		}
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		if (Iterables.isEmpty(values)) {
			type.buildForEmptyList(builder);
		} else {
			type.build(builder, expression, values);
		}
	}
}
