package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.authorization.role.finder.EmfStaticRoleFinder;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.authorizer.IEmfAuthorizer;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.page.EmfPages;
import com.softicar.platform.emf.page.IEmfPage;
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
 * A registry of existing {@link IEmfRole} instances.
 * <p>
 * Accessible via {@link CurrentEmfRoleRegistry}.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class EmfRoleRegistry implements IEmfRoleRegistry {

	private final Collection<IEmfRole<?>> roles;
	private final Collection<IEmfRole<?>> atomicRoles;
	private final Map<IEmfTable<?, ?, ?>, Collection<IEmfRole<?>>> tableToRolesMap;
	private final Map<IEmfTable<?, ?, ?>, Collection<IEmfRole<?>>> tableToAtomicRolesMap;
	private final Map<IEmfRole<?>, Collection<IEmfTable<?, ?, ?>>> atomicRoleToTablesMap;
	private final Collection<IEmfModule<?>> modules;
	private final Map<IEmfModule<?>, Collection<IEmfStaticRole<?>>> moduleToStaticRolesMap;
	private final Map<UUID, IEmfStaticRole<?>> uuidToStaticRoleMap;
	private final Map<IEmfTable<?, ?, ?>, Collection<IEmfStaticRole<?>>> tableToStaticRolesMap;
	private final Map<IEmfModule<?>, Map<IEmfRole<?>, Collection<IEmfPage<?>>>> moduleToRolesToPagesMap;

	EmfRoleRegistry() {

		this.roles = new HashList<>();
		this.atomicRoles = new HashList<>();
		this.tableToRolesMap = new HashMap<>();
		this.tableToAtomicRolesMap = new HashMap<>();
		this.atomicRoleToTablesMap = new HashMap<>();
		this.modules = new HashList<>();
		this.moduleToStaticRolesMap = new HashMap<>();
		this.uuidToStaticRoleMap = new TreeMap<>();
		this.tableToStaticRolesMap = new HashMap<>();
		this.moduleToRolesToPagesMap = new HashMap<>();

		for (IEmfModule<?> module: CurrentEmfModuleRegistry.get().getAllModules()) {
			registerModule(module);
			for (IEmfTable<?, ?, ?> table: EmfTableRegistry.getInstance().getTables(module)) {
				registerRolesFromAuthorizer(module, table);
				registerRolesFromPrimaryActions(module, table);
				registerRolesFromCommonActions(module, table);
				registerRolesFromManagementActions(module, table);
				registerRolesFromTableClass(module, table);
			}
		}
		registerPageRoles();
	}

	@Override
	public Collection<IEmfModule<?>> getModules() {

		return modules;
	}

	@Override
	public Collection<IEmfRole<?>> getRoles() {

		return roles;
	}

	@Override
	public Collection<IEmfRole<?>> getRoles(IEmfTable<?, ?, ?> table) {

		return tableToRolesMap.getOrDefault(table, Collections.emptySet());
	}

	@Override
	public Collection<IEmfRole<?>> getRoles(IEmfModule<?> module) {

		Collection<IEmfRole<?>> roles = new HashList<>();
		for (IEmfTable<?, ?, ?> table: EmfTableRegistry.getInstance().getTables(module)) {
			roles.addAll(tableToRolesMap.getOrDefault(table, Collections.emptySet()));
		}
		return roles;
	}

	@Override
	public Collection<IEmfStaticRole<?>> getStaticRoles(IEmfModule<?> module) {

		return moduleToStaticRolesMap.getOrDefault(module, Collections.emptySet());
	}

	@Override
	public Optional<IEmfStaticRole<?>> getStaticRole(UUID roleUuid) {

		return Optional.ofNullable(uuidToStaticRoleMap.get(roleUuid));
	}

	@Override
	public Collection<IEmfStaticRole<?>> getStaticRoles(IEmfTable<?, ?, ?> table) {

		return tableToStaticRolesMap.getOrDefault(table, Collections.emptyList());
	}

	@Override
	public <R extends IEmfTableRow<R, ?>> Optional<IEmfStaticRole<R>> getStaticRole(IEmfTable<R, ?, ?> table, UUID roleUuid) {

		return getStaticRole(roleUuid)//
			.filter(getStaticRoles(table)::contains)
			.map(CastUtils::cast);
	}

	@Override
	public Collection<IEmfRole<?>> getAtomicRoles() {

		return atomicRoles;
	}

	@Override
	public Collection<IEmfRole<?>> getAtomicRoles(IEmfTable<?, ?, ?> table) {

		return tableToAtomicRolesMap.getOrDefault(table, Collections.emptySet());
	}

	@Override
	public Collection<IEmfRole<?>> getAtomicRoles(IEmfModule<?> module) {

		Collection<IEmfRole<?>> roles = new HashList<>();
		for (IEmfTable<?, ?, ?> table: EmfTableRegistry.getInstance().getTables(module)) {
			roles.addAll(tableToAtomicRolesMap.getOrDefault(table, Collections.emptySet()));
		}
		return roles;
	}

	@Override
	public Collection<IEmfTable<?, ?, ?>> getAtomicRoleTables(IEmfRole<?> role) {

		return atomicRoleToTablesMap.getOrDefault(role, Collections.emptySet());
	}

	@Override
	public Map<IEmfRole<?>, Collection<IEmfPage<?>>> getRoleToPagesMap(IEmfModule<?> module) {

		return moduleToRolesToPagesMap.getOrDefault(module, Collections.emptyMap());
	}

	// ------------------------------ module specific ------------------------------ //

	private void registerModule(IEmfModule<?> module) {

		modules.add(module);
	}

	// ------------------------------ page specific ------------------------------ //

	private void registerPageRoles() {

		for (IEmfPage<?> page: EmfSourceCodeReferencePoints.getReferencePoints(IEmfPage.class)) {
			IEmfRole<?> pageRole = page.getAuthorizedRole();
			IEmfModule<?> module = EmfPages.getModule(page);
			registerRole(module, pageRole);
			addToRolesToPagesMap(module, page, pageRole);
		}
	}

	private void addToRolesToPagesMap(IEmfModule<?> module, IEmfPage<?> page, IEmfRole<?> pageRole) {

		moduleToRolesToPagesMap//
			.computeIfAbsent(module, dummy -> new HashMap<>())
			.computeIfAbsent(pageRole, dummy -> new HashList<>())
			.add(page);
	}

	// ------------------------------ table specific ------------------------------ //

	private void registerRolesFromAuthorizer(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		IEmfAuthorizer<?, ?> authorizer = table.getAuthorizer();
		registerRole(module, table, authorizer.getCreationRole());
		registerRole(module, table, authorizer.getDeleteRole());
		registerRole(module, table, authorizer.getEditRole());
		registerRole(module, table, authorizer.getViewRole());
	}

	private void registerRolesFromPrimaryActions(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfPrimaryAction<?> action: table.getPrimaryActions()) {
			registerRole(module, table, action.getAuthorizedRole());
		}
	}

	private void registerRolesFromCommonActions(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfCommonAction<?> action: table.getCommonActions()) {
			registerRole(module, table, action.getAuthorizedRole());
		}
	}

	private void registerRolesFromManagementActions(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfManagementAction<?> action: table.getManagementActions()) {
			registerRole(module, table, action.getAuthorizedRole());
		}
	}

	private void registerRolesFromTableClass(IEmfModule<?> module, IEmfTable<?, ?, ?> table) {

		for (IEmfStaticRole<?> role: new EmfStaticRoleFinder(table).findStaticRoles()) {
			registerRole(module, table, role);
			registerStaticRole(table, role);
		}
	}

	private void registerRole(IEmfModule<?> module, IEmfTable<?, ?, ?> table, IEmfRole<?> role) {

		if (!isTrivialRole(role)) {
			registerRole(module, role);

			addToTableToRolesMap(table, role);
			addToTableToAtomicRolesMap(table, role);
			addToAtomicRoleToTablesMap(table, role);
		}
	}

	private void registerStaticRole(IEmfTable<?, ?, ?> table, IEmfStaticRole<?> role) {

		tableToStaticRolesMap.computeIfAbsent(table, dummy -> new HashList<>()).add(role);
	}

	private void addToTableToRolesMap(IEmfTable<?, ?, ?> table, IEmfRole<?> role) {

		tableToRolesMap.computeIfAbsent(table, dummy -> new HashList<>()).add(role);
	}

	private void addToTableToAtomicRolesMap(IEmfTable<?, ?, ?> table, IEmfRole<?> role) {

		for (IEmfRole<?> atomicRole: new EmfRoleAtomConverter<>(role).convert()) {
			tableToAtomicRolesMap.computeIfAbsent(table, dummy -> new HashList<>()).add(atomicRole);
		}
	}

	private void addToAtomicRoleToTablesMap(IEmfTable<?, ?, ?> table, IEmfRole<?> role) {

		for (IEmfRole<?> atomicRole: new EmfRoleAtomConverter<>(role).convert()) {
			atomicRoleToTablesMap.computeIfAbsent(atomicRole, dummy -> new HashList<>()).add(table);
		}
	}

	// ------------------------------ generic role ------------------------------ //

	private void registerRole(IEmfModule<?> module, IEmfRole<?> role) {

		if (!isTrivialRole(role)) {
			roles.add(role);
			new EmfRoleAtomConverter<>(role).convert().forEach(atomicRole -> registerAtomicRole(module, atomicRole));
		}
	}

	private void registerAtomicRole(IEmfModule<?> module, IEmfRole<?> atomicRole) {

		atomicRoles.add(atomicRole);

		if (atomicRole instanceof IEmfStaticRole) {
			var moduleRole = (IEmfStaticRole<?>) atomicRole;
			this.moduleToStaticRolesMap.computeIfAbsent(module, dummy -> new HashSet<>()).add(moduleRole);
			this.uuidToStaticRoleMap.put(moduleRole.getAnnotatedUuid(), moduleRole);
		}
	}

	private boolean isTrivialRole(IEmfRole<?> role) {

		return role.equals(EmfRoles.nobody()) || role.equals(EmfRoles.anybody());
	}
}
