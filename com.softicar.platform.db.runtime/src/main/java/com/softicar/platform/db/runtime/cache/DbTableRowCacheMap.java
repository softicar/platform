package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Maps {@link IDbTable} instances to instances of {@link DbTableRowCache}.
 *
 * @author Oliver Richers
 */
public class DbTableRowCacheMap {

	private final Map<IDbTable<?, ?>, DbTableRowCache<?, ?>> caches;

	public DbTableRowCacheMap() {

		this.caches = new IdentityHashMap<>();
	}

	public <R extends IDbTableRow<R, P>, P> DbTableRowCache<R, P> getCache(IDbTable<R, P> table) {

		return CastUtils.cast(caches.computeIfAbsent(table, dummy -> new DbTableRowCache<>(table)));
	}

	public Collection<DbTableRowCache<?, ?>> getAllCaches() {

		return caches.values();
	}
}
