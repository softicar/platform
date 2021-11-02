package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;

public final class SqlTableChooser3_3<V, S extends ISqlSelectCoreAdapter, T0, T1, T2, E0, E1, E2> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression4<? extends V, E0, E1, E2, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public S t1(ISqlExpression4<? extends V, E0, E1, E2, T1> expression) {

		return addTable(1).addExpression(expression);
	}

	public S t2(ISqlExpression4<? extends V, E0, E1, E2, T2> expression) {

		return addTable(2).addExpression(expression);
	}

	SqlTableChooser3_3(AbstractSqlTableChooser<S> chooser, int tableIndex) {

		super(chooser, tableIndex);
	}
}

