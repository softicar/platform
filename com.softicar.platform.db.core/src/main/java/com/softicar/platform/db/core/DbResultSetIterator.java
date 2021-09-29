package com.softicar.platform.db.core;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An {@link Iterator} over a {@link DbResultSet}.
 *
 * @author Oliver Richers
 */
public class DbResultSetIterator<R> implements Iterator<R> {

	private final DbResultSet resultSet;
	private final Function<DbResultSet, R> resultSetReader;
	private boolean hasNext;
	private R next;

	public DbResultSetIterator(Supplier<DbResultSet> resultSetSupplier, Function<DbResultSet, R> resultSetReader) {

		this.resultSet = resultSetSupplier.get();
		this.resultSetReader = resultSetReader;
		fetchNext();
	}

	@Override
	public final boolean hasNext() {

		return hasNext;
	}

	@Override
	public final R next() {

		if (!hasNext) {
			throw new NoSuchElementException();
		}

		R next = this.next;
		fetchNext();
		return next;
	}

	@Override
	public final void remove() {

		throw new UnsupportedOperationException();
	}

	private final void fetchNext() {

		this.hasNext = resultSet.next();
		if (hasNext) {
			this.next = resultSetReader.apply(resultSet);
		} else {
			this.resultSet.close();
			this.next = null;
		}
	}
}
