package com.softicar.platform.db.runtime.table;

import com.softicar.platform.db.runtime.cache.IDbTableRowCache;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTable;
import com.softicar.platform.db.runtime.table.configuration.IDbTableConfiguration;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.validator.DbTableValidator;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IDbTable<R, P> extends IDbBasicTable<R> {

	/**
	 * Returns a reference to the internal {@link IDbTableRowCache} for this
	 * table.
	 *
	 * @return the internal {@link IDbTableRowCache} (never null)
	 */
	IDbTableRowCache<R, P> getCache();

	/**
	 * Returns the configuration of this table.
	 *
	 * @return the configuration (never null)
	 */
	IDbTableConfiguration<R, P> getConfiguration();

	/**
	 * If this table is a {@link IDbSubObjectTable}, the given {@link Consumer}
	 * will be called.
	 *
	 * @param consumer
	 *            a consumer (never null)
	 */
	void ifSubObjectTable(Consumer<IDbSubObjectTable<? super R, ?, ?>> consumer);

	// -------------------- structure -------------------- //

	/**
	 * Returns the primary key of this database table.
	 *
	 * @return the primary key (never null)
	 */
	IDbTableKey<R, P> getPrimaryKey();

	/**
	 * Gets a collection of all tables referenced via foreign keys.
	 *
	 * @return a collection of all referenced tables
	 */
	Set<IDbTable<?, ?>> getReferencedTables();

	/**
	 * Creates the structure of this table in the database.
	 * <p>
	 * If the table already exists, this method will throw an exception.
	 */
	void createTable();

	/**
	 * Inserts all default data into this database table.
	 * <p>
	 * This method primarily aims at reference data tables, e.g. enumeration
	 * tables.
	 */
	void insertDefaultData();

	// -------------------- getting by primary key -------------------- //

	/**
	 * Gets the table row with the given primary key from this table.
	 * <p>
	 * This method first queries the internal table row cache (see
	 * {@link IDbTableRowCache}). If the matching table row is found in the
	 * cache, it will be returned. In this case, no database query is executed,
	 * unless the {@link DbTableRowFlag#INVALIDATED} was set for that table row.
	 * <p>
	 * If no previously loaded table row can be found in the cache, a database
	 * query is executed to load the row from the database. The newly loaded
	 * table row is stored in the cache for later retrieval.
	 * <p>
	 * If no row with the specified primary key can be found, this method
	 * returns <i>null</i>.
	 * <p>
	 * If the given primary key to this method is <i>null</i>, this method
	 * returns <i>null</i> without doing anything else.
	 *
	 * @param primaryKey
	 *            the primary key of the row (may be null)
	 * @return the matching table row (may be null)
	 * @see IDbTableRowCache
	 */
	R get(P primaryKey);

	/**
	 * Gets all rows with the specified primary keys from this table.
	 * <p>
	 * Just like {@link #get(Object)}, this method first queries the internal
	 * table row cache (see {@link IDbTableRowCache}). A database query is only
	 * executed for those table rows that cannot be found in the cache and those
	 * that have the {@link DbTableRowFlag#INVALIDATED} flag.
	 * <p>
	 * Only table rows that exist are returned, so the number of table rows in
	 * the returned collection may be smaller then the number of supplied
	 * non-<i>null</i> primary keys. The returned collection never contains
	 * <i>null</i> values.
	 *
	 * @param primaryKeys
	 *            the primary keys of the table rows (never null; elements may
	 *            be null)
	 * @return a collection of all loaded table rows (never null)
	 * @see #get(Object)
	 */
	List<R> getAll(Collection<P> primaryKeys);

	/**
	 * Gets all rows with the specified primary keys from this table.
	 * <p>
	 * This method works exactly like {@link #getAll(Collection)} but allows the
	 * customization of the collection factory.
	 *
	 * @param primaryKeys
	 *            the primary keys of the table rows (never null; elements may
	 *            be null)
	 * @param collectionFactory
	 *            the factory to create the result collection (never null)
	 * @return a collection of all loaded table rows
	 * @see #get(Object)
	 */
	<C extends Collection<R>> C getAll(Collection<P> primaryKeys, Supplier<C> collectionFactory);

	/**
	 * Gets all rows with the specified primary keys from this table.
	 * <p>
	 * This method works exactly like {@link #getAll(Collection)} but collects
	 * the table rows in a {@link Map}. The map never contains <i>null</i> keys
	 * or values.
	 *
	 * @param primaryKeys
	 *            the primary keys of the table rows (never null; elements may
	 *            be null)
	 * @return a map of all loaded table rows, mapping from primary key to row
	 *         (never null)
	 * @see #get(Object)
	 */
	Map<P, R> getAllAsMap(Collection<P> primaryKeys);

	// -------------------- loading -------------------- //

	/**
	 * Loads the table row with the given primary key from this table.
	 * <p>
	 * Similar to {@link #get(Object)}, this method first queries the internal
	 * table row cache (see {@link IDbTableRowCache}).
	 * <p>
	 * In contrast to {@link #get(Object)}, this method executes a database
	 * query in any case. Table rows that are found in the table row cache are
	 * updated with fresh data from the database. That update is only omitted
	 * for table rows which are flagged as {@link DbTableRowFlag#DIRTY}.
	 * <p>
	 * If no row with the specified primary key can be found, this method
	 * returns <i>null</i>.
	 * <p>
	 * If the given primary key to this method is <i>null</i>, this method
	 * returns <i>null</i> without doing anything else.
	 *
	 * @param primaryKey
	 *            the primary key of the row (may be null)
	 * @return the matching table row (may be null)
	 * @see IDbTableRowCache
	 */
	R load(P primaryKey);

	/**
	 * Loads the table row with the given primary key from this table and locks
	 * it.
	 * <p>
	 * This method works exactly like {@link #load(Object)} with an additional
	 * locking of the table row.
	 *
	 * @param primaryKey
	 *            the primary key of the row (may be null)
	 * @param lock
	 *            the kind of {@link SqlSelectLock} to use (may be null)
	 * @return the matching table row (may be null)
	 * @see #load(Object)
	 */
	R load(P primaryKey, SqlSelectLock lock);

	// -------------------- stubs -------------------- //

	/**
	 * Gets a stubbed or cached table row with the given primary key from this
	 * table.
	 * <p>
	 * This method first queries the internal table row cache (see
	 * {@link IDbTableRowCache}). If the matching table row is found in the
	 * cache, it will be returned. Otherwise, a stubbed table row is created and
	 * returned (see {@link DbTableRowFlag#STUB}). This method <b>never</b>
	 * executes a database query.
	 * <p>
	 * If the given primary key to this method is <i>null</i>, this method
	 * returns <i>null</i> without doing anything else.
	 *
	 * @param primaryKey
	 *            the primary key of the table row (may be null)
	 * @return the corresponding table row or a stubbed row (may be null)
	 * @see IDbTableRowCache
	 */
	R getStub(P primaryKey);

	/**
	 * Gets stubbed or cached table rows with the given primary keys from this
	 * table.
	 * <p>
	 * This method first queries the internal table row cache (see
	 * {@link IDbTableRowCache}). All table rows that are found in the cache,
	 * will be returned as they are. For all other primary keys, a stubbed table
	 * row is created (see {@link DbTableRowFlag#STUB}). This method
	 * <b>never</b> executes a database query.
	 * <p>
	 * Stores the table rows in a {@link Map}. The map never contains
	 * <i>null</i> keys or values. The number of entries in the map corresponds
	 * to the number of supplied non-<i>null</i> primary keys.
	 *
	 * @param primaryKeys
	 *            the primary keys of the table rows (never null; elements may
	 *            be null)
	 * @return a map from the primary keys to the corresponding table rows
	 *         (never null)
	 */
	Map<P, R> getStubsAsMap(Iterable<? extends P> primaryKeys);

	// -------------------- validation -------------------- //

	/**
	 * Validates the structure of this table.
	 * <p>
	 * If it is invalid, an exception will be thrown.
	 */
	default void assertValidConfigurationOrThrow() {

		new DbTableValidator<>(this).validate();
	}
}
