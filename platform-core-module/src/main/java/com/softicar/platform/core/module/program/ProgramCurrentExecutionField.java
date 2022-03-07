package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

class ProgramCurrentExecutionField extends AbstractTransientObjectField<AGProgram, AGProgramExecution> {

	public ProgramCurrentExecutionField() {

		super(AGProgramExecution.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CURRENT_EXECUTION;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, AGProgramExecution> setter) {

		programs.forEach(program -> setter.set(program, program.getCurrentExecution()));
	}

	@Override
	protected AGProgramExecution getDefaultValue() {

		return AGProgramState.CURRENT_EXECUTION.getDefault();
	}
}
