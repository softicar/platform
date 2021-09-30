package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlTableChooser1_0<V, S extends ISqlSelectCoreAdapter, T0> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression1<? extends V, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public SqlTableChooser1_1<V, S, T0, T0> t0() {

		return new SqlTableChooser1_1<>(this, 0);
	}

	public SqlTableChooser1_0(S select, PartType partType) {

		super(select, partType);
	}
}

