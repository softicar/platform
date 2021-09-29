package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;

public final class SqlTableChooser5_3<V, S extends ISqlSelectCoreAdapter, T0, T1, T2, T3, T4, E0, E1, E2> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression4<? extends V, E0, E1, E2, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public S t1(ISqlExpression4<? extends V, E0, E1, E2, T1> expression) {

		return addTable(1).addExpression(expression);
	}

	public S t2(ISqlExpression4<? extends V, E0, E1, E2, T2> expression) {

		return addTable(2).addExpression(expression);
	}

	public S t3(ISqlExpression4<? extends V, E0, E1, E2, T3> expression) {

		return addTable(3).addExpression(expression);
	}

	public S t4(ISqlExpression4<? extends V, E0, E1, E2, T4> expression) {

		return addTable(4).addExpression(expression);
	}

	SqlTableChooser5_3(AbstractSqlTableChooser<S> chooser, int tableIndex) {

		super(chooser, tableIndex);
	}
}

