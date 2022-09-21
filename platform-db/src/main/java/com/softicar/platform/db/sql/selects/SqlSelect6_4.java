package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser6_0;
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

public final class SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3> extends SqlSelectBase4<V0, V1, V2, V3> implements ISqlSelectLastTableExtension<SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T5> {

	protected SqlSelect6_4(SqlSelectBase3<V0, V1, V2> other, ISqlExpression<V3> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> select(ISqlExpression1<V, T5> expression) {

		return select().t5(expression);
	}

	// -------------------------------- join -------------------------------- //

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> join(ISqlTable<T6> table) {

		return _join(new SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3>(other, expression), table, JoinType.JOIN);
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> join(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return joinOnTable5(foreignField);
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinOnTable0(ISqlForeignRowField<T0, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinOnTable1(ISqlForeignRowField<T1, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinOnTable2(ISqlForeignRowField<T2, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinOnTable3(ISqlForeignRowField<T3, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t3().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinOnTable4(ISqlForeignRowField<T4, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t4().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinOnTable5(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t5().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinReverse(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return joinReverseOnTable5(foreignField);
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinReverseOnTable0(ISqlForeignRowField<T6, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t0(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinReverseOnTable1(ISqlForeignRowField<T6, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t1(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinReverseOnTable2(ISqlForeignRowField<T6, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t2(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinReverseOnTable3(ISqlForeignRowField<T6, T3, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t3(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinReverseOnTable4(ISqlForeignRowField<T6, T4, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t4(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinReverseOnTable5(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t5(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeft(ISqlTable<T6> table) {

		return _join(new SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeft(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return joinLeftOnTable5(foreignField);
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftOnTable0(ISqlForeignRowField<T0, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftOnTable1(ISqlForeignRowField<T1, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftOnTable2(ISqlForeignRowField<T2, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftOnTable3(ISqlForeignRowField<T3, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t3().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftOnTable4(ISqlForeignRowField<T4, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t4().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftOnTable5(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t5().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftReverse(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return joinLeftReverseOnTable5(foreignField);
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftReverseOnTable0(ISqlForeignRowField<T6, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t0(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftReverseOnTable1(ISqlForeignRowField<T6, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t1(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftReverseOnTable2(ISqlForeignRowField<T6, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t2(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftReverseOnTable3(ISqlForeignRowField<T6, T3, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t3(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftReverseOnTable4(ISqlForeignRowField<T6, T4, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t4(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_4<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3> joinLeftReverseOnTable5(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t5(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser6_0<Boolean, SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T0, T1, T2, T3, T4, T5> on() {

		return new SqlTableChooser6_0<>(this, PartType.JOIN);
	}

	public SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3> on(ISqlBooleanExpression2<T4, T5> condition) {

		return on().t4().t5(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser6_0<Boolean, SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T0, T1, T2, T3, T4, T5> where() {

		return new SqlTableChooser6_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser6_0<Object, SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T0, T1, T2, T3, T4, T5> groupBy() {

		return new SqlTableChooser6_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser6_0<Boolean, SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T0, T1, T2, T3, T4, T5> having() {

		return new SqlTableChooser6_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser6_0<Object, SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T0, T1, T2, T3, T4, T5> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser6_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser6_0<Object, SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T0, T1, T2, T3, T4, T5> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser6_0<Object, SqlSelect6_4<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3>, T0, T1, T2, T3, T4, T5> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t0(ISqlExpression1<V, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t1(ISqlExpression1<V, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t2(ISqlExpression1<V, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t3(ISqlExpression1<V, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t4(ISqlExpression1<V, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t5(ISqlExpression1<V, T5> expression) { return t5().x(expression); }
		public SelectChooser1<T0> t0() { return new SelectChooser1<>(this, 0); }
		public SelectChooser1<T1> t1() { return new SelectChooser1<>(this, 1); }
		public SelectChooser1<T2> t2() { return new SelectChooser1<>(this, 2); }
		public SelectChooser1<T3> t3() { return new SelectChooser1<>(this, 3); }
		public SelectChooser1<T4> t4() { return new SelectChooser1<>(this, 4); }
		public SelectChooser1<T5> t5() { return new SelectChooser1<>(this, 5); }
		<V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V>(SqlSelect6_4.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}

	public final class SelectChooser1<E0> extends SelectChooserBase {

		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t0(ISqlExpression2<V, E0, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t1(ISqlExpression2<V, E0, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t2(ISqlExpression2<V, E0, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t3(ISqlExpression2<V, E0, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t4(ISqlExpression2<V, E0, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t5(ISqlExpression2<V, E0, T5> expression) { return t5().x(expression); }
		public SelectChooser2<E0, T0> t0() { return new SelectChooser2<>(this, 0); }
		public SelectChooser2<E0, T1> t1() { return new SelectChooser2<>(this, 1); }
		public SelectChooser2<E0, T2> t2() { return new SelectChooser2<>(this, 2); }
		public SelectChooser2<E0, T3> t3() { return new SelectChooser2<>(this, 3); }
		public SelectChooser2<E0, T4> t4() { return new SelectChooser2<>(this, 4); }
		public SelectChooser2<E0, T5> t5() { return new SelectChooser2<>(this, 5); }
		<V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> x(ISqlExpression1<V, E0> expression) { return addExpression(new SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V>(SqlSelect6_4.this, expression), expression); }
		SelectChooser1(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser2<E0, E1> extends SelectChooserBase {

		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t0(ISqlExpression3<V, E0, E1, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t1(ISqlExpression3<V, E0, E1, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t2(ISqlExpression3<V, E0, E1, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t3(ISqlExpression3<V, E0, E1, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t4(ISqlExpression3<V, E0, E1, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t5(ISqlExpression3<V, E0, E1, T5> expression) { return t5().x(expression); }
		public SelectChooser3<E0, E1, T0> t0() { return new SelectChooser3<>(this, 0); }
		public SelectChooser3<E0, E1, T1> t1() { return new SelectChooser3<>(this, 1); }
		public SelectChooser3<E0, E1, T2> t2() { return new SelectChooser3<>(this, 2); }
		public SelectChooser3<E0, E1, T3> t3() { return new SelectChooser3<>(this, 3); }
		public SelectChooser3<E0, E1, T4> t4() { return new SelectChooser3<>(this, 4); }
		public SelectChooser3<E0, E1, T5> t5() { return new SelectChooser3<>(this, 5); }
		<V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> x(ISqlExpression2<V, E0, E1> expression) { return addExpression(new SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V>(SqlSelect6_4.this, expression), expression); }
		SelectChooser2(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser3<E0, E1, E2> extends SelectChooserBase {

		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t0(ISqlExpression4<V, E0, E1, E2, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t1(ISqlExpression4<V, E0, E1, E2, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t2(ISqlExpression4<V, E0, E1, E2, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t3(ISqlExpression4<V, E0, E1, E2, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t4(ISqlExpression4<V, E0, E1, E2, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> t5(ISqlExpression4<V, E0, E1, E2, T5> expression) { return t5().x(expression); }
		public SelectChooser4<E0, E1, E2, T0> t0() { return new SelectChooser4<>(this, 0); }
		public SelectChooser4<E0, E1, E2, T1> t1() { return new SelectChooser4<>(this, 1); }
		public SelectChooser4<E0, E1, E2, T2> t2() { return new SelectChooser4<>(this, 2); }
		public SelectChooser4<E0, E1, E2, T3> t3() { return new SelectChooser4<>(this, 3); }
		public SelectChooser4<E0, E1, E2, T4> t4() { return new SelectChooser4<>(this, 4); }
		public SelectChooser4<E0, E1, E2, T5> t5() { return new SelectChooser4<>(this, 5); }
		<V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> x(ISqlExpression3<V, E0, E1, E2> expression) { return addExpression(new SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V>(SqlSelect6_4.this, expression), expression); }
		SelectChooser3(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser4<E0, E1, E2, E3> extends SelectChooserBase {

		<V> SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V> x(ISqlExpression4<V, E0, E1, E2, E3> expression) { return addExpression(new SqlSelect6_5<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V>(SqlSelect6_4.this, expression), expression); }
		SelectChooser4(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}
}

