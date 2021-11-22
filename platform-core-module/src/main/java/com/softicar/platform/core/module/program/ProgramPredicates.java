package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface ProgramPredicates {

	IEmfPredicate<AGProgram> NOT_QUEUED = new EmfPredicate<>(//
		CoreI18n.NOT_QUEUED,
		it -> !it.isQueued());
}
