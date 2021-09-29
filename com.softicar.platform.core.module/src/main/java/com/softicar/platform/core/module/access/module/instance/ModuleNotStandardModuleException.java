package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.emf.module.IEmfModule;

public class ModuleNotStandardModuleException extends SofticarDeveloperException {

	public ModuleNotStandardModuleException(IEmfModule<?> module) {

		super("Module '%s' is not a standard module.", module.getClass().getSimpleName());
	}
}