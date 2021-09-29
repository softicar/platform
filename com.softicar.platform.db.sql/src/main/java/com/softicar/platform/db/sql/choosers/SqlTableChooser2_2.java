package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;

public final class SqlTableChooser2_2<V, S extends ISqlSelectCoreAdapter, T0, T1, E0, E1> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression3<? extends V, E0, E1, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public S t1(ISqlExpression3<? extends V, E0, E1, T1> expression) {

		return addTable(1).addExpression(expression);
	}

	public SqlTableChooser2_3<V, S, T0, T1, E0, E1, T0> t0() {

		return new SqlTableChooser2_3<>(this, 0);
	}

	public SqlTableChooser2_3<V, S, T0, T1, E0, E1, T1> t1() {

		return new SqlTableChooser2_3<>(this, 1);
	}

	SqlTableChooser2_2(AbstractSqlTableChooser<S> chooser, int tableIndex) {

		super(chooser, tableIndex);
	}
}

