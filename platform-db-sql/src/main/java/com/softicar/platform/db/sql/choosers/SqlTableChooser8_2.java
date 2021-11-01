package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;

public final class SqlTableChooser8_2<V, S extends ISqlSelectCoreAdapter, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1> extends AbstractSqlTableChooser<S> {

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

	public S t4(ISqlExpression3<? extends V, E0, E1, T4> expression) {

		return addTable(4).addExpression(expression);
	}

	public S t5(ISqlExpression3<? extends V, E0, E1, T5> expression) {

		return addTable(5).addExpression(expression);
	}

	public S t6(ISqlExpression3<? extends V, E0, E1, T6> expression) {

		return addTable(6).addExpression(expression);
	}

	public S t7(ISqlExpression3<? extends V, E0, E1, T7> expression) {

		return addTable(7).addExpression(expression);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T0> t0() {

		return new SqlTableChooser8_3<>(this, 0);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T1> t1() {

		return new SqlTableChooser8_3<>(this, 1);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T2> t2() {

		return new SqlTableChooser8_3<>(this, 2);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T3> t3() {

		return new SqlTableChooser8_3<>(this, 3);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T4> t4() {

		return new SqlTableChooser8_3<>(this, 4);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T5> t5() {

		return new SqlTableChooser8_3<>(this, 5);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T6> t6() {

		return new SqlTableChooser8_3<>(this, 6);
	}

	public SqlTableChooser8_3<V, S, T0, T1, T2, T3, T4, T5, T6, T7, E0, E1, T7> t7() {

		return new SqlTableChooser8_3<>(this, 7);
	}

	SqlTableChooser8_2(AbstractSqlTableChooser<S> chooser, int tableIndex) {

		super(chooser, tableIndex);
	}
}

