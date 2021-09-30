package com.softicar.platform.core.module.access.role.assignment.module.system;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.UUID;

public class AGSystemModuleRoleAssignment extends AGSystemModuleRoleAssignmentGenerated implements IEmfObject<AGSystemModuleRoleAssignment> {

	public UUID getRoleUuid() {

		return getRole().getUuid();
	}

	public AGSystemModuleRoleAssignment setRole(UUID uuid) {

		return setRole(AGUuid.getOrCreate(uuid));
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString()
			.append(//
				"[%s - %s - %s]",
				getModule().toDisplayWithoutId(),
				getRole().toDisplayWithoutId(),
				getUser().toDisplayWithoutId());
	}
}
