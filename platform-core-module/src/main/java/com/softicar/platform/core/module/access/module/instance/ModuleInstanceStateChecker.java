package com.softicar.platform.core.module.access.module.instance;

public class ModuleInstanceStateChecker {

	private final AGModuleInstance moduleInstance;

	public ModuleInstanceStateChecker(AGModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;
	}

	public boolean isInitialized() {

		return moduleInstance//
			.getModule()
			.flatMap(module -> module.getModuleInstance(moduleInstance.getId()))
			.isPresent();
	}
}
