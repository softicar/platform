package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Utility methods for {@link DbTableRowCacheMap}.
 *
 * @author Oliver Richers
 */
public class DbTableRowCaches {

	/**
	 * Invalidates all {@link DbTableRowCache} instances.
	 * <p>
	 * Effectively, this calls {@link IDbTableRowCache#invalidateAll()} on all
	 * existing caches.
	 */
	public static void invalidateAll() {

		CurrentDbTableRowCacheMap//
			.get()
			.getAllCaches()
			.forEach(DbTableRowCache::invalidateAll);
	}

	/**
	 * Reloads all {@link IDbTableRow} objects of all {@link DbTableRowCache}
	 * instances.
	 * <p>
	 * Effectively, this calls {@link IDbTableRowCache#reloadAll()} on all
	 * existing caches.
	 * <p>
	 * Warning: This method can produce a high load on the server and should be
	 * avoided. Instead, you should prefer to call {@link #invalidateAll()}.
	 */
	public static void reloadAll() {

		CurrentDbTableRowCacheMap//
			.get()
			.getAllCaches()
			.forEach(DbTableRowCache::reloadAll);
	}
}
