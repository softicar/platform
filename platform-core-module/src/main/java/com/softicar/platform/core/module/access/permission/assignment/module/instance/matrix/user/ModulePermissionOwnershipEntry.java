package com.softicar.platform.core.module.access.permission.assignment.module.instance.matrix.user;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.permission.assignment.module.instance.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Comparator;
import java.util.UUID;

class ModulePermissionOwnershipEntry implements Comparable<ModulePermissionOwnershipEntry> {

	private final ModulePermissionOwnershipEntryKey key;
	private final boolean active;

	public ModulePermissionOwnershipEntry(AGModuleInstance moduleInstance, AGUser user, UUID permissionUuid, boolean active) {

		this.key = new ModulePermissionOwnershipEntryKey(moduleInstance, user, permissionUuid);
		this.active = active;
	}

	public AGModuleInstance getModuleInstance() {

		return key.getModuleInstance();
	}

	public AGUser getUser() {

		return key.getUser();
	}

	public UUID getPermissionUuid() {

		return key.getPermissionUuid();
	}

	public ModulePermissionOwnershipEntryKey getKey() {

		return key;
	}

	public boolean isActive() {

		return active;
	}

	public AGModuleInstancePermissionAssignment applyValuesTo(AGModuleInstancePermissionAssignment record) {

		record.setModuleInstance(key.getModuleInstance());
		record.setUser(key.getUser());
		record.setPermission(key.getPermissionUuid());
		record.setActive(active);
		return record;
	}

	@Override
	public int compareTo(ModulePermissionOwnershipEntry other) {

		return Comparator//
			.comparing(ModulePermissionOwnershipEntry::getKey, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModulePermissionOwnershipEntry::isActive, Comparator.nullsFirst(Comparator.naturalOrder()))
			.compare(this, other);
	}
}
