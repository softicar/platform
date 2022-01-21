package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

public class ProgramCurrentExecutionField extends AbstractTransientObjectField<AGProgram, AGProgramExecution> {

	public ProgramCurrentExecutionField() {

		super(AGProgramExecution.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CURRENT_EXECUTION;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, AGProgramExecution> setter) {

		AGProgramState.TABLE//
			.createSelect()
			.where(AGProgramState.PROGRAM.isIn(programs))
			.forEach(record -> setter.set(record.getProgram(), record.getCurrentExecution()));

	}

	@Override
	protected AGProgramExecution getDefaultValue() {

		return AGProgramState.CURRENT_EXECUTION.getDefault();
	}
}
