package com.softicar.platform.db.sql.statement.base;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.statement.ISqlJoin;
import com.softicar.platform.db.sql.statement.ISqlSelectOrJoin;
import com.softicar.platform.db.sql.statement.builder.SqlSelectBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlGroupByClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlHeadClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlOrderByClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSqlSelectOrJoin<R, J, This> //
		extends AbstractSqlConditionalStatement<J, This> //
		implements ISqlSelectOrJoin<R, J, This> {

	private final String alias;
	private final SqlSelectBuilder builder;

	protected AbstractSqlSelectOrJoin(String alias, SqlSelectBuilder builder) {

		this.alias = alias;
		this.builder = builder;
	}

	// -------------------------------- PUBLIC -------------------------------- //

	@Override
	public R getFirst() {

		List<R> tmp = list(1);
		return tmp.isEmpty()? null : tmp.get(0);
	}

	@Override
	public R getOne() {

		List<R> tmp = list(2);
		if (tmp.size() > 1) {
			throw new SofticarDeveloperException("Query returned more objects than expected.");
		}
		return tmp.isEmpty()? null : tmp.get(0);
	}

	@Override
	public List<R> list() {

		return list(0, 0);
	}

	@Override
	public List<R> list(int limit) {

		return list(0, limit);
	}

	@Override
	public List<R> list(int offset, int limit) {

		return stream(offset, limit)//
			.collect(Collectors.toList());
	}

	@Override
	public <X> ISqlJoin<R, X> join(ISqlForeignRowField<J, X, ?> field) {

		return join(field.getTargetField().getTable(), field.getTargetField(), field);
	}

	@Override
	public <X> ISqlJoin<R, X> joinReverse(ISqlForeignRowField<X, J, ?> field) {

		return join(field.getTable(), field, field.getTargetField());
	}

	@Override
	public <X> ISqlJoin<R, X> joinLeft(ISqlForeignRowField<J, X, ?> field) {

		return joinLeft(field.getTargetField().getTable(), field.getTargetField(), field);
	}

	@Override
	public <X> ISqlJoin<R, X> joinLeftReverse(ISqlForeignRowField<X, J, ?> field) {

		return joinLeft(field.getTable(), field, field.getTargetField());
	}

	@Override
	public This where(ISqlBooleanExpression<J> expression, boolean doWhere) {

		getWhereBuilder().where(expression, doWhere);
		return getThis();
	}

	@Override
	public This orWhere(ISqlBooleanExpression<J> expression, boolean doWhere) {

		getWhereBuilder().orWhere(expression, doWhere);
		return getThis();
	}

	@Override
	public This orderBy(ISqlField<J, ?> field) {

		getOrderByBuilder().orderBy(field);
		return getThis();
	}

	@Override
	public This orderDescendingBy(ISqlField<J, ?> field) {

		getOrderByBuilder().orderDescendingBy(field);
		return getThis();
	}

	@Override
	public This groupBy(ISqlField<J, ?> field) {

		getGroupByBuilder().groupBy(field);
		return getThis();
	}

	@Override
	public String getSelectText() {

		return getBuilder().getSelectText().toString();
	}

	@Override
	public String toString() {

		return getSelectText();
	}

	// -------------------------------- ABSTRACT -------------------------------- //

	protected abstract <X> ISqlJoin<R, X> createJoin(String joinAlias);

	// -------------------------------- BUILDERS -------------------------------- //

	protected SqlSelectBuilder getBuilder() {

		return builder;
	}

	@Override
	protected SqlWhereClauseBuilder getWhereBuilder() {

		getBuilder().setAlias(alias);
		return getBuilder().getWhereBuilder();
	}

	protected SqlHeadClauseBuilder getHeadBuilder() {

		getBuilder().setAlias(alias);
		return getBuilder().getHeadClauseBuilder();
	}

	protected SqlOrderByClauseBuilder getOrderByBuilder() {

		getBuilder().setAlias(alias);
		return getBuilder().getOrderByBuilder();
	}

	protected SqlGroupByClauseBuilder getGroupByBuilder() {

		getBuilder().setAlias(alias);
		return getBuilder().getGroupByBuilder();
	}

	// -------------------------------- PRIVATE -------------------------------- //

	private <X> ISqlJoin<R, X> join(ISqlTable<X> joinTable, ISqlField<X, ?> joinField, ISqlField<J, ?> thisField) {

		return join(joinTable, joinField, thisField, false);
	}

	private <X> ISqlJoin<R, X> joinLeft(ISqlTable<X> joinTable, ISqlField<X, ?> joinField, ISqlField<J, ?> thisField) {

		return join(joinTable, joinField, thisField, true);
	}

	private <X> ISqlJoin<R, X> join(ISqlTable<X> joinTable, ISqlField<X, ?> joinField, ISqlField<J, ?> thisField, boolean leftJoin) {

		SqlHeadClauseBuilder builder = getHeadBuilder();
		String joinAlias = builder.allocateJoinAlias(joinTable);
		ISqlJoin<R, X> join = createJoin(joinAlias);
		builder
			.addText(
				" %sJOIN %s %s ON %s.%s = %s.%s",
				leftJoin? "LEFT " : "",
				joinTable.getFullName().getQuoted(),
				joinAlias,
				joinAlias,
				builder.getQuotedName(joinField),
				alias,
				builder.getQuotedName(thisField));
		return join;
	}
}
