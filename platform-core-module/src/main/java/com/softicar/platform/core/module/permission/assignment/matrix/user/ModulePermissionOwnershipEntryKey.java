package com.softicar.platform.core.module.permission.assignment.matrix.user;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Comparator;
import java.util.UUID;

class ModulePermissionOwnershipEntryKey implements Comparable<ModulePermissionOwnershipEntryKey> {

	private final AGModuleInstanceBase moduleInstanceBase;
	private final AGUser user;
	private final UUID permissionUuid;

	public ModulePermissionOwnershipEntryKey(AGModuleInstancePermissionAssignment record) {

		this(record.getModuleInstanceBase(), record.getUser(), record.getPermissionUuid());
	}

	public ModulePermissionOwnershipEntryKey(AGModuleInstanceBase moduleInstanceBase, AGUser user, UUID permissionUuid) {

		this.moduleInstanceBase = moduleInstanceBase;
		this.user = user;
		this.permissionUuid = permissionUuid;
	}

	public AGModuleInstanceBase getModuleInstanceBase() {

		return moduleInstanceBase;
	}

	public AGUser getUser() {

		return user;
	}

	public UUID getPermissionUuid() {

		return permissionUuid;
	}

	public boolean isEqualKey(AGModuleInstancePermissionAssignment record) {

		return compareTo(new ModulePermissionOwnershipEntryKey(record)) == 0;
	}

	@Override
	public int compareTo(ModulePermissionOwnershipEntryKey other) {

		return Comparator//
			.comparing(ModulePermissionOwnershipEntryKey::getModuleInstanceBase, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModulePermissionOwnershipEntryKey::getUser, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModulePermissionOwnershipEntryKey::getPermissionUuid, Comparator.nullsFirst(Comparator.naturalOrder()))
			.compare(this, other);
	}
}
