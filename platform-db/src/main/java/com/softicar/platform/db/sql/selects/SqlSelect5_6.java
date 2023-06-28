package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser5_0;
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

public final class SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5> extends SqlSelectBase6<V0, V1, V2, V3, V4, V5> implements ISqlSelectLastTableExtension<SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T4> {

	protected SqlSelect5_6(SqlSelectBase5<V0, V1, V2, V3, V4> other, ISqlExpression<V5> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> select(ISqlExpression1<V, T4> expression) {

		return select().t4(expression);
	}

	// -------------------------------- join -------------------------------- //

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> join(ISqlTable<T5> table) {

		return _join(new SqlSelect6_6<>(other, expression), table, JoinType.JOIN);
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> join(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return joinOnTable4(foreignField);
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinOnTable0(ISqlForeignRowField<T0, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinOnTable1(ISqlForeignRowField<T1, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinOnTable2(ISqlForeignRowField<T2, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinOnTable3(ISqlForeignRowField<T3, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t3().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinOnTable4(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t4().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinReverse(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return joinReverseOnTable4(foreignField);
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinReverseOnTable0(ISqlForeignRowField<T5, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t0(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinReverseOnTable1(ISqlForeignRowField<T5, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t1(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinReverseOnTable2(ISqlForeignRowField<T5, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t2(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinReverseOnTable3(ISqlForeignRowField<T5, T3, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t3(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinReverseOnTable4(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t4(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeft(ISqlTable<T5> table) {

		return _join(new SqlSelect6_6<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeft(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return joinLeftOnTable4(foreignField);
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftOnTable0(ISqlForeignRowField<T0, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftOnTable1(ISqlForeignRowField<T1, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftOnTable2(ISqlForeignRowField<T2, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftOnTable3(ISqlForeignRowField<T3, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t3().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftOnTable4(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t4().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftReverse(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return joinLeftReverseOnTable4(foreignField);
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftReverseOnTable0(ISqlForeignRowField<T5, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t0(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftReverseOnTable1(ISqlForeignRowField<T5, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t1(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftReverseOnTable2(ISqlForeignRowField<T5, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t2(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftReverseOnTable3(ISqlForeignRowField<T5, T3, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t3(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_6<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5> joinLeftReverseOnTable4(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t4(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser5_0<Boolean, SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T0, T1, T2, T3, T4> on() {

		return new SqlTableChooser5_0<>(this, PartType.JOIN);
	}

	public SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5> on(ISqlBooleanExpression2<T3, T4> condition) {

		return on().t3().t4(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser5_0<Boolean, SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T0, T1, T2, T3, T4> where() {

		return new SqlTableChooser5_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser5_0<Object, SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T0, T1, T2, T3, T4> groupBy() {

		return new SqlTableChooser5_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser5_0<Boolean, SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T0, T1, T2, T3, T4> having() {

		return new SqlTableChooser5_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser5_0<Object, SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T0, T1, T2, T3, T4> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser5_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser5_0<Object, SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T0, T1, T2, T3, T4> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser5_0<Object, SqlSelect5_6<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5>, T0, T1, T2, T3, T4> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t0(ISqlExpression1<V, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t1(ISqlExpression1<V, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t2(ISqlExpression1<V, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t3(ISqlExpression1<V, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t4(ISqlExpression1<V, T4> expression) { return t4().x(expression); }
		public SelectChooser1<T0> t0() { return new SelectChooser1<>(this, 0); }
		public SelectChooser1<T1> t1() { return new SelectChooser1<>(this, 1); }
		public SelectChooser1<T2> t2() { return new SelectChooser1<>(this, 2); }
		public SelectChooser1<T3> t3() { return new SelectChooser1<>(this, 3); }
		public SelectChooser1<T4> t4() { return new SelectChooser1<>(this, 4); }
		<V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect5_7<>(SqlSelect5_6.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}

	public final class SelectChooser1<E0> extends SelectChooserBase {

		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t0(ISqlExpression2<V, E0, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t1(ISqlExpression2<V, E0, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t2(ISqlExpression2<V, E0, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t3(ISqlExpression2<V, E0, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t4(ISqlExpression2<V, E0, T4> expression) { return t4().x(expression); }
		public SelectChooser2<E0, T0> t0() { return new SelectChooser2<>(this, 0); }
		public SelectChooser2<E0, T1> t1() { return new SelectChooser2<>(this, 1); }
		public SelectChooser2<E0, T2> t2() { return new SelectChooser2<>(this, 2); }
		public SelectChooser2<E0, T3> t3() { return new SelectChooser2<>(this, 3); }
		public SelectChooser2<E0, T4> t4() { return new SelectChooser2<>(this, 4); }
		<V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> x(ISqlExpression1<V, E0> expression) { return addExpression(new SqlSelect5_7<>(SqlSelect5_6.this, expression), expression); }
		SelectChooser1(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser2<E0, E1> extends SelectChooserBase {

		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t0(ISqlExpression3<V, E0, E1, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t1(ISqlExpression3<V, E0, E1, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t2(ISqlExpression3<V, E0, E1, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t3(ISqlExpression3<V, E0, E1, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t4(ISqlExpression3<V, E0, E1, T4> expression) { return t4().x(expression); }
		public SelectChooser3<E0, E1, T0> t0() { return new SelectChooser3<>(this, 0); }
		public SelectChooser3<E0, E1, T1> t1() { return new SelectChooser3<>(this, 1); }
		public SelectChooser3<E0, E1, T2> t2() { return new SelectChooser3<>(this, 2); }
		public SelectChooser3<E0, E1, T3> t3() { return new SelectChooser3<>(this, 3); }
		public SelectChooser3<E0, E1, T4> t4() { return new SelectChooser3<>(this, 4); }
		<V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> x(ISqlExpression2<V, E0, E1> expression) { return addExpression(new SqlSelect5_7<>(SqlSelect5_6.this, expression), expression); }
		SelectChooser2(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser3<E0, E1, E2> extends SelectChooserBase {

		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t0(ISqlExpression4<V, E0, E1, E2, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t1(ISqlExpression4<V, E0, E1, E2, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t2(ISqlExpression4<V, E0, E1, E2, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t3(ISqlExpression4<V, E0, E1, E2, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> t4(ISqlExpression4<V, E0, E1, E2, T4> expression) { return t4().x(expression); }
		public SelectChooser4<E0, E1, E2, T0> t0() { return new SelectChooser4<>(this, 0); }
		public SelectChooser4<E0, E1, E2, T1> t1() { return new SelectChooser4<>(this, 1); }
		public SelectChooser4<E0, E1, E2, T2> t2() { return new SelectChooser4<>(this, 2); }
		public SelectChooser4<E0, E1, E2, T3> t3() { return new SelectChooser4<>(this, 3); }
		public SelectChooser4<E0, E1, E2, T4> t4() { return new SelectChooser4<>(this, 4); }
		<V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> x(ISqlExpression3<V, E0, E1, E2> expression) { return addExpression(new SqlSelect5_7<>(SqlSelect5_6.this, expression), expression); }
		SelectChooser3(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser4<E0, E1, E2, E3> extends SelectChooserBase {

		<V> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V> x(ISqlExpression4<V, E0, E1, E2, E3> expression) { return addExpression(new SqlSelect5_7<>(SqlSelect5_6.this, expression), expression); }
		SelectChooser4(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}
}

