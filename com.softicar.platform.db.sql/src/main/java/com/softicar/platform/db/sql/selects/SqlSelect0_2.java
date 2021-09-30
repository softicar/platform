package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;

public final class SqlSelect0_2<V0, V1> extends SqlSelectBase2<V0, V1> implements ISqlSelectExtension<SqlSelect0_2<V0, V1>> {

	protected SqlSelect0_2(SqlSelectBase1<V0> other, ISqlExpression<V1> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect0_2<V0, V1> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect0_3<V0, V1, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		<V> SqlSelect0_3<V0, V1, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect0_3<>(SqlSelect0_2.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}
}

