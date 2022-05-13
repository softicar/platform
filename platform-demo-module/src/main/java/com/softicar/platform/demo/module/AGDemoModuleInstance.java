package com.softicar.platform.demo.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.demo.module.person.AGDemoPersonModuleInstance;

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

	public AGDemoPersonModuleInstance getDemoPersonModuleInstance() {

		return AGDemoPersonModuleInstance.TABLE//
			.createSelect()
			.where(AGDemoPersonModuleInstance.DEMO_MODULE_INSTANCE.equal(getThis()))
			.getOne();
	}
}
