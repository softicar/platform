package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface ScheduledProgramExecutionPredicates {

	IEmfPredicate<AGScheduledProgramExecution> IS_NOT_QUEUED = new EmfPredicate<>(//
		IDisplayString.create("Test"),
		it -> !AGProgram.loadOrInsert(it.getProgramUuid()).isQueued());

	IEmfPredicate<AGScheduledProgramExecution> IS_NOT_RUNNING = new EmfPredicate<>(//
		IDisplayString.create("Test"),
		dummy -> AGProgramExecution.getRecentExecutions(DayTime.now().truncateSeconds()).isEmpty());
}
