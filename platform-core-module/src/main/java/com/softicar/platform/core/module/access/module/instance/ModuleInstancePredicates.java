package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface ModuleInstancePredicates {

	IEmfPredicate<AGModuleInstance> INITIALIZED = new EmfPredicate<>(//
		CoreI18n.INITIALIZED,
		AGModuleInstance::isInitialized);

	IEmfPredicate<AGModuleInstance> NOT_INITIALIZED = INITIALIZED.not();
}
