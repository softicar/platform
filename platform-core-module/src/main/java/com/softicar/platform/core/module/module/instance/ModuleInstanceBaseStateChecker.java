package com.softicar.platform.core.module.module.instance;

public class ModuleInstanceBaseStateChecker {

	private final AGModuleInstanceBase moduleInstanceBase;

	public ModuleInstanceBaseStateChecker(AGModuleInstanceBase moduleInstanceBase) {

		this.moduleInstanceBase = moduleInstanceBase;
	}

	public boolean isInitialized() {

		return moduleInstanceBase//
			.getModule()
			.flatMap(module -> module.getModuleInstance(moduleInstanceBase.getId()))
			.isPresent();
	}
}
