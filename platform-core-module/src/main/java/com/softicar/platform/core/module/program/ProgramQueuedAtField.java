package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.db.runtime.transients.AbstractTransientDayTimeField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

class ProgramQueuedAtField extends AbstractTransientDayTimeField<AGProgram> {

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.QUEUED_AT;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, DayTime> setter) {

		programs.forEach(program -> setter.set(program, program.getQueuedAt()));
	}

	@Override
	protected DayTime getDefaultValue() {

		return AGProgramState.QUEUED_AT.getDefault();
	}
}
