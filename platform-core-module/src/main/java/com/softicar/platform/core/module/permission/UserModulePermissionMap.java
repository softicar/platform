package com.softicar.platform.core.module.permission;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.role.permission.AGRolePermission;
import com.softicar.platform.core.module.role.user.AGRoleUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

public class UserModulePermissionMap {

	private final AGUser user;
	private final Map<AGModuleInstance, Set<UUID>> permissions;

	public UserModulePermissionMap(AGUser user) {

		this.user = user;
		this.permissions = new TreeMap<>();

		loadDirectAssignments();
		loadRolePermissions();
	}

	public boolean hasPermission(AGModuleInstance moduleInstance, UUID permissionUuid) {

		return permissions//
			.getOrDefault(moduleInstance, Collections.emptySet())
			.contains(permissionUuid);
	}

	private void loadDirectAssignments() {

		AGModuleInstancePermissionAssignment.TABLE//
			.createSelect()
			.where(AGModuleInstancePermissionAssignment.USER.isEqual(user))
			.where(AGModuleInstancePermissionAssignment.ACTIVE.isTrue())
			.forEach(this::addPermission);
	}

	private void loadRolePermissions() {

		var roles = AGRole.TABLE//
			.createSelect()
			.where(AGRole.ACTIVE)
			.joinReverse(AGRoleUser.ROLE)
			.where(AGRoleUser.ACTIVE)
			.where(AGRoleUser.USER.isEqual(user))
			.list();

		AGRolePermission.TABLE//
			.createSelect()
			.where(AGRolePermission.ACTIVE)
			.where(AGRolePermission.ROLE.isIn(roles))
			.forEach(this::addPermission);
	}

	private void addPermission(AGModuleInstancePermissionAssignment assignment) {

		addPermission(assignment.getModuleInstance(), assignment.getPermission());
	}

	private void addPermission(AGRolePermission permission) {

		addPermission(permission.getModuleInstance(), permission.getPermissionUuid());
	}

	private void addPermission(AGModuleInstance moduleInstance, AGUuid permissionUuid) {

		permissions//
			.computeIfAbsent(moduleInstance, dummy -> new TreeSet<>())
			.add(permissionUuid.getUuid());
	}
}
