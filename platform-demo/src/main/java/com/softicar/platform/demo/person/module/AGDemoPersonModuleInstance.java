package com.softicar.platform.demo.person.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.emf.page.EmfPagePath;

public class AGDemoPersonModuleInstance extends AGDemoPersonModuleInstanceGenerated implements IModuleInstance<AGDemoPersonModuleInstance> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		String title = getTitle();
		if (title.isBlank()) {
			return IModuleInstance.super.toDisplayWithoutId();
		} else {
			return IDisplayString.create(title);
		}
	}

	public EmfPagePath getDefaultPagePathOfDemoCoreModule() {

		return getDemoCoreModuleInstance().getDefaultPagePath();
	}
}
