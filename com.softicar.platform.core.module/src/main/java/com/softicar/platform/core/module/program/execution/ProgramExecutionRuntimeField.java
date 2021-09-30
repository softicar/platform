package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.time.Duration;
import java.util.Set;

public class ProgramExecutionRuntimeField extends AbstractTransientObjectField<AGProgramExecution, Duration> {

	public ProgramExecutionRuntimeField() {

		super(Duration.class);
	}

	@Override
	protected void loadValues(Set<AGProgramExecution> programExecutions, IValueSetter<AGProgramExecution, Duration> setter) {

		for (AGProgramExecution programExecution: programExecutions) {
			setter.set(programExecution, programExecution.getDuration());
		}
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.RUNTIME;
	}

	@Override
	protected Duration getDefaultValue() {

		return Duration.ZERO;
	}
}
