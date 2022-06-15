package com.softicar.platform.core.module.permission.assignment;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmfModulePermissionAssignmentCache {

	private final AGUser user;
	private final Map<AGModuleInstance, Set<UUID>> standardModulePermissions;

	public EmfModulePermissionAssignmentCache(AGUser user) {

		this.user = user;
		this.standardModulePermissions = new TreeMap<>();
	}

	public boolean hasModulePermission(UUID permissionUuid, AGModuleInstance moduleInstance) {

		return standardModulePermissions//
			.computeIfAbsent(moduleInstance, this::loadModulePermissionUuids)
			.contains(permissionUuid);
	}

	private Set<UUID> loadModulePermissionUuids(AGModuleInstance moduleInstance) {

		return AGModuleInstancePermissionAssignment.TABLE//
			.createSelect()
			.where(AGModuleInstancePermissionAssignment.MODULE_INSTANCE.isEqual(moduleInstance))
			.where(AGModuleInstancePermissionAssignment.USER.isEqual(user))
			.where(AGModuleInstancePermissionAssignment.ACTIVE.isTrue())
			.stream()
			.map(AGModuleInstancePermissionAssignment::getPermissionUuid)
			.collect(Collectors.toSet());
	}
}