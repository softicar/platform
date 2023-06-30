package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.choosers.SqlTableChooser8_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4> extends SqlSelectBase5<V0, V1, V2, V3, V4> implements ISqlSelectLastTableExtension<SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T7> {

	protected SqlSelect8_5(SqlSelectBase4<V0, V1, V2, V3> other, ISqlExpression<V4> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> select(ISqlExpression1<V, T7> expression) {

		return select().t7(expression);
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser8_0<Boolean, SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T0, T1, T2, T3, T4, T5, T6, T7> on() {

		return new SqlTableChooser8_0<>(this, PartType.JOIN);
	}

	public SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4> on(ISqlBooleanExpression2<T6, T7> condition) {

		return on().t6().t7(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser8_0<Boolean, SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T0, T1, T2, T3, T4, T5, T6, T7> where() {

		return new SqlTableChooser8_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser8_0<Object, SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T0, T1, T2, T3, T4, T5, T6, T7> groupBy() {

		return new SqlTableChooser8_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser8_0<Boolean, SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T0, T1, T2, T3, T4, T5, T6, T7> having() {

		return new SqlTableChooser8_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser8_0<Object, SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T0, T1, T2, T3, T4, T5, T6, T7> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser8_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser8_0<Object, SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T0, T1, T2, T3, T4, T5, T6, T7> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser8_0<Object, SqlSelect8_5<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4>, T0, T1, T2, T3, T4, T5, T6, T7> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t0(ISqlExpression1<V, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t1(ISqlExpression1<V, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t2(ISqlExpression1<V, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t3(ISqlExpression1<V, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t4(ISqlExpression1<V, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t5(ISqlExpression1<V, T5> expression) { return t5().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t6(ISqlExpression1<V, T6> expression) { return t6().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t7(ISqlExpression1<V, T7> expression) { return t7().x(expression); }
		public SelectChooser1<T0> t0() { return new SelectChooser1<>(this, 0); }
		public SelectChooser1<T1> t1() { return new SelectChooser1<>(this, 1); }
		public SelectChooser1<T2> t2() { return new SelectChooser1<>(this, 2); }
		public SelectChooser1<T3> t3() { return new SelectChooser1<>(this, 3); }
		public SelectChooser1<T4> t4() { return new SelectChooser1<>(this, 4); }
		public SelectChooser1<T5> t5() { return new SelectChooser1<>(this, 5); }
		public SelectChooser1<T6> t6() { return new SelectChooser1<>(this, 6); }
		public SelectChooser1<T7> t7() { return new SelectChooser1<>(this, 7); }
		<V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect8_6<>(SqlSelect8_5.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}

	public final class SelectChooser1<E0> extends SelectChooserBase {

		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t0(ISqlExpression2<V, E0, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t1(ISqlExpression2<V, E0, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t2(ISqlExpression2<V, E0, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t3(ISqlExpression2<V, E0, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t4(ISqlExpression2<V, E0, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t5(ISqlExpression2<V, E0, T5> expression) { return t5().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t6(ISqlExpression2<V, E0, T6> expression) { return t6().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t7(ISqlExpression2<V, E0, T7> expression) { return t7().x(expression); }
		public SelectChooser2<E0, T0> t0() { return new SelectChooser2<>(this, 0); }
		public SelectChooser2<E0, T1> t1() { return new SelectChooser2<>(this, 1); }
		public SelectChooser2<E0, T2> t2() { return new SelectChooser2<>(this, 2); }
		public SelectChooser2<E0, T3> t3() { return new SelectChooser2<>(this, 3); }
		public SelectChooser2<E0, T4> t4() { return new SelectChooser2<>(this, 4); }
		public SelectChooser2<E0, T5> t5() { return new SelectChooser2<>(this, 5); }
		public SelectChooser2<E0, T6> t6() { return new SelectChooser2<>(this, 6); }
		public SelectChooser2<E0, T7> t7() { return new SelectChooser2<>(this, 7); }
		<V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> x(ISqlExpression1<V, E0> expression) { return addExpression(new SqlSelect8_6<>(SqlSelect8_5.this, expression), expression); }
		SelectChooser1(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser2<E0, E1> extends SelectChooserBase {

		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t0(ISqlExpression3<V, E0, E1, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t1(ISqlExpression3<V, E0, E1, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t2(ISqlExpression3<V, E0, E1, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t3(ISqlExpression3<V, E0, E1, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t4(ISqlExpression3<V, E0, E1, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t5(ISqlExpression3<V, E0, E1, T5> expression) { return t5().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t6(ISqlExpression3<V, E0, E1, T6> expression) { return t6().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t7(ISqlExpression3<V, E0, E1, T7> expression) { return t7().x(expression); }
		public SelectChooser3<E0, E1, T0> t0() { return new SelectChooser3<>(this, 0); }
		public SelectChooser3<E0, E1, T1> t1() { return new SelectChooser3<>(this, 1); }
		public SelectChooser3<E0, E1, T2> t2() { return new SelectChooser3<>(this, 2); }
		public SelectChooser3<E0, E1, T3> t3() { return new SelectChooser3<>(this, 3); }
		public SelectChooser3<E0, E1, T4> t4() { return new SelectChooser3<>(this, 4); }
		public SelectChooser3<E0, E1, T5> t5() { return new SelectChooser3<>(this, 5); }
		public SelectChooser3<E0, E1, T6> t6() { return new SelectChooser3<>(this, 6); }
		public SelectChooser3<E0, E1, T7> t7() { return new SelectChooser3<>(this, 7); }
		<V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> x(ISqlExpression2<V, E0, E1> expression) { return addExpression(new SqlSelect8_6<>(SqlSelect8_5.this, expression), expression); }
		SelectChooser2(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser3<E0, E1, E2> extends SelectChooserBase {

		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t0(ISqlExpression4<V, E0, E1, E2, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t1(ISqlExpression4<V, E0, E1, E2, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t2(ISqlExpression4<V, E0, E1, E2, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t3(ISqlExpression4<V, E0, E1, E2, T3> expression) { return t3().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t4(ISqlExpression4<V, E0, E1, E2, T4> expression) { return t4().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t5(ISqlExpression4<V, E0, E1, E2, T5> expression) { return t5().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t6(ISqlExpression4<V, E0, E1, E2, T6> expression) { return t6().x(expression); }
		public <V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> t7(ISqlExpression4<V, E0, E1, E2, T7> expression) { return t7().x(expression); }
		public SelectChooser4<E0, E1, E2, T0> t0() { return new SelectChooser4<>(this, 0); }
		public SelectChooser4<E0, E1, E2, T1> t1() { return new SelectChooser4<>(this, 1); }
		public SelectChooser4<E0, E1, E2, T2> t2() { return new SelectChooser4<>(this, 2); }
		public SelectChooser4<E0, E1, E2, T3> t3() { return new SelectChooser4<>(this, 3); }
		public SelectChooser4<E0, E1, E2, T4> t4() { return new SelectChooser4<>(this, 4); }
		public SelectChooser4<E0, E1, E2, T5> t5() { return new SelectChooser4<>(this, 5); }
		public SelectChooser4<E0, E1, E2, T6> t6() { return new SelectChooser4<>(this, 6); }
		public SelectChooser4<E0, E1, E2, T7> t7() { return new SelectChooser4<>(this, 7); }
		<V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> x(ISqlExpression3<V, E0, E1, E2> expression) { return addExpression(new SqlSelect8_6<>(SqlSelect8_5.this, expression), expression); }
		SelectChooser3(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser4<E0, E1, E2, E3> extends SelectChooserBase {

		<V> SqlSelect8_6<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V> x(ISqlExpression4<V, E0, E1, E2, E3> expression) { return addExpression(new SqlSelect8_6<>(SqlSelect8_5.this, expression), expression); }
		SelectChooser4(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}
}

