package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Comparator;
import java.util.UUID;

class ModuleRoleMembershipEntryKey implements Comparable<ModuleRoleMembershipEntryKey> {

	private final AGModuleInstance moduleInstance;
	private final AGUser user;
	private final UUID roleUuid;

	public ModuleRoleMembershipEntryKey(AGModuleInstanceRoleAssignment record) {

		this(record.getModuleInstance(), record.getUser(), record.getRoleUuid());
	}

	public ModuleRoleMembershipEntryKey(AGModuleInstance moduleInstance, AGUser user, UUID roleUuid) {

		this.moduleInstance = moduleInstance;
		this.user = user;
		this.roleUuid = roleUuid;
	}

	public AGModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	public AGUser getUser() {

		return user;
	}

	public UUID getRoleUuid() {

		return roleUuid;
	}

	public boolean isEqualKey(AGModuleInstanceRoleAssignment record) {

		return compareTo(new ModuleRoleMembershipEntryKey(record)) == 0;
	}

	@Override
	public int compareTo(ModuleRoleMembershipEntryKey other) {

		return Comparator//
			.comparing(ModuleRoleMembershipEntryKey::getModuleInstance, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModuleRoleMembershipEntryKey::getUser, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModuleRoleMembershipEntryKey::getRoleUuid, Comparator.nullsFirst(Comparator.naturalOrder()))
			.compare(this, other);
	}
}
