package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.db.runtime.transients.AbstractTransientBooleanField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

class ProgramAbortRequestedField extends AbstractTransientBooleanField<AGProgram> {

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.ABORT_REQUESTED;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, Boolean> setter) {

		programs.forEach(program -> setter.set(program, program.isAbortRequested()));
	}

	@Override
	protected Boolean getDefaultValue() {

		return AGProgramState.ABORT_REQUESTED.getDefault();
	}
}
