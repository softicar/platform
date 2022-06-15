package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.emf.module.IEmfModule;

public class ModuleClassNotImplementingModuleInterfaceException extends SofticarDeveloperException {

	public ModuleClassNotImplementingModuleInterfaceException(IEmfModule<?> module) {

		super("Module class '%s' does not implement interface '%s'.", module.getClass().getSimpleName(), IModule.class.getSimpleName());
	}
}
