package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.transients.AbstractTransientDayTimeField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

public class ProgramQueuedAtField extends AbstractTransientDayTimeField<AGProgram> {

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.QUEUED_AT;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, DayTime> setter) {

		AGProgramState.TABLE//
			.createSelect()
			.where(AGProgramState.PROGRAM.isIn(programs))
			.forEach(record -> setter.set(record.getProgram(), record.getQueuedAt()));
	}

	@Override
	protected DayTime getDefaultValue() {

		return AGProgramState.QUEUED_AT.getDefault();
	}
}
