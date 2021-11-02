package com.softicar.platform.core.module.access.role.assignment.module.instance;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.UUID;

public class AGModuleInstanceRoleAssignment extends AGModuleInstanceRoleAssignmentGenerated implements IEmfObject<AGModuleInstanceRoleAssignment> {

	public UUID getRoleUuid() {

		return getRole().getUuid();
	}

	public AGModuleInstanceRoleAssignment setRole(UUID uuid) {

		return setRole(AGUuid.getOrCreate(uuid));
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString()
			.append(//
				"[%s - %s - %s]",
				getModuleInstance().toDisplayWithoutId(),
				getRole().toDisplayWithoutId(),
				getUser().toDisplayWithoutId());
	}
}
