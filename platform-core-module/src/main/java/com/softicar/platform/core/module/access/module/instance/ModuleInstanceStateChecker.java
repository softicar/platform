package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.core.module.module.IStandardModule;

public class ModuleInstanceStateChecker {

	private final AGModuleInstance moduleInstance;

	public ModuleInstanceStateChecker(AGModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;
	}

	public boolean isInitialized() {

		return moduleInstance.getModule().map(IStandardModule::cast).flatMap(module -> module.getModuleInstance(moduleInstance)).isPresent();
	}
}
