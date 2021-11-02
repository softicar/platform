package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.IStandardModule;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class ModuleInstanceValidator extends AbstractEmfValidator<AGModuleInstance> {

	@Override
	public void validate() {

		tableRow.getModule().ifPresent(this::assertStandardModule);
	}

	private void assertStandardModule(IEmfModule<?> module) {

		if (!IStandardModule.class.isInstance(module)) {
			addError(//
				AGModuleInstance.MODULE_UUID,
				CoreI18n.MODULE_ARG1_IS_NOT_A_STANDARD_MODULE.toDisplay(module.getClassName()));
		}
	}
}
