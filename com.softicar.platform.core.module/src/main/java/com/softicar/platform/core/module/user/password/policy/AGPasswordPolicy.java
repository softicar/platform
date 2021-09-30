package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGPasswordPolicy extends AGPasswordPolicyGenerated implements IEmfObject<AGPasswordPolicy> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getThis().getName());
	}
}
