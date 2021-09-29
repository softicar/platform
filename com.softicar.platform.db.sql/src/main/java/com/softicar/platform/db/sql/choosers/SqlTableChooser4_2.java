package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;

public final class SqlTableChooser4_2<V, S extends ISqlSelectCoreAdapter, T0, T1, T2, T3, E0, E1> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression3<? extends V, E0, E1, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public S t1(ISqlExpression3<? extends V, E0, E1, T1> expression) {

		return addTable(1).addExpression(expression);
	}

	public S t2(ISqlExpression3<? extends V, E0, E1, T2> expression) {

		return addTable(2).addExpression(expression);
	}

	public S t3(ISqlExpression3<? extends V, E0, E1, T3> expression) {

		return addTable(3).addExpression(expression);
	}

	public SqlTableChooser4_3<V, S, T0, T1, T2, T3, E0, E1, T0> t0() {

		return new SqlTableChooser4_3<>(this, 0);
	}

	public SqlTableChooser4_3<V, S, T0, T1, T2, T3, E0, E1, T1> t1() {

		return new SqlTableChooser4_3<>(this, 1);
	}

	public SqlTableChooser4_3<V, S, T0, T1, T2, T3, E0, E1, T2> t2() {

		return new SqlTableChooser4_3<>(this, 2);
	}

	public SqlTableChooser4_3<V, S, T0, T1, T2, T3, E0, E1, T3> t3() {

		return new SqlTableChooser4_3<>(this, 3);
	}

	SqlTableChooser4_2(AbstractSqlTableChooser<S> chooser, int tableIndex) {

		super(chooser, tableIndex);
	}
}

