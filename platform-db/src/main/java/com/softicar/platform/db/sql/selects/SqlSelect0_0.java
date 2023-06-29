package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;

public class SqlSelect0_0 extends SqlSelectBase0 {

	public final class SelectChooser0 extends SelectChooserBase {

		<V0> SqlSelect0_1<V0> x(ISqlExpression0<V0> expression) {

			return addExpression(new SqlSelect0_1<>(SqlSelect0_0.this, expression), expression);
		}
	}

	public <T0> SqlSelect1_0<T0> from(ISqlTable<T0> table) {

		return _join(new SqlSelect1_0<>(), table, JoinType.FROM);
	}

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V0> SqlSelect0_1<V0> select(ISqlExpression0<V0> expression) {

		return select().x(expression);
	}

	public SqlSelect0_0() {

		super(new SqlSelectCore());
	}
}
