package com.softicar.platform.core.module.permission.assignment;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.UUID;

public class AGModuleInstancePermissionAssignment extends AGModuleInstancePermissionAssignmentGenerated
		implements IEmfObject<AGModuleInstancePermissionAssignment> {

	public UUID getPermissionUuid() {

		return getPermission().getUuid();
	}

	public AGModuleInstancePermissionAssignment setPermission(UUID uuid) {

		return setPermission(AGUuid.getOrCreate(uuid));
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString()
			.append(//
				"[%s - %s - %s]",
				getModuleInstance().toDisplayWithoutId(),
				getPermission().toDisplayWithoutId(),
				getUser().toDisplayWithoutId());
	}
}
