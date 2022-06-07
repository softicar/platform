package com.softicar.platform.core.module.access.permission.assignment;

import com.softicar.platform.common.core.supplier.LazySupplier;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.permission.EmfSystemModulePermission;
import com.softicar.platform.core.module.access.permission.assignment.module.instance.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.access.permission.assignment.module.system.AGSystemModulePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmfModulePermissionAssignmentCache {

	private final AGUser user;
	private final Map<AGModuleInstance, Set<UUID>> standardModulePermissions;
	private final LazySupplier<Set<UUID>> systemModulePermissions;

	public EmfModulePermissionAssignmentCache(AGUser user) {

		this.user = user;
		this.standardModulePermissions = new TreeMap<>();
		this.systemModulePermissions = new LazySupplier<>(this::loadSystemModulePermissionUuids);
	}

	public boolean hasModulePermission(UUID permissionUuid, AGModuleInstance moduleInstance) {

		return standardModulePermissions//
			.computeIfAbsent(moduleInstance, this::loadStandardModulePermissionUuids)
			.contains(permissionUuid);
	}

	public boolean hasModulePermission(EmfSystemModulePermission modulePermission) {

		return systemModulePermissions.get().contains(modulePermission.getAnnotatedUuid());
	}

	private Set<UUID> loadStandardModulePermissionUuids(AGModuleInstance moduleInstance) {

		return AGModuleInstancePermissionAssignment.TABLE//
			.createSelect()
			.where(AGModuleInstancePermissionAssignment.MODULE_INSTANCE.isEqual(moduleInstance))
			.where(AGModuleInstancePermissionAssignment.USER.isEqual(user))
			.where(AGModuleInstancePermissionAssignment.ACTIVE.isTrue())
			.stream()
			.map(AGModuleInstancePermissionAssignment::getPermissionUuid)
			.collect(Collectors.toSet());
	}

	private Set<UUID> loadSystemModulePermissionUuids() {

		return AGSystemModulePermissionAssignment.TABLE//
			.createSelect()
			.where(AGSystemModulePermissionAssignment.USER.isEqual(user))
			.where(AGSystemModulePermissionAssignment.ACTIVE.isTrue())
			.stream()
			.map(AGSystemModulePermissionAssignment::getPermissionUuid)
			.collect(Collectors.toSet());
	}
}
