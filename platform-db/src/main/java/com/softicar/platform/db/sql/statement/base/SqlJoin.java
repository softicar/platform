package com.softicar.platform.db.sql.statement.base;

import com.softicar.platform.db.sql.statement.ISqlJoin;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.Iterator;

public class SqlJoin<R, J> //
		extends AbstractSqlSelectOrJoin<R, J, ISqlJoin<R, J>> //
		implements ISqlJoin<R, J> {

	private final AbstractSqlSelect<R> select;

	protected SqlJoin(String alias, AbstractSqlSelect<R> select) {

		super(alias, select.getBuilder());
		this.select = select;
	}

	@Override
	public ISqlSelect<R> getSelect() {

		return select;
	}

	@Override
	public Iterator<R> iterator(int offset, int limit) {

		return select.iterator(offset, limit);
	}

	@Override
	public int count() {

		return select.count();
	}

	@Override
	protected <X> ISqlJoin<R, X> createJoin(String joinAlias) {

		return select.createJoin(joinAlias);
	}

	@Override
	protected ISqlJoin<R, J> getThis() {

		return this;
	}
}
