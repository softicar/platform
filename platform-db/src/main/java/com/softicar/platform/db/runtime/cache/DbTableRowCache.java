package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagWriter;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.transients.DbTransientValueCache;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Primary implementation of {@link IDbTableRowCache}.
 *
 * @author Oliver Richers
 */
public class DbTableRowCache<R extends IDbTableRow<R, P>, P> implements IDbTableRowCache<R, P> {

	private final Map<P, DbTableRowCacheReference<R, P>> map;
	private final ReferenceQueue<R> referenceQueue;
	private final IDbTable<R, P> table;
	private final DbTransientValueCache<R> transientValueCache;
	private final DbTableRowCacheRefresher<R, P> cacheRefresher;

	public DbTableRowCache(IDbTable<R, P> table) {

		this.map = new TreeMap<>(table.getPrimaryKey());
		this.referenceQueue = new ReferenceQueue<>();
		this.table = table;
		this.transientValueCache = new DbTransientValueCache<>();
		this.cacheRefresher = new DbTableRowCacheRefresher<>(table);
	}

	@Override
	public R getSimple(P primaryKey) {

		removeDeadReferences();
		DbTableRowCacheReference<R, P> reference = map.get(primaryKey);
		return reference != null? reference.get() : null;
	}

	@Override
	public void put(P primaryKey, R object) {

		removeDeadReferences();
		DbTableRowCacheReference<R, P> previous = map.put(primaryKey, new DbTableRowCacheReference<>(primaryKey, object, referenceQueue));
		if (previous != null && previous.get() != null) {
			String valueClassName = table.getValueClass().getCanonicalName();
			throw new SofticarDeveloperException(
				"An internal error of the database framework has occurred for %s with primary key %s.",
				valueClassName,
				primaryKey);
		}
	}

	@Override
	public void remove(P primaryKey) {

		removeDeadReferences();
		map.remove(primaryKey);
	}

	@Override
	public List<R> getAllValues() {

		removeDeadReferences();
		ArrayList<R> result = new ArrayList<>(map.size());
		for (DbTableRowCacheReference<R, P> reference: map.values()) {
			R value = reference.get();
			if (value != null) {
				result.add(value);
			}
		}
		return result;
	}

	private void removeDeadReferences() {

		Reference<? extends R> reference;
		while ((reference = referenceQueue.poll()) != null) {
			DbTableRowCacheReference<? extends R, P> collectedReference = CastUtils.cast(reference);

			DbTableRowCacheReference<R, P> removedReference = map.remove(collectedReference.getPrimaryKey());
			if (removedReference != null) {
				R value = removedReference.get();
				if (value != null) {
					// NOTE: It is possible that the collected reference and the
					//       current, removed reference are not the same, so we
					//       have to put the current reference back!!!
					map.put(removedReference.getPrimaryKey(), removedReference);
				}
			}
		}
	}

	@Override
	public void invalidate(P primaryKey) {

		R object = getSimple(primaryKey);
		if (object != null) {
			object.invalidate();
		}
	}

	@Override
	public void invalidateAll() {

		removeDeadReferences();
		for (DbTableRowCacheReference<R, P> reference: map.values()) {
			R object = reference.get();
			if (object != null) {
				new DbTableRowFlagWriter(object).setInvalidated(true);
			}
		}
		transientValueCache.invalidateAll();
	}

	@Override
	public void reload(P primaryKey) {

		R object = getSimple(primaryKey);
		if (object != null) {
			object.reload();
		}
	}

	@Override
	public void reloadAll() {

		table.reloadAll(getAllValues());
	}

	@Override
	public void refresh(Collection<R> rows) {

		cacheRefresher.refresh(rows);
	}

	@Override
	public DbTransientValueCache<R> getTransientValueCache() {

		return transientValueCache;
	}
}
