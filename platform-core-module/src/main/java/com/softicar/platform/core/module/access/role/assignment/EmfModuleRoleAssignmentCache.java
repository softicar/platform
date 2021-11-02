package com.softicar.platform.core.module.access.role.assignment;

import com.softicar.platform.common.core.supplier.LazySupplier;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.EmfSystemModuleRole;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.access.role.assignment.module.system.AGSystemModuleRoleAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmfModuleRoleAssignmentCache {

	private final AGUser user;
	private final Map<AGModuleInstance, Set<UUID>> standardModuleRoles;
	private final LazySupplier<Set<UUID>> systemModuleRoles;

	public EmfModuleRoleAssignmentCache(AGUser user) {

		this.user = user;
		this.standardModuleRoles = new TreeMap<>();
		this.systemModuleRoles = new LazySupplier<>(this::loadSystemModuleRoleUuids);
	}

	public boolean hasModuleRole(UUID roleUuid, AGModuleInstance moduleInstance) {

		return standardModuleRoles//
			.computeIfAbsent(moduleInstance, this::loadStandardModuleRoleUuids)
			.contains(roleUuid);
	}

	public boolean hasModuleRole(EmfSystemModuleRole moduleRole) {

		return systemModuleRoles.get().contains(moduleRole.getAnnotatedUuid());
	}

	private Set<UUID> loadStandardModuleRoleUuids(AGModuleInstance moduleInstance) {

		return AGModuleInstanceRoleAssignment.TABLE//
			.createSelect()
			.where(AGModuleInstanceRoleAssignment.MODULE_INSTANCE.isEqual(moduleInstance))
			.where(AGModuleInstanceRoleAssignment.USER.isEqual(user))
			.where(AGModuleInstanceRoleAssignment.ACTIVE.isTrue())
			.stream()
			.map(AGModuleInstanceRoleAssignment::getRoleUuid)
			.collect(Collectors.toSet());
	}

	private Set<UUID> loadSystemModuleRoleUuids() {

		return AGSystemModuleRoleAssignment.TABLE//
			.createSelect()
			.where(AGSystemModuleRoleAssignment.USER.isEqual(user))
			.where(AGSystemModuleRoleAssignment.ACTIVE.isTrue())
			.stream()
			.map(AGSystemModuleRoleAssignment::getRoleUuid)
			.collect(Collectors.toSet());
	}
}
