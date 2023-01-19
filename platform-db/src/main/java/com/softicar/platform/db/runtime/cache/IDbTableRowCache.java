package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.transients.DbTransientValueCache;
import java.util.Collection;
import java.util.List;

/**
 * A cache for instances of {@link IDbTableRow}.
 * <p>
 * Each {@link IDbTable} owns an instance of this cache. This cache is used to
 * provide the <b>invariance</b> that for every primary key of a given
 * {@link IDbTable}, only a single instance of {@link IDbTableRow} exists within
 * the realm of one thread.
 * <p>
 * The primary purpose of this invariance is to ensure that the modification of
 * an {@link IDbTableRow} is consistently visible throughout the whole thread.
 * It also ensures that equality and identity of {@link IDbTableRow} is the
 * same. Finally, it also reduced memory consumption.
 *
 * @author Oliver Richers
 */
public interface IDbTableRowCache<R, P> {

	/**
	 * Returns the corresponding table row from this cache.
	 *
	 * @param primaryKey
	 *            the primary key of the table row
	 * @return the table row or <i>null</i> if no matching row is cached
	 */
	R getSimple(P primaryKey);

	/**
	 * Returns all table rows contained in this cache.
	 * <p>
	 * <b>Warning:</b> This is a potentially expensive operation, depending on
	 * the number of cached rows.
	 *
	 * @return a list of all contained table rows
	 */
	List<R> getAllValues();

	/**
	 * Removes the table row with the specified primary key from this cache.
	 * <p>
	 * If this cache does not contain a matching table row, this method does
	 * nothing.
	 *
	 * @param primaryKey
	 *            the primary key of the table row to remove, never <i>null</i>
	 */
	void remove(P primaryKey);

	/**
	 * Adds the specified table row to this cache.
	 *
	 * @param primaryKey
	 *            the primary key of the table row to add, never <i>null</i>
	 * @param object
	 *            the table row to add, never <i>null</i>
	 */
	void put(P primaryKey, R object);

	/**
	 * Invalidates the table row with the given primary key in this cache.
	 * <p>
	 * The invalidation sets the reload flag of the respective row, which means
	 * that all data of the row will be reloaded on the next access.
	 * <p>
	 * If no row with the given primary key is in this cache, this method does
	 * nothing.
	 *
	 * @param primaryKey
	 *            the primary key of the table row to invalidate (never
	 *            <i>null</i>)
	 */
	void invalidate(P primaryKey);

	/**
	 * Invalidates all table rows in this cache.
	 * <p>
	 * This method has the same effect as calling {@link #invalidate} on all
	 * contained rows.
	 */
	void invalidateAll();

	/**
	 * Reloads the table row with the given primary key from the database
	 * server.
	 * <p>
	 * If no table row with the given primary key is in this cache, this method
	 * does nothing.
	 *
	 * @param primaryKey
	 *            the primary key of the table row to reload (never <i>null</i>)
	 */
	void reload(P primaryKey);

	/**
	 * Reloads all table rows in this cache.
	 */
	void reloadAll();

	/**
	 * Refreshes the given stub rows and/or invalidated rows in this cache.
	 *
	 * @param rows
	 *            the rows to refresh (never <i>null</i>)
	 */
	void refresh(Collection<R> rows);

	/**
	 * Returns the transient value cache.
	 *
	 * @return the transient value cache (never <i>null</i>)
	 */
	DbTransientValueCache<R> getTransientValueCache();
}
