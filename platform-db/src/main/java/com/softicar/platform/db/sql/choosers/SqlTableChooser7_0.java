package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlTableChooser7_0<V, S extends ISqlSelectCoreAdapter, T0, T1, T2, T3, T4, T5, T6> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression1<? extends V, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public S t1(ISqlExpression1<? extends V, T1> expression) {

		return addTable(1).addExpression(expression);
	}

	public S t2(ISqlExpression1<? extends V, T2> expression) {

		return addTable(2).addExpression(expression);
	}

	public S t3(ISqlExpression1<? extends V, T3> expression) {

		return addTable(3).addExpression(expression);
	}

	public S t4(ISqlExpression1<? extends V, T4> expression) {

		return addTable(4).addExpression(expression);
	}

	public S t5(ISqlExpression1<? extends V, T5> expression) {

		return addTable(5).addExpression(expression);
	}

	public S t6(ISqlExpression1<? extends V, T6> expression) {

		return addTable(6).addExpression(expression);
	}

	public SqlTableChooser7_1<V, S, T0, T1, T2, T3, T4, T5, T6, T0> t0() {

		return new SqlTableChooser7_1<>(this, 0);
	}

	public SqlTableChooser7_1<V, S, T0, T1, T2, T3, T4, T5, T6, T1> t1() {

		return new SqlTableChooser7_1<>(this, 1);
	}

	public SqlTableChooser7_1<V, S, T0, T1, T2, T3, T4, T5, T6, T2> t2() {

		return new SqlTableChooser7_1<>(this, 2);
	}

	public SqlTableChooser7_1<V, S, T0, T1, T2, T3, T4, T5, T6, T3> t3() {

		return new SqlTableChooser7_1<>(this, 3);
	}

	public SqlTableChooser7_1<V, S, T0, T1, T2, T3, T4, T5, T6, T4> t4() {

		return new SqlTableChooser7_1<>(this, 4);
	}

	public SqlTableChooser7_1<V, S, T0, T1, T2, T3, T4, T5, T6, T5> t5() {

		return new SqlTableChooser7_1<>(this, 5);
	}

	public SqlTableChooser7_1<V, S, T0, T1, T2, T3, T4, T5, T6, T6> t6() {

		return new SqlTableChooser7_1<>(this, 6);
	}

	public SqlTableChooser7_0(S select, PartType partType) {

		super(select, partType);
	}
}

