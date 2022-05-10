package com.softicar.platform.demo.module.business.unit;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;

public class AGDemoBusinessUnitModuleInstance extends AGDemoBusinessUnitModuleInstanceGenerated implements IStandardModuleInstance<AGDemoBusinessUnitModuleInstance> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		String title = getTitle();
		if (title.isBlank()) {
			return IStandardModuleInstance.super.toDisplayWithoutId();
		} else {
			return IDisplayString.create(title);
		}
	}
}
