package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Comparator;
import java.util.UUID;

class ModuleRoleMembershipEntry implements Comparable<ModuleRoleMembershipEntry> {

	private final ModuleRoleMembershipEntryKey key;
	private final boolean active;

	public ModuleRoleMembershipEntry(AGModuleInstance moduleInstance, AGUser user, UUID roleUuid, boolean active) {

		this.key = new ModuleRoleMembershipEntryKey(moduleInstance, user, roleUuid);
		this.active = active;
	}

	public AGModuleInstance getModuleInstance() {

		return key.getModuleInstance();
	}

	public AGUser getUser() {

		return key.getUser();
	}

	public UUID getRoleUuid() {

		return key.getRoleUuid();
	}

	public ModuleRoleMembershipEntryKey getKey() {

		return key;
	}

	public boolean isActive() {

		return active;
	}

	public AGModuleInstanceRoleAssignment applyValuesTo(AGModuleInstanceRoleAssignment record) {

		record.setModuleInstance(key.getModuleInstance());
		record.setUser(key.getUser());
		record.setRole(key.getRoleUuid());
		record.setActive(active);
		return record;
	}

	@Override
	public int compareTo(ModuleRoleMembershipEntry other) {

		return Comparator//
			.comparing(ModuleRoleMembershipEntry::getKey, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModuleRoleMembershipEntry::isActive, Comparator.nullsFirst(Comparator.naturalOrder()))
			.compare(this, other);
	}
}
