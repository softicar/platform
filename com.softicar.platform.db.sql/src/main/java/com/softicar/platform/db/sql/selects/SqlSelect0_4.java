package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;

public final class SqlSelect0_4<V0, V1, V2, V3> extends SqlSelectBase4<V0, V1, V2, V3> implements ISqlSelectExtension<SqlSelect0_4<V0, V1, V2, V3>> {

	protected SqlSelect0_4(SqlSelectBase3<V0, V1, V2> other, ISqlExpression<V3> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect0_4<V0, V1, V2, V3> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect0_5<V0, V1, V2, V3, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		<V> SqlSelect0_5<V0, V1, V2, V3, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect0_5<>(SqlSelect0_4.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}
}

