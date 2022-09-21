package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;

public final class SqlSelect0_3<V0, V1, V2> extends SqlSelectBase3<V0, V1, V2> implements ISqlSelectExtension<SqlSelect0_3<V0, V1, V2>> {

	protected SqlSelect0_3(SqlSelectBase2<V0, V1> other, ISqlExpression<V2> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect0_3<V0, V1, V2> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect0_4<V0, V1, V2, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		<V> SqlSelect0_4<V0, V1, V2, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect0_4<>(SqlSelect0_3.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}
}

