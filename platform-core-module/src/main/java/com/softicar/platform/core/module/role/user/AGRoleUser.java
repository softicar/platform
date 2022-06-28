package com.softicar.platform.core.module.role.user;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGRoleUser extends AGRoleUserGenerated implements IEmfObject<AGRoleUser> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.format("%s: %s", getRole().toDisplayWithoutId(), getUser().toDisplayWithoutId());
	}
}
