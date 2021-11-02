package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;

public final class SqlTableChooser1_1<V, S extends ISqlSelectCoreAdapter, T0, E0> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression2<? extends V, E0, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	public SqlTableChooser1_2<V, S, T0, E0, T0> t0() {

		return new SqlTableChooser1_2<>(this, 0);
	}

	SqlTableChooser1_1(AbstractSqlTableChooser<S> chooser, int tableIndex) {

		super(chooser, tableIndex);
	}
}

