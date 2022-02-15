package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

class ProgramQueuedByField extends AbstractTransientObjectField<AGProgram, AGUser> {

	public ProgramQueuedByField() {

		super(AGUser.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.QUEUED_BY;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, AGUser> setter) {

		AGProgramState.TABLE//
			.createSelect()
			.where(AGProgramState.PROGRAM.isIn(programs))
			.forEach(record -> setter.set(record.getProgram(), record.getQueuedBy()));

	}

	@Override
	protected AGUser getDefaultValue() {

		return AGProgramState.QUEUED_BY.getDefault();
	}
}
