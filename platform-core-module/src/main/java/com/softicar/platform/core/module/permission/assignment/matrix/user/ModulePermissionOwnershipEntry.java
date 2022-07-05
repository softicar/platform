package com.softicar.platform.core.module.permission.assignment.matrix.user;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Comparator;
import java.util.UUID;

class ModulePermissionOwnershipEntry implements Comparable<ModulePermissionOwnershipEntry> {

	private final ModulePermissionOwnershipEntryKey key;
	private final boolean active;

	public ModulePermissionOwnershipEntry(AGModuleInstanceBase moduleInstanceBase, AGUser user, UUID permissionUuid, boolean active) {

		this.key = new ModulePermissionOwnershipEntryKey(moduleInstanceBase, user, permissionUuid);
		this.active = active;
	}

	public AGModuleInstanceBase getModuleInstanceBase() {

		return key.getModuleInstanceBase();
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

		record.setModuleInstanceBase(key.getModuleInstanceBase());
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
