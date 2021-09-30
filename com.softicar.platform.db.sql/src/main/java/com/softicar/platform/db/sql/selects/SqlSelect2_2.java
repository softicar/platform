package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser2_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect2_2<T0, T1, V0, V1> extends SqlSelectBase2<V0, V1> implements ISqlSelectLastTableExtension<SqlSelect2_2<T0, T1, V0, V1>, T1> {

	protected SqlSelect2_2(SqlSelectBase1<V0> other, ISqlExpression<V1> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect2_2<T0, T1, V0, V1> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect2_3<T0, T1, V0, V1, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	public <V> SqlSelect2_3<T0, T1, V0, V1, V> select(ISqlExpression1<V, T1> expression) {

		return select().t1(expression);
	}

	// -------------------------------- join -------------------------------- //

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> join(ISqlTable<T2> table) {

		return _join(new SqlSelect3_2<T0, T1, T2, V0, V1>(other, expression), table, JoinType.JOIN);
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> join(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return joinOnTable1(foreignField);
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinOnTable0(ISqlForeignRowField<T0, T2, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinOnTable1(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinReverse(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return joinReverseOnTable1(foreignField);
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinReverseOnTable0(ISqlForeignRowField<T2, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t2().t0(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinReverseOnTable1(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t2().t1(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinLeft(ISqlTable<T2> table) {

		return _join(new SqlSelect3_2<T0, T1, T2, V0, V1>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinLeft(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return joinLeftOnTable1(foreignField);
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinLeftOnTable0(ISqlForeignRowField<T0, T2, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinLeftOnTable1(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinLeftReverse(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return joinLeftReverseOnTable1(foreignField);
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinLeftReverseOnTable0(ISqlForeignRowField<T2, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t2().t0(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_2<T0, T1, T2, V0, V1> joinLeftReverseOnTable1(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t2().t1(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser2_0<Boolean, SqlSelect2_2<T0, T1, V0, V1>, T0, T1> on() {

		return new SqlTableChooser2_0<>(this, PartType.JOIN);
	}

	public SqlSelect2_2<T0, T1, V0, V1> on(ISqlBooleanExpression2<T0, T1> condition) {

		return on().t0().t1(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser2_0<Boolean, SqlSelect2_2<T0, T1, V0, V1>, T0, T1> where() {

		return new SqlTableChooser2_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser2_0<Object, SqlSelect2_2<T0, T1, V0, V1>, T0, T1> groupBy() {

		return new SqlTableChooser2_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser2_0<Boolean, SqlSelect2_2<T0, T1, V0, V1>, T0, T1> having() {

		return new SqlTableChooser2_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser2_0<Object, SqlSelect2_2<T0, T1, V0, V1>, T0, T1> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser2_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser2_0<Object, SqlSelect2_2<T0, T1, V0, V1>, T0, T1> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser2_0<Object, SqlSelect2_2<T0, T1, V0, V1>, T0, T1> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t0(ISqlExpression1<V, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t1(ISqlExpression1<V, T1> expression) { return t1().x(expression); }
		public SelectChooser1<T0> t0() { return new SelectChooser1<>(this, 0); }
		public SelectChooser1<T1> t1() { return new SelectChooser1<>(this, 1); }
		<V> SqlSelect2_3<T0, T1, V0, V1, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect2_3<T0, T1, V0, V1, V>(SqlSelect2_2.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}

	public final class SelectChooser1<E0> extends SelectChooserBase {

		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t0(ISqlExpression2<V, E0, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t1(ISqlExpression2<V, E0, T1> expression) { return t1().x(expression); }
		public SelectChooser2<E0, T0> t0() { return new SelectChooser2<>(this, 0); }
		public SelectChooser2<E0, T1> t1() { return new SelectChooser2<>(this, 1); }
		<V> SqlSelect2_3<T0, T1, V0, V1, V> x(ISqlExpression1<V, E0> expression) { return addExpression(new SqlSelect2_3<T0, T1, V0, V1, V>(SqlSelect2_2.this, expression), expression); }
		SelectChooser1(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser2<E0, E1> extends SelectChooserBase {

		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t0(ISqlExpression3<V, E0, E1, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t1(ISqlExpression3<V, E0, E1, T1> expression) { return t1().x(expression); }
		public SelectChooser3<E0, E1, T0> t0() { return new SelectChooser3<>(this, 0); }
		public SelectChooser3<E0, E1, T1> t1() { return new SelectChooser3<>(this, 1); }
		<V> SqlSelect2_3<T0, T1, V0, V1, V> x(ISqlExpression2<V, E0, E1> expression) { return addExpression(new SqlSelect2_3<T0, T1, V0, V1, V>(SqlSelect2_2.this, expression), expression); }
		SelectChooser2(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser3<E0, E1, E2> extends SelectChooserBase {

		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t0(ISqlExpression4<V, E0, E1, E2, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect2_3<T0, T1, V0, V1, V> t1(ISqlExpression4<V, E0, E1, E2, T1> expression) { return t1().x(expression); }
		public SelectChooser4<E0, E1, E2, T0> t0() { return new SelectChooser4<>(this, 0); }
		public SelectChooser4<E0, E1, E2, T1> t1() { return new SelectChooser4<>(this, 1); }
		<V> SqlSelect2_3<T0, T1, V0, V1, V> x(ISqlExpression3<V, E0, E1, E2> expression) { return addExpression(new SqlSelect2_3<T0, T1, V0, V1, V>(SqlSelect2_2.this, expression), expression); }
		SelectChooser3(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser4<E0, E1, E2, E3> extends SelectChooserBase {

		<V> SqlSelect2_3<T0, T1, V0, V1, V> x(ISqlExpression4<V, E0, E1, E2, E3> expression) { return addExpression(new SqlSelect2_3<T0, T1, V0, V1, V>(SqlSelect2_2.this, expression), expression); }
		SelectChooser4(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}
}

