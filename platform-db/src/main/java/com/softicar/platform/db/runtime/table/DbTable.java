package com.softicar.platform.db.runtime.table;

import com.softicar.platform.common.core.thread.utils.ThreadSafeLazySupplier;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.cache.CurrentDbTableRowCacheMap;
import com.softicar.platform.db.runtime.cache.IDbTableRowCache;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTable;
import com.softicar.platform.db.runtime.table.configuration.DbTableConfiguration;
import com.softicar.platform.db.runtime.table.configuration.IDbTableConfiguration;
import com.softicar.platform.db.runtime.table.creator.DbTableStructureCreator;
import com.softicar.platform.db.runtime.table.listener.IDbTableListener;
import com.softicar.platform.db.runtime.table.logic.DbTableRowComparator;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFieldReader;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFieldWriter;
import com.softicar.platform.db.runtime.table.logic.DbTableRowGetter;
import com.softicar.platform.db.runtime.table.logic.DbTableRowLoader;
import com.softicar.platform.db.runtime.table.logic.DbTableRowResultSetReader;
import com.softicar.platform.db.runtime.table.logic.DbTableRowStubber;
import com.softicar.platform.db.runtime.table.logic.save.DbTableRowSaver;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.table.statements.DbTableDelete;
import com.softicar.platform.db.runtime.table.statements.DbTableInsert;
import com.softicar.platform.db.runtime.table.statements.DbTableSelect;
import com.softicar.platform.db.runtime.table.statements.DbTableUpdate;
import com.softicar.platform.db.runtime.transients.IDbTransientField;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.ISqlDelete;
import com.softicar.platform.db.sql.statement.ISqlInsert;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.db.sql.statement.ISqlUpdate;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DbTable<R extends IDbTableRow<R, P>, P> implements IDbTable<R, P> {

	private final IDbTableBuilder<R, P> builder;
	private final Supplier<DbTableConfiguration<R, P>> configurationSupplier;
	private List<IDbTableListener<R>> tableListeners;

	public DbTable(IDbTableBuilder<R, P> builder) {

		this.builder = builder;
		this.builder.setTable(this);
		this.configurationSupplier = new ThreadSafeLazySupplier<>(this::createConfiguration);
		this.tableListeners = null;
	}

	@Override
	public final DbTableName getFullName() {

		return builder.getTableName();
	}

	@Override
	public final IDbTableRowCache<R, P> getCache() {

		return CurrentDbTableRowCacheMap.get().getCache(this);
	}

	@Override
	public Supplier<R> getRowFactory() {

		return builder.getRowFactory();
	}

	@Override
	public IDbTableConfiguration<R, P> getConfiguration() {

		return configurationSupplier.get();
	}

	@Override
	public void ifSubObjectTable(Consumer<IDbSubObjectTable<? super R, ?, ?>> consumer) {

		// nothing to do
	}

	@Override
	public boolean hasTableListeners() {

		return tableListeners != null;
	}

	@Override
	public Iterable<IDbTableListener<R>> getTableListeners() {

		return tableListeners != null? tableListeners : Collections.emptyList();
	}

	// -------------------- expression methods -------------------- //

	/**
	 * Returns the value to of this table, which is the table itself.
	 *
	 * @return the value type of entries in this table
	 */
	@Override
	public final ISqlValueType<R> getValueType() {

		return this;
	}

	@Override
	public final void build(ISqlClauseBuilder builder) {

		builder.fixTable();
		for (int i = 0; i < getAllFields().size(); ++i) {
			if (i > 0) {
				builder.addText(", ");
			}
			builder.addField(getAllFields().get(i));
		}
		builder.unfixTable();
	}

	// -------------------- value type methods -------------------- //

	@Override
	public final R getValue(DbResultSet resultSet, int index) {

		return new DbTableRowResultSetReader<>(this, resultSet, index).read();
	}

	@Override
	public final Class<R> getValueClass() {

		return builder.getRowClass();
	}

	@Override
	public final int compare(R left, R right) {

		return new DbTableRowComparator<>(this).compare(left, right);
	}

	@Override
	public final int getColumnCount() {

		return getAllFields().size();
	}

	// -------------------- keys -------------------- //

	@Override
	public Collection<IDbKey<R>> getAllKeys() {

		return builder.getAllKeys();
	}

	@Override
	public Collection<? extends IDbKey<R>> getUniqueKeys() {

		return builder.getUniqueKeys();
	}

	@Override
	public Collection<? extends IDbKey<R>> getIndexKeys() {

		return builder.getIndexKeys();
	}

	// -------------------- primary key -------------------- //

	@Override
	public final IDbTableKey<R, P> getPrimaryKey() {

		return builder.getPrimaryKey();
	}

	// -------------------- fields -------------------- //

	@Override
	public final List<IDbField<R, ?>> getAllFields() {

		return builder.getAllFields();
	}

	@Override
	public final List<IDbField<R, ?>> getControlFields() {

		return builder.getControlFields();
	}

	@Override
	public final List<IDbField<R, ?>> getDataFields() {

		return builder.getDataFields();
	}

	// -------------------- field access -------------------- //

	@Override
	public final <V> V getValue(R row, IDbField<R, V> field) {

		return new DbTableRowFieldReader<>(row, field).read();
	}

	@Override
	public final <V> boolean setValue(R row, IDbField<R, V> field, V value) {

		return new DbTableRowFieldWriter<>(row, field).write(value);
	}

	// -------------------- transient field access -------------------- //

	@Override
	public <V> V getTransientValue(R row, IDbTransientField<R, V> field) {

		return getCache().getTransientValueCache().getTransientValue(row, field);
	}

	@Override
	public <V> void setTransientValue(R row, IDbTransientField<R, V> field, V value) {

		getCache().getTransientValueCache().setTransientValue(row, field, value);
	}

	// -------------------- statements -------------------- //

	@Override
	public final ISqlDelete<R> createDelete() {

		return new DbTableDelete<>(this);
	}

	@Override
	public ISqlInsert<R> createInsert() {

		return new DbTableInsert<>(this);
	}

	@Override
	public ISqlUpdate<R> createUpdate() {

		return new DbTableUpdate<>(this);
	}

	@Override
	public final ISqlSelect<R> createSelect() {

		return createSelect(null);
	}

	@Override
	public final ISqlSelect<R> createSelect(SqlSelectLock lock) {

		return new DbTableSelect<>(this, lock);
	}

	// -------------------- getting by primary key -------------------- //

	@Override
	public final R get(P primaryKey) {

		return new DbTableRowGetter<>(this, primaryKey).getAsMap().get(primaryKey);
	}

	@Override
	public final List<R> getAll(Collection<P> primaryKeys) {

		return new DbTableRowGetter<>(this, primaryKeys).getAsList();
	}

	@Override
	public final <C extends Collection<R>> C getAll(Collection<P> primaryKeys, Supplier<C> collectionFactory) {

		return new DbTableRowGetter<>(this, primaryKeys).getAsCollection(collectionFactory);
	}

	@Override
	public final Map<P, R> getAllAsMap(Collection<P> primaryKeys) {

		return new DbTableRowGetter<>(this, primaryKeys).getAsMap();
	}

	// -------------------- loading -------------------- //

	@Override
	public final R load(P key) {

		return load(key, null);
	}

	@Override
	public final R load(P key, SqlSelectLock lock) {

		// TODO: may not invalidate loaded rows
		return key != null? new DbTableRowLoader<>(this).addKey(key).setLock(lock).loadAsMap().get(key) : null;
	}

	@Override
	public final List<R> loadAll() {

		return createSelect().list();
	}

	// -------------------- reloading -------------------- //

	@Override
	public final void reloadAll(Collection<R> rows) {

		new DbTableRowLoader<>(this).addRows(rows.stream()).reload();
	}

	@Override
	public final void reloadAllForUpdate(Collection<R> rows) {

		new DbTableRowLoader<>(this).setLock(SqlSelectLock.FOR_UPDATE).addRows(rows.stream()).reload();
	}

	// -------------------- stubs -------------------- //

	@Override
	public final R getStub(DbResultSet resultSet, int index) {

		return getStub(getPrimaryKey().getFromResultSet(resultSet, index));
	}

	@Override
	public final R getStub(P primaryKey) {

		return new DbTableRowStubber<>(this, primaryKey).stub();
	}

	@Override
	public Map<P, R> getStubsAsMap(Iterable<? extends P> primaryKeys) {

		Map<P, R> stubs = new TreeMap<>();
		for (P key: primaryKeys) {
			if (key != null) {
				stubs.put(key, getStub(key));
			}
		}
		return stubs;
	}

	@Override
	public final void refreshAll(Collection<R> staleRows) {

		getCache().refresh(staleRows);
	}

	// -------------------- saving of rows -------------------- //

	@Override
	public final void save(R row) {

		new DbTableRowSaver<>(this).addRow(row).save();
	}

	@Override
	public final void saveAll(Collection<? extends R> rows) {

		new DbTableRowSaver<>(this).addRows(rows).save();
	}

	@Override
	public final void saveAll(Collection<? extends R> rows, int chunkSize) {

		new DbTableRowSaver<>(this).addRows(rows).setChunkSize(chunkSize).save();
	}

	@Override
	public final void saveAllIfNecessary(Collection<? extends R> rows) {

		new DbTableRowSaver<>(this).addRows(rows).setLazyMode(true).save();
	}

	@Override
	public final void saveAllIfNecessary(Collection<? extends R> rows, int chunkSize) {

		new DbTableRowSaver<>(this).addRows(rows).setChunkSize(chunkSize).setLazyMode(true).save();
	}

	// -------------------- table definition -------------------- //

	@Override
	public final void createTable() {

		new DbTableStructureCreator(this).create();
	}

	@Override
	public final void insertDefaultData() {

		getConfiguration().getDataInitializer().initializeData();
	}

	@Override
	public Set<IDbTable<?, ?>> getReferencedTables() {

		return getAllFields()
			.stream()
			.map(field -> field.cast().toForeignRowField())
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(IDbForeignRowField::getTargetTable)
			.filter(table -> !table.equals(this))
			.collect(Collectors.toSet());
	}

	// -------------------- basics -------------------- //

	/**
	 * Returns the fully qualified name of this table.
	 */
	@Override
	public final String toString() {

		return getFullName().toString();
	}

	/**
	 * Returns true if the two tables point to the same physical database table.
	 */
	@Override
	public final boolean equals(Object other) {

		if (other instanceof ISqlTable<?>) {
			ISqlTable<?> otherTable = (ISqlTable<?>) other;
			return getFullName().equals(otherTable.getFullName());
		} else {
			return false;
		}
	}

	@Override
	public final int hashCode() {

		return getFullName().hashCode();
	}

	// -------------------- protected -------------------- //

	/**
	 * Adds a new table listener to this table.
	 * <p>
	 * Table listeners are singletons any may not contain non-final fields.
	 *
	 * @param tableListener
	 *            the table lister to add (never null)
	 */
	protected void addTableListener(IDbTableListener<R> tableListener) {

		if (tableListeners == null) {
			this.tableListeners = new ArrayList<>();
		}
		this.tableListeners.add(tableListener);
	}

	protected void customizeTableConfiguration(DbTableConfiguration<R, P> configuration) {

		// nothing to configure by default
		DevNull.swallow(configuration);
	}

	// -------------------- private -------------------- //

	private DbTableConfiguration<R, P> createConfiguration() {

		DbTableConfiguration<R, P> configuration = new DbTableConfiguration<>(builder);
		customizeTableConfiguration(configuration);
		return configuration;
	}
}
