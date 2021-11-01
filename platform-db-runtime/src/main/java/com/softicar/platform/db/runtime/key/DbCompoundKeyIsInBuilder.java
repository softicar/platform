package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.SqlBooleanOperator;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class DbCompoundKeyIsInBuilder<R, P> {

	private final AbstractDbCompoundKey<R, P> key;
	private final Collection<P> values;

	public DbCompoundKeyIsInBuilder(AbstractDbCompoundKey<R, P> key, Collection<P> values) {

		this.key = key;
		this.values = values;
	}

	public ISqlBooleanExpression<R> build() {

		List<ISqlBooleanExpression<R>> expressions = values//
			.stream()
			.map(key::isEqual)
			.collect(Collectors.toList());
		return new SqlBooleanOperator<>(SqlBooleanOperator.Type.OR, expressions);
	}
}
