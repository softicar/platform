package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;

public final class SqlSelect0_1<V0> extends SqlSelectBase1<V0> implements ISqlSelectExtension<SqlSelect0_1<V0>> {

	protected SqlSelect0_1(SqlSelectBase0 other, ISqlExpression<V0> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect0_1<V0> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect0_2<V0, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		<V> SqlSelect0_2<V0, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect0_2<>(SqlSelect0_1.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}
}

