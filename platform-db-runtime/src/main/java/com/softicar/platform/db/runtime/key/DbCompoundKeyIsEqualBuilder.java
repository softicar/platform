package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.SqlBooleanOperator;
import java.util.List;
import java.util.stream.Collectors;

class DbCompoundKeyIsEqualBuilder<R, P> {

	private final AbstractDbCompoundKey<R, P> key;
	private final P value;

	public DbCompoundKeyIsEqualBuilder(AbstractDbCompoundKey<R, P> key, P value) {

		this.key = key;
		this.value = value;
	}

	public ISqlBooleanExpression<R> build() {

		List<ISqlBooleanExpression<R>> expressions = key//
			.getFields()
			.stream()
			.map(this::isEqual)
			.collect(Collectors.toList());
		return new SqlBooleanOperator<>(SqlBooleanOperator.Type.AND, expressions);
	}

	private <V> ISqlBooleanExpression<R> isEqual(IDbField<R, V> field) {

		return field.isEqual(key.getValue(field, value));
	}
}
