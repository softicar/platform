package com.softicar.platform.core.module.program.execution.status;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.ProgramExecutionRunnableRegistry;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

public class ProgramExecutionStatusField extends AbstractTransientObjectField<AGProgramExecution, ProgramExecutionStatus> {

	public ProgramExecutionStatusField() {

		super(ProgramExecutionStatus.class);
	}

	@Override
	protected void loadValues(Set<AGProgramExecution> programExecutions, IValueSetter<AGProgramExecution, ProgramExecutionStatus> setter) {

		for (AGProgramExecution execution: programExecutions) {
			setter.set(execution, determineStatus(execution));
		}
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.STATUS;
	}

	@Override
	protected ProgramExecutionStatus getDefaultValue() {

		return ProgramExecutionStatus.UNKNOWN;
	}

	private ProgramExecutionStatus determineStatus(AGProgramExecution execution) {

		if (execution.getStartedAt() != null) {
			// not yet terminated
			if (execution.getTerminatedAt() == null) {
				if (ProgramExecutionRunnableRegistry.getInstance().hasRunnable(execution)) {
					return ProgramExecutionStatus.RUNNING;
				} else {
					return ProgramExecutionStatus.ORPHANED;
				}
			}

			// terminated
			else {
				if (execution.isMaximumRuntimeExceeded()) {
					return ProgramExecutionStatus.RUNTIME_EXCEEDED;
				} else if (execution.isFailed()) {
					return ProgramExecutionStatus.FAILED;
				} else {
					return ProgramExecutionStatus.SUCCESSFUL;
				}
			}
		}

		return ProgramExecutionStatus.UNKNOWN;
	}
}
