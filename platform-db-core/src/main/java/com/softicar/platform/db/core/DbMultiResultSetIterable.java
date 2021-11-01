package com.softicar.platform.db.core;

import com.softicar.platform.db.core.connection.DbConnections;
import java.util.Iterator;

/**
 * An {@link Iterable} over a sequence of {@link DbResultSet}.
 *
 * @author Oliver Richers
 */
public class DbMultiResultSetIterable implements Iterator<DbResultSet>, Iterable<DbResultSet>, AutoCloseable {

	private DbResultSet resultSet;
	private boolean taken;

	public DbMultiResultSetIterable(DbResultSet resultSet) {

		this.resultSet = resultSet;
		this.taken = false;
	}

	/**
	 * Checks whether a call to {@link #next()} would be valid.
	 * <p>
	 * If this method returns <i>false</i> no further {@link DbResultSet}
	 * exists.
	 * <p>
	 * Caution: Every {@link DbResultSet} previously returned by {@link #next()}
	 * will be closed and may not be used anymore.
	 *
	 * @return <i>true</i> if more result sets are available; <i>false</i>
	 *         otherwise
	 */
	@Override
	public boolean hasNext() {

		if (taken) {
			this.resultSet = DbConnections.nextResultSet();
			this.taken = false;
		}
		return resultSet != null;
	}

	/**
	 * Returns the next {@link DbResultSet}.
	 * <p>
	 * Caution: Every {@link DbResultSet} previously returned by {@link #next()}
	 * will be closed and may not be used anymore.
	 *
	 * @return the next {@link DbResultSet} (may be null)
	 */
	@Override
	public DbResultSet next() {

		if (taken) {
			this.resultSet = DbConnections.nextResultSet();
			this.taken = false;
		} else {
			this.taken = true;
		}
		return resultSet;
	}

	/**
	 * Closes all {@link DbResultSet} objects.
	 */
	@Override
	public void close() {

		while (resultSet != null) {
			this.resultSet.close();
			this.resultSet = DbConnections.nextResultSet();
		}
	}

	@Override
	public Iterator<DbResultSet> iterator() {

		return this;
	}
}
