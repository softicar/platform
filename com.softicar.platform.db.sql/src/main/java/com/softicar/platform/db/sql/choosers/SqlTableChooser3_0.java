package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlTableChooser3_0<V, S extends ISqlSelectCoreAdapter, T0, T1, T2> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression1<? extends V, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public S t1(ISqlExpression1<? extends V, T1> expression) {

		return addTable(1).addExpression(expression);
	}

	public S t2(ISqlExpression1<? extends V, T2> expression) {

		return addTable(2).addExpression(expression);
	}

	public SqlTableChooser3_1<V, S, T0, T1, T2, T0> t0() {

		return new SqlTableChooser3_1<>(this, 0);
	}

	public SqlTableChooser3_1<V, S, T0, T1, T2, T1> t1() {

		return new SqlTableChooser3_1<>(this, 1);
	}

	public SqlTableChooser3_1<V, S, T0, T1, T2, T2> t2() {

		return new SqlTableChooser3_1<>(this, 2);
	}

	public SqlTableChooser3_0(S select, PartType partType) {

		super(select, partType);
	}
}

