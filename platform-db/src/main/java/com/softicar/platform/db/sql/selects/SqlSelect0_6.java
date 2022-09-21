package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;

public final class SqlSelect0_6<V0, V1, V2, V3, V4, V5> extends SqlSelectBase6<V0, V1, V2, V3, V4, V5> implements ISqlSelectExtension<SqlSelect0_6<V0, V1, V2, V3, V4, V5>> {

	protected SqlSelect0_6(SqlSelectBase5<V0, V1, V2, V3, V4> other, ISqlExpression<V5> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect0_6<V0, V1, V2, V3, V4, V5> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect0_7<V0, V1, V2, V3, V4, V5, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		<V> SqlSelect0_7<V0, V1, V2, V3, V4, V5, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect0_7<>(SqlSelect0_6.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}
}

