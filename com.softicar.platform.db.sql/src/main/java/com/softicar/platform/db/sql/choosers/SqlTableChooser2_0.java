package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlTableChooser2_0<V, S extends ISqlSelectCoreAdapter, T0, T1> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression1<? extends V, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public S t1(ISqlExpression1<? extends V, T1> expression) {

		return addTable(1).addExpression(expression);
	}

	public SqlTableChooser2_1<V, S, T0, T1, T0> t0() {

		return new SqlTableChooser2_1<>(this, 0);
	}

	public SqlTableChooser2_1<V, S, T0, T1, T1> t1() {

		return new SqlTableChooser2_1<>(this, 1);
	}

	public SqlTableChooser2_0(S select, PartType partType) {

		super(select, partType);
	}
}

