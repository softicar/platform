package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;

public final class SqlSelect0_7<V0, V1, V2, V3, V4, V5, V6> extends SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> implements ISqlSelectExtension<SqlSelect0_7<V0, V1, V2, V3, V4, V5, V6>> {

	protected SqlSelect0_7(SqlSelectBase6<V0, V1, V2, V3, V4, V5> other, ISqlExpression<V6> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect0_7<V0, V1, V2, V3, V4, V5, V6> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect0_8<V0, V1, V2, V3, V4, V5, V6, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		<V> SqlSelect0_8<V0, V1, V2, V3, V4, V5, V6, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect0_8<>(SqlSelect0_7.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}
}

