package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.emf.module.IEmfModule;

public class ModuleClassDoesNotImplementModuleInterfaceException extends SofticarUserException {

	public ModuleClassDoesNotImplementModuleInterfaceException(IEmfModule<?> module) {

		super(CoreI18n.MODULE_CLASS_ARG1_MUST_IMPLEMENT_INTERFACE_ARG2.toDisplay(module.getClass().getSimpleName(), IModule.class.getSimpleName()));
	}
}
