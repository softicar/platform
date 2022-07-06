package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class ModuleInstanceBaseValidator extends AbstractEmfValidator<AGModuleInstanceBase> {

	@Override
	public void validate() {

		tableRow.getModule().ifPresent(this::assertImplementsModuleInterface);
	}

	private void assertImplementsModuleInterface(IEmfModule<?> module) {

		if (!IModule.class.isInstance(module)) {
			addError(//
				AGModuleInstanceBase.MODULE_UUID,
				CoreI18n.MODULE_CLASS_ARG1_MUST_IMPLEMENT_INTERFACE_ARG2.toDisplay(module.getClassName(), IModule.class.getSimpleName()));
		}
	}
}
