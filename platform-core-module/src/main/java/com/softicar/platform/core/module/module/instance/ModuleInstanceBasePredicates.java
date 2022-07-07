package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface ModuleInstanceBasePredicates {

	IEmfPredicate<AGModuleInstanceBase> INITIALIZED = new EmfPredicate<>(//
		CoreI18n.INITIALIZED,
		AGModuleInstanceBase::isInitialized);

	IEmfPredicate<AGModuleInstanceBase> ACTIVE = new EmfPredicate<>(//
		CoreI18n.ACTIVE,
		AGModuleInstanceBase::isActive);

	IEmfPredicate<AGModuleInstanceBase> NOT_INITIALIZED = INITIALIZED.not();

	IEmfPredicate<AGModuleInstanceBase> NOT_ACTIVE = ACTIVE.not();
}
