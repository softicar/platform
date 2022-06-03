package com.softicar.platform.core.module.access.permission.assignment.module.instance.matrix.user;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.permission.assignment.module.instance.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Comparator;
import java.util.UUID;

class ModulePermissionOwnershipEntryKey implements Comparable<ModulePermissionOwnershipEntryKey> {

	private final AGModuleInstance moduleInstance;
	private final AGUser user;
	private final UUID permissionUuid;

	public ModulePermissionOwnershipEntryKey(AGModuleInstancePermissionAssignment record) {

		this(record.getModuleInstance(), record.getUser(), record.getPermissionUuid());
	}

	public ModulePermissionOwnershipEntryKey(AGModuleInstance moduleInstance, AGUser user, UUID permissionUuid) {

		this.moduleInstance = moduleInstance;
		this.user = user;
		this.permissionUuid = permissionUuid;
	}

	public AGModuleInstance getModuleInstance() {

		return moduleInstance;
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
			.comparing(ModulePermissionOwnershipEntryKey::getModuleInstance, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModulePermissionOwnershipEntryKey::getUser, Comparator.nullsFirst(Comparator.naturalOrder()))
			.thenComparing(ModulePermissionOwnershipEntryKey::getPermissionUuid, Comparator.nullsFirst(Comparator.naturalOrder()))
			.compare(this, other);
	}
}
