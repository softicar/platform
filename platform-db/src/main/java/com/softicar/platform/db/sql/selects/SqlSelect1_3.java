package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser1_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect1_3<T0, V0, V1, V2> extends SqlSelectBase3<V0, V1, V2> implements ISqlSelectLastTableExtension<SqlSelect1_3<T0, V0, V1, V2>, T0> {

	protected SqlSelect1_3(SqlSelectBase2<V0, V1> other, ISqlExpression<V2> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect1_3<T0, V0, V1, V2> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect1_4<T0, V0, V1, V2, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	public <V> SqlSelect1_4<T0, V0, V1, V2, V> select(ISqlExpression1<V, T0> expression) {

		return select().t0(expression);
	}

	// -------------------------------- join -------------------------------- //

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> join(ISqlTable<T1> table) {

		return _join(new SqlSelect2_3<>(other, expression), table, JoinType.JOIN);
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> join(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return joinOnTable0(foreignField);
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinOnTable0(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t1(foreignField.isEqualToTargetField());
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinReverse(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return joinReverseOnTable0(foreignField);
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinReverseOnTable0(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t1().t0(foreignField.isEqualToTargetField());
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinLeft(ISqlTable<T1> table) {

		return _join(new SqlSelect2_3<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinLeft(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return joinLeftOnTable0(foreignField);
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinLeftOnTable0(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t1(foreignField.isEqualToTargetField());
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinLeftReverse(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return joinLeftReverseOnTable0(foreignField);
	}

	public <T1> SqlSelect2_3<T0, T1, V0, V1, V2> joinLeftReverseOnTable0(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t1().t0(foreignField.isEqualToTargetField());
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser1_0<Boolean, SqlSelect1_3<T0, V0, V1, V2>, T0> where() {

		return new SqlTableChooser1_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser1_0<Object, SqlSelect1_3<T0, V0, V1, V2>, T0> groupBy() {

		return new SqlTableChooser1_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser1_0<Boolean, SqlSelect1_3<T0, V0, V1, V2>, T0> having() {

		return new SqlTableChooser1_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser1_0<Object, SqlSelect1_3<T0, V0, V1, V2>, T0> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser1_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser1_0<Object, SqlSelect1_3<T0, V0, V1, V2>, T0> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser1_0<Object, SqlSelect1_3<T0, V0, V1, V2>, T0> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		public <V> SqlSelect1_4<T0, V0, V1, V2, V> t0(ISqlExpression1<V, T0> expression) { return t0().x(expression); }
		public SelectChooser1<T0> t0() { return new SelectChooser1<>(this, 0); }
		<V> SqlSelect1_4<T0, V0, V1, V2, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect1_4<>(SqlSelect1_3.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}

	public final class SelectChooser1<E0> extends SelectChooserBase {

		public <V> SqlSelect1_4<T0, V0, V1, V2, V> t0(ISqlExpression2<V, E0, T0> expression) { return t0().x(expression); }
		public SelectChooser2<E0, T0> t0() { return new SelectChooser2<>(this, 0); }
		<V> SqlSelect1_4<T0, V0, V1, V2, V> x(ISqlExpression1<V, E0> expression) { return addExpression(new SqlSelect1_4<>(SqlSelect1_3.this, expression), expression); }
		SelectChooser1(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser2<E0, E1> extends SelectChooserBase {

		public <V> SqlSelect1_4<T0, V0, V1, V2, V> t0(ISqlExpression3<V, E0, E1, T0> expression) { return t0().x(expression); }
		public SelectChooser3<E0, E1, T0> t0() { return new SelectChooser3<>(this, 0); }
		<V> SqlSelect1_4<T0, V0, V1, V2, V> x(ISqlExpression2<V, E0, E1> expression) { return addExpression(new SqlSelect1_4<>(SqlSelect1_3.this, expression), expression); }
		SelectChooser2(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser3<E0, E1, E2> extends SelectChooserBase {

		public <V> SqlSelect1_4<T0, V0, V1, V2, V> t0(ISqlExpression4<V, E0, E1, E2, T0> expression) { return t0().x(expression); }
		public SelectChooser4<E0, E1, E2, T0> t0() { return new SelectChooser4<>(this, 0); }
		<V> SqlSelect1_4<T0, V0, V1, V2, V> x(ISqlExpression3<V, E0, E1, E2> expression) { return addExpression(new SqlSelect1_4<>(SqlSelect1_3.this, expression), expression); }
		SelectChooser3(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser4<E0, E1, E2, E3> extends SelectChooserBase {

		<V> SqlSelect1_4<T0, V0, V1, V2, V> x(ISqlExpression4<V, E0, E1, E2, E3> expression) { return addExpression(new SqlSelect1_4<>(SqlSelect1_3.this, expression), expression); }
		SelectChooser4(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}
}

