package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

final class SqlOrderDescendingAdapter<V> implements ISqlExpression<V> {

	private final ISqlExpression<V> other;

	public SqlOrderDescendingAdapter(ISqlExpression<V> other) {

		this.other = other;
	}

	@Override
	public int getColumnCount() {

		return other.getColumnCount();
	}

	@Override
	public ISqlValueType<V> getValueType() {

		return other.getValueType();
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		other.build(builder);
		builder.addText(" DESC");
	}
}
