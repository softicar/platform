package com.softicar.platform.core.module.access.permission.assignment.module.system;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.UUID;

public class AGSystemModulePermissionAssignment extends AGSystemModulePermissionAssignmentGenerated implements IEmfObject<AGSystemModulePermissionAssignment> {

	public UUID getPermissionUuid() {

		return getPermission().getUuid();
	}

	public AGSystemModulePermissionAssignment setPermission(UUID uuid) {

		return setPermission(AGUuid.getOrCreate(uuid));
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString()
			.append(//
				"[%s - %s - %s]",
				getModule().toDisplayWithoutId(),
				getPermission().toDisplayWithoutId(),
				getUser().toDisplayWithoutId());
	}
}
