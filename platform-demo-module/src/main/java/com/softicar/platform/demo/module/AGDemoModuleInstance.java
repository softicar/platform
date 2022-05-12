package com.softicar.platform.demo.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;

public class AGDemoModuleInstance extends AGDemoModuleInstanceGenerated implements IStandardModuleInstance<AGDemoModuleInstance> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		String title = getTitle();
		if (title.isBlank()) {
			return IStandardModuleInstance.super.toDisplayWithoutId();
		} else {
			return IDisplayString.create(title);
		}
	}

	public AGDemoBusinessUnitModuleInstance getDemoBusinessUnitModuleInstance() {

		return AGDemoBusinessUnitModuleInstance.TABLE//
			.createSelect()
			.where(AGDemoBusinessUnitModuleInstance.DEMO_MODULE_INSTANCE.equal(getThis()))
			.getOne();
	}
}
