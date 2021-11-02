package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.common.core.singleton.Singleton;

public class CurrentDbTableRowCacheMap {

	private static final Singleton<DbTableRowCacheMap> CACHE_MAP = new Singleton<>(DbTableRowCacheMap::new);

	/**
	 * Returns the current instance of {@link DbTableRowCacheMap}.
	 * <p>
	 * If no instance was defined, this creates a new and empty instance.
	 *
	 * @return current instance (never null)
	 */
	public static DbTableRowCacheMap get() {

		return CACHE_MAP.get();
	}

	/**
	 * Defines the current instance of {@link DbTableRowCacheMap} to use.
	 *
	 * @param cacheMap
	 *            the instance to use (may be null)
	 */
	public static void set(DbTableRowCacheMap cacheMap) {

		CACHE_MAP.set(cacheMap);
	}
}
