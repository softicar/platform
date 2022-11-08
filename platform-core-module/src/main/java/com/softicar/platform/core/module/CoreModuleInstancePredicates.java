package com.softicar.platform.core.module;

import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface CoreModuleInstancePredicates {

	IEmfPredicate<AGCoreModuleInstance> TEST_SYSTEM = new EmfPredicate<>(//
		CoreI18n.TEST_SYSTEM,
		AGCoreModuleInstance::isTestSystem);
}
