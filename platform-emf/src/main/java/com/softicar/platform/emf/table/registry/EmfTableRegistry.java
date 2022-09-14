package com.softicar.platform.emf.table.registry;

import com.softicar.platform.common.container.map.identity.IdentityHashList;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import com.softicar.platform.emf.module.EmfModulePackageMap;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.table.IEmfTraitTable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmfTableRegistry {

	private final Collection<IEmfTable<?, ?, ?>> tables;
	private final Map<IEmfTable<?, ?, ?>, Collection<IEmfTraitTable<?, ?>>> tableToTraitTablesMap;
	private final Map<UUID, Collection<IEmfTable<?, ?, ?>>> moduleUuidToTablesMap;
	private final EmfModulePackageMap modulePackageMap;

	private EmfTableRegistry() {

		this.tables = new IdentityHashList<>();
		this.tableToTraitTablesMap = new IdentityHashMap<>();
		this.moduleUuidToTablesMap = new HashMap<>();
		this.modulePackageMap = new EmfModulePackageMap(IEmfModuleRegistry.get());

		new DbTableFinder<>(IEmfTable.class, IEmfTableRow.class)//
			.findAllTables()
			.forEach(table -> registerTable(table.getValueClass(), table));
	}

	/**
	 * Returns the singleton instance of this class.
	 *
	 * @return the instance (never null)
	 */
	public static EmfTableRegistry getInstance() {

		return InstanceHolder.getInstance();
	}

	public Collection<IEmfTable<?, ?, ?>> getTables() {

		return Collections.unmodifiableCollection(tables);
	}

	public Collection<IEmfTable<?, ?, ?>> getTables(IEmfModule<?> module) {

		Collection<IEmfTable<?, ?, ?>> tables = moduleUuidToTablesMap.getOrDefault(module.getAnnotatedUuid(), Collections.emptySet());
		return Collections.unmodifiableCollection(tables);
	}

	public <I> Collection<I> getTables(Class<I> interfaceClass) {

		Collection<I> tables = this.tables//
			.stream()
			.filter(interfaceClass::isInstance)
			.map(interfaceClass::cast)
			.collect(Collectors.toList());
		return Collections.unmodifiableCollection(tables);
	}

	public <R extends IEmfTableRow<R, ?>> Collection<IEmfTraitTable<?, R>> getTraitTables(IEmfTable<R, ?, ?> table) {

		Collection<IEmfTraitTable<?, ?>> tables = tableToTraitTablesMap.getOrDefault(table, Collections.emptySet());
		return CastUtils.cast(Collections.unmodifiableCollection(tables));
	}

	// ------------------------------ private ------------------------------ //

	private void registerTable(Class<?> tableRowClass, IEmfTable<?, ?, ?> table) {

		addToTables(table);
		addToTraitTables(table);
		addToModuleToTablesMap(tableRowClass, table);
	}

	private void addToTables(IEmfTable<?, ?, ?> table) {

		tables.add(table);
	}

	private void addToTraitTables(IEmfTable<?, ?, ?> table) {

		if (table instanceof IEmfTraitTable) {
			IEmfTraitTable<?, ?> traitTable = (IEmfTraitTable<?, ?>) table;
			tableToTraitTablesMap.computeIfAbsent(traitTable.getTargetTable(), dummy -> new IdentityHashList<>()).add(traitTable);
		}
	}

	private void addToModuleToTablesMap(Class<?> tableRowClass, IEmfTable<?, ?, ?> table) {

		Optional<IEmfModule<?>> module = modulePackageMap.determineModule(tableRowClass);
		if (module.isPresent()) {
			moduleUuidToTablesMap.computeIfAbsent(module.get().getAnnotatedUuid(), dummy -> new IdentityHashList<>()).add(table);
		} else {
			Log.fverbose("Failed to determine module of table row class: %s", tableRowClass.getCanonicalName());
		}
	}

	private static class InstanceHolder {

		private static final EmfTableRegistry INSTANCE = new EmfTableRegistry();

		public static EmfTableRegistry getInstance() {

			return INSTANCE;
		}
	}
}
