package com.softicar.platform.db.runtime.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * A weak reference used to hold a reference to a cached database table row.
 *
 * @author Oliver Richers
 */
public class DbTableRowCacheReference<R, P> extends WeakReference<R> {

	private final P primaryKey;
	private final boolean isNull;

	protected DbTableRowCacheReference(P primaryKey, R row, ReferenceQueue<R> queue) {

		super(row, queue);

		this.primaryKey = primaryKey;
		this.isNull = row == null;
	}

	public P getPrimaryKey() {

		return primaryKey;
	}

	public boolean isNull() {

		return isNull;
	}
}
