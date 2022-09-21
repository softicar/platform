package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;

public final class SqlTableChooser1_3<V, S extends ISqlSelectCoreAdapter, T0, E0, E1, E2> extends AbstractSqlTableChooser<S> {

	public S t0(ISqlExpression4<? extends V, E0, E1, E2, T0> expression) {

		return addTable(0).addExpression(expression);
	}

	SqlTableChooser1_3(AbstractSqlTableChooser<S> chooser, int tableIndex) {

		super(chooser, tableIndex);
	}
}

