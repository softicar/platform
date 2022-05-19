package com.softicar.platform.demo.module.person.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.emf.page.EmfPagePath;

public class AGDemoPersonModuleInstance extends AGDemoPersonModuleInstanceGenerated implements IStandardModuleInstance<AGDemoPersonModuleInstance> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		String title = getTitle();
		if (title.isBlank()) {
			return IStandardModuleInstance.super.toDisplayWithoutId();
		} else {
			return IDisplayString.create(title);
		}
	}

	public EmfPagePath getDefaultPagePathOfDemoModuleInstance() {

		return getDemoCoreModuleInstance().getDefaultPagePath();
	}
}
