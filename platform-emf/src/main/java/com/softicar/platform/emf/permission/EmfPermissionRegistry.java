package com.softicar.platform.emf.permission;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.authorizer.IEmfAuthorizer;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.page.EmfPages;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.finder.EmfStaticPermissionFinder;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.registry.EmfTableRegistry;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

/**
 * A registry of existing {@link IEmfPermission} instances.
 * <p>
 * Accessible via {@link CurrentEmfPermissionRegistry}.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class EmfPermissionRegistry implements IEmfPermissionRegistry {

	private final Collection<IEmfPermission<?>> permissions;
	private final Collection<IEmfPermission<?>> atomicPermissions;
	private final Map<IEmfTable<?, ?, ?>, Collection<IEmfPermission<?>>> tableToPermissionsMap;
	private final Map<IEmfTable<?, ?, ?>, Collection<IEmfPermission<?>>> tableToAtomicPermissionsMap;
	private final Map<IEmfPermission<?>, Collection<IEmfTable<?, ?, ?>>> atomicPermissionToTablesMap;
	private final Collection<IEmfModule<?>> modules;
	private final Map<IEmfModule<?>, Collection<IEmfStaticPermission<?>>> moduleToStaticPermissionsMap;
	private final Map<UUID, IEmfStaticPermission<?>> uuidToStaticPermissionMap;
	private final Map<IEmfTable<?, ?, ?>, Collection<IEmfStaticPermission<?>>> tableToStaticPermissionsMap;
	private final Map<IEmfModule<?>, Map<IEmfPermission<?>, Collection<IEmfPage<?>>>> moduleToPermissionsToPagesMap;

	EmfPermissionRegistry() {

		this.permissions = new HashList<>();
		this.atomicPermissions = new HashList<>();
		this.tableToPermissionsMap = new HashMap<>();
		this.tableToAtomicPermissionsMap = new HashMap<>();
		this.atomicPermissionToTablesMap = new HashMap<>();
		this.modules = new HashList<>();
		this.moduleToStaticPermissionsMap = new HashMap<>();
		this.uuidToStaticPermissionMap = new TreeMap<>();
		this.tableToStaticPermissionsMap = new HashMap<>();
		this.moduleToPermissionsToPagesMap = new HashMap<>();

		for (IEmfModule<?> module: CurrentEmfModuleRegistry.get().getAllModules()) {
			registerModule(module);
			for (IEmfTable<?, ?, ?> table: EmfTableRegistry.getInstance().getTables(module)) {
				registerPermissionsFromAuthorizer(module, table);
				registerPermissionsFromPrimaryActions(module, table);
				registerPermissionsFromCommonActions(module, table);
				registerPermissionsFromManagementActions(module, table);
				registerPermissionsFromTableClass(module, table);
			}
		}
		registerPagePermissions();
	}

	@Override
	public Collection<IEmfModule<?>> getModules() {

		return modules;
	}

	@Override
	public Collection<IEmfPermission<?>> getPermissions() {

		return permissions;
	}

	@Override
	public Collection<IEmfPermission<?>> getPermissions(IEmfTable<?, ?, ?> table) {

		return tableToPermissionsMap.getOrDefault(table, Collections.emptySet());
	}

	@Override
	public Collection<IEmfPermission<?>> getPermissions(IEmfModule<?> module) {

		Collection<IEmfPermission<?>> permissions = new HashList<>();
		for (IEmfTable<?, ?, ?> table: EmfTableRegistry.getInstance().getTables(module)) {
			permissions.addAll(tableToPermissionsMap.getOrDefault(table, Collections.emptySet()));
		}
		return permissions;
	}

	@Override
	public Collection<IEmfStaticPermission<?>> getStaticPermissions(IEmfModule<?> module) {

		return moduleToStaticPermissionsMap.getOrDefault(module, Collections.emptySet());
	}

	@Override
	public Optional<IEmfStaticPermission<?>> getStaticPermission(UUID permissionUuid) {

		return Optional.ofNullable(uuidToStaticPermissionMap.get(permissionUuid));
	}

	@Override
	public Collection<IEmfStaticPermission<?>> getStaticPermissions(IEmfTable<?, ?, ?> table) {

		return tableToStaticPermissionsMap.getOrDefault(table, Collections.emptyList());
	}

	@Override
	public <R extends IEmfTableRow<R, ?>> Optional<IEmfStaticPermission<R>> getStaticPermission(IEmfTable<R, ?, ?> table, UUID permissionUuid) {

		return getStaticPermission(permissionUuid)//
			.filter(getStaticPermissions(table)::contains)
			.map(CastUtils::cast);
	}

	@Override
	public Collection<IEmfPermission<?>> getAtomicPermissions() {

		return atomicPermissions;
	}

	@Override
	public Collection<IEmfPermission<?>> getAtomicPermissions(IEmfTable<?, ?, ?> table) {

		return tableToAtomicPermissionsMap.getOrDefault(table, Collections.emptySet());
	}

	@Override
	public Collection<IEmfPermission<?>> getAtomicPermissions(IEmfModule<?> module) {

		Collection<IEmfPermission<?>> permissions = new HashList<>();
		for (IEmfTable<?, ?, ?> table: EmfTableRegistry.getInstance().getTables(module)) {
			permissions.addAll(tableToAtomicPermissionsMap.getOrDefault(table, Collections.emptySet()));
		}
		return permissions;
	}

	@Override
	public Collection<IEmfTable<?, ?, ?>> getAtomicPermissionTables(IEmfPermission<?> permission) {

		return atomicPermissionToTablesMap.getOrDefault(permission, Collections.emptySet());
	}

	@Override
	public Map<IEmfPermission<?>, Collection<IEmfPage<?>>> getPermissionToPagesMap(IEmfModule<?> module) {

		return moduleToPermissionsToPagesMap.getOrDefault(module, Collections.emptyMap());
	}

	// ------------------------------ module specific ------------------------------ //

	private void registerModule(IEmfModule<?> module) {

		modules.add(module);
	}

	// ------------------------------ page specific ------------------------------ //

	private void registerPagePermissions() {

		for (IEmfPage<?> page: EmfSourceCodeReferencePoints.getReferencePoints(IEmfPage.class)) {
			IEmfPermission<?> pagePermission = page.getRequiredPermission();
			IEmfModule<?> module = EmfPages.getModule(page);
			registerPermission(module, pagePermission);
			addToPermissionsToPagesMap(module, page, pagePermission);
		}
	}

	private void addToPermissionsToPagesMap(IEmfModule<?> module, IEmfPage<?> page, IEmfPermission<?> pagePermission) {

		moduleToPermissionsToPagesMap//
			.computeIfAbsent(module, dummy -> new HashMap<>())
			.computeIfAbsent(pagePermission, dummy -> new HashList<>())
			.add(page);
	}

	// ------------------------------ table specific ------------------------------ //

	private void registerPermissionsFromAuthorizer(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		IEmfAuthorizer<?, ?> authorizer = table.getAuthorizer();
		registerPermission(module, table, authorizer.getCreationPermission());
		registerPermission(module, table, authorizer.getDeletePermission());
		registerPermission(module, table, authorizer.getEditPermission());
		registerPermission(module, table, authorizer.getViewPermission());
	}

	private void registerPermissionsFromPrimaryActions(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfPrimaryAction<?> action: table.getPrimaryActions()) {
			registerPermission(module, table, action.getRequiredPermission());
		}
	}

	private void registerPermissionsFromCommonActions(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfCommonAction<?> action: table.getCommonActions()) {
			registerPermission(module, table, action.getRequiredPermission());
		}
	}

	private void registerPermissionsFromManagementActions(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfManagementAction<?> action: table.getManagementActions()) {
			registerPermission(module, table, action.getRequiredPermission());
		}
	}

	private void registerPermissionsFromTableClass(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfStaticPermission<?> permission: new EmfStaticPermissionFinder(table).findStaticPermissions()) {
			registerPermission(module, table, permission);
			registerStaticPermission(table, permission);
		}
	}

	private void registerPermission(IEmfModule<?> module, IEmfTable<?, ?, ?> table, IEmfPermission<?> permission) {

		if (!isTrivialPermission(permission)) {
			registerPermission(module, permission);

			addToTableToPermissionsMap(table, permission);
			addToTableToAtomicPermissionsMap(table, permission);
			addToAtomicPermissionToTablesMap(table, permission);
		}
	}

	private void registerStaticPermission(IEmfTable<?, ?, ?> table, IEmfStaticPermission<?> permission) {

		tableToStaticPermissionsMap.computeIfAbsent(table, dummy -> new HashList<>()).add(permission);
	}

	private void addToTableToPermissionsMap(IEmfTable<?, ?, ?> table, IEmfPermission<?> permission) {

		tableToPermissionsMap.computeIfAbsent(table, dummy -> new HashList<>()).add(permission);
	}

	private void addToTableToAtomicPermissionsMap(IEmfTable<?, ?, ?> table, IEmfPermission<?> permission) {

		for (IEmfPermission<?> atomicPermission: new EmfPermissionAtomConverter<>(permission).convert()) {
			tableToAtomicPermissionsMap.computeIfAbsent(table, dummy -> new HashList<>()).add(atomicPermission);
		}
	}

	private void addToAtomicPermissionToTablesMap(IEmfTable<?, ?, ?> table, IEmfPermission<?> permission) {

		for (IEmfPermission<?> atomicPermission: new EmfPermissionAtomConverter<>(permission).convert()) {
			atomicPermissionToTablesMap.computeIfAbsent(atomicPermission, dummy -> new HashList<>()).add(table);
		}
	}

	// ------------------------------ generic permission ------------------------------ //

	private void registerPermission(IEmfModule<?> module, IEmfPermission<?> permission) {

		if (!isTrivialPermission(permission)) {
			permissions.add(permission);
			new EmfPermissionAtomConverter<>(permission).convert().forEach(atomicPermission -> registerAtomicPermission(module, atomicPermission));
		}
	}

	private void registerAtomicPermission(IEmfModule<?> module, IEmfPermission<?> atomicPermission) {

		atomicPermissions.add(atomicPermission);

		if (atomicPermission instanceof IEmfStaticPermission) {
			var modulePermission = (IEmfStaticPermission<?>) atomicPermission;
			this.moduleToStaticPermissionsMap.computeIfAbsent(module, dummy -> new HashSet<>()).add(modulePermission);
			this.uuidToStaticPermissionMap.put(modulePermission.getAnnotatedUuid(), modulePermission);
		}
	}

	private boolean isTrivialPermission(IEmfPermission<?> permission) {

		return permission.equals(EmfPermissions.nobody()) || permission.equals(EmfPermissions.anybody());
	}
}
