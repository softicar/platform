package com.softicar.platform.core.module.role.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGRolePermission extends AGRolePermissionGenerated implements IEmfObject<AGRolePermission> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.format("%s - %s", getRole().toDisplayWithoutId(), getPermissionUuid().toDisplayWithoutId());
	}
}
