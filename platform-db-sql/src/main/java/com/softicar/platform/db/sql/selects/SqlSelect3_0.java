package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser3_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect3_0<T0, T1, T2> extends SqlSelectBase0 implements ISqlSelectLastTableExtension<SqlSelect3_0<T0, T1, T2>, T2> {

	protected SqlSelect3_0() {

		// protected
	}

	@Override
	public SqlSelect3_0<T0, T1, T2> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect3_1<T0, T1, T2, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	public <V> SqlSelect3_1<T0, T1, T2, V> select(ISqlExpression1<V, T2> expression) {

		return select().t2(expression);
	}

	// -------------------------------- join -------------------------------- //

	public <T3> SqlSelect4_0<T0, T1, T2, T3> join(ISqlTable<T3> table) {

		return _join(new SqlSelect4_0<T0, T1, T2, T3>(), table, JoinType.JOIN);
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> join(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return joinOnTable2(foreignField);
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinOnTable0(ISqlForeignRowField<T0, T3, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinOnTable1(ISqlForeignRowField<T1, T3, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinOnTable2(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinReverse(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return joinReverseOnTable2(foreignField);
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinReverseOnTable0(ISqlForeignRowField<T3, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t3().t0(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinReverseOnTable1(ISqlForeignRowField<T3, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t3().t1(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinReverseOnTable2(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t3().t2(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeft(ISqlTable<T3> table) {

		return _join(new SqlSelect4_0<T0, T1, T2, T3>(), table, JoinType.LEFT_JOIN);
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeft(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return joinLeftOnTable2(foreignField);
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeftOnTable0(ISqlForeignRowField<T0, T3, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeftOnTable1(ISqlForeignRowField<T1, T3, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeftOnTable2(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeftReverse(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return joinLeftReverseOnTable2(foreignField);
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeftReverseOnTable0(ISqlForeignRowField<T3, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t3().t0(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeftReverseOnTable1(ISqlForeignRowField<T3, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t3().t1(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_0<T0, T1, T2, T3> joinLeftReverseOnTable2(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t3().t2(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser3_0<Boolean, SqlSelect3_0<T0, T1, T2>, T0, T1, T2> on() {

		return new SqlTableChooser3_0<>(this, PartType.JOIN);
	}

	public SqlSelect3_0<T0, T1, T2> on(ISqlBooleanExpression2<T1, T2> condition) {

		return on().t1().t2(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser3_0<Boolean, SqlSelect3_0<T0, T1, T2>, T0, T1, T2> where() {

		return new SqlTableChooser3_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser3_0<Object, SqlSelect3_0<T0, T1, T2>, T0, T1, T2> groupBy() {

		return new SqlTableChooser3_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser3_0<Boolean, SqlSelect3_0<T0, T1, T2>, T0, T1, T2> having() {

		return new SqlTableChooser3_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser3_0<Object, SqlSelect3_0<T0, T1, T2>, T0, T1, T2> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser3_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser3_0<Object, SqlSelect3_0<T0, T1, T2>, T0, T1, T2> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser3_0<Object, SqlSelect3_0<T0, T1, T2>, T0, T1, T2> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		public <V> SqlSelect3_1<T0, T1, T2, V> t0(ISqlExpression1<V, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t1(ISqlExpression1<V, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t2(ISqlExpression1<V, T2> expression) { return t2().x(expression); }
		public SelectChooser1<T0> t0() { return new SelectChooser1<>(this, 0); }
		public SelectChooser1<T1> t1() { return new SelectChooser1<>(this, 1); }
		public SelectChooser1<T2> t2() { return new SelectChooser1<>(this, 2); }
		<V> SqlSelect3_1<T0, T1, T2, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect3_1<T0, T1, T2, V>(SqlSelect3_0.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}

	public final class SelectChooser1<E0> extends SelectChooserBase {

		public <V> SqlSelect3_1<T0, T1, T2, V> t0(ISqlExpression2<V, E0, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t1(ISqlExpression2<V, E0, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t2(ISqlExpression2<V, E0, T2> expression) { return t2().x(expression); }
		public SelectChooser2<E0, T0> t0() { return new SelectChooser2<>(this, 0); }
		public SelectChooser2<E0, T1> t1() { return new SelectChooser2<>(this, 1); }
		public SelectChooser2<E0, T2> t2() { return new SelectChooser2<>(this, 2); }
		<V> SqlSelect3_1<T0, T1, T2, V> x(ISqlExpression1<V, E0> expression) { return addExpression(new SqlSelect3_1<T0, T1, T2, V>(SqlSelect3_0.this, expression), expression); }
		SelectChooser1(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser2<E0, E1> extends SelectChooserBase {

		public <V> SqlSelect3_1<T0, T1, T2, V> t0(ISqlExpression3<V, E0, E1, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t1(ISqlExpression3<V, E0, E1, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t2(ISqlExpression3<V, E0, E1, T2> expression) { return t2().x(expression); }
		public SelectChooser3<E0, E1, T0> t0() { return new SelectChooser3<>(this, 0); }
		public SelectChooser3<E0, E1, T1> t1() { return new SelectChooser3<>(this, 1); }
		public SelectChooser3<E0, E1, T2> t2() { return new SelectChooser3<>(this, 2); }
		<V> SqlSelect3_1<T0, T1, T2, V> x(ISqlExpression2<V, E0, E1> expression) { return addExpression(new SqlSelect3_1<T0, T1, T2, V>(SqlSelect3_0.this, expression), expression); }
		SelectChooser2(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser3<E0, E1, E2> extends SelectChooserBase {

		public <V> SqlSelect3_1<T0, T1, T2, V> t0(ISqlExpression4<V, E0, E1, E2, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t1(ISqlExpression4<V, E0, E1, E2, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect3_1<T0, T1, T2, V> t2(ISqlExpression4<V, E0, E1, E2, T2> expression) { return t2().x(expression); }
		public SelectChooser4<E0, E1, E2, T0> t0() { return new SelectChooser4<>(this, 0); }
		public SelectChooser4<E0, E1, E2, T1> t1() { return new SelectChooser4<>(this, 1); }
		public SelectChooser4<E0, E1, E2, T2> t2() { return new SelectChooser4<>(this, 2); }
		<V> SqlSelect3_1<T0, T1, T2, V> x(ISqlExpression3<V, E0, E1, E2> expression) { return addExpression(new SqlSelect3_1<T0, T1, T2, V>(SqlSelect3_0.this, expression), expression); }
		SelectChooser3(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser4<E0, E1, E2, E3> extends SelectChooserBase {

		<V> SqlSelect3_1<T0, T1, T2, V> x(ISqlExpression4<V, E0, E1, E2, E3> expression) { return addExpression(new SqlSelect3_1<T0, T1, T2, V>(SqlSelect3_0.this, expression), expression); }
		SelectChooser4(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}
}

