package com.softicar.platform.core.module.program;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface ProgramPredicates {

	IEmfPredicate<AGProgram> IS_NOT_QUEUED = new EmfPredicate<>(//
		CoreI18n.IS_NOT_QUEUED,
		it -> !it.isQueued());

	IEmfPredicate<AGProgram> IS_NOT_RUNNING = new EmfPredicate<>(//
		CoreI18n.IS_NOT_RUNNING,
		dummy -> AGProgramExecution.getRecentExecutions(DayTime.now().truncateSeconds()).isEmpty());
}
