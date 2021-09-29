package com.softicar.platform.db.runtime.query;

/**
 * Abstract base class of {@link IDbQueryRow}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDbQueryRow<R extends IDbQueryRow<R>> implements IDbQueryRow<R> {

	private final IDbQuery<R> query;

	protected AbstractDbQueryRow(IDbQuery<R> query) {

		this.query = query;
	}

	protected abstract R getThis();

	@Override
	public <V> V getValue(IDbQueryColumn<R, V> column) {

		return column.getValue(getThis());
	}

	@Override
	public String toString() {

		MapToStringBuilder builder = new MapToStringBuilder();
		for (IDbQueryColumn<R, ?> column: query.getColumns()) {
			builder.append(column.getName(), column.getValue(getThis()));
		}
		return builder.toString();
	}

	@Override
	public int compareTo(R other) {

		for (IDbQueryColumn<R, ?> column: query.getColumns()) {
			int cmp = column.compareRowValues(getThis(), other);
			if (cmp != 0) {
				return cmp;
			}
		}
		return 0;
	}
}
