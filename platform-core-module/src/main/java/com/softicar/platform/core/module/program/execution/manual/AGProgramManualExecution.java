package com.softicar.platform.core.module.program.execution.manual;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.emf.object.IEmfObject;

public class AGProgramManualExecution extends AGProgramManualExecutionGenerated implements IEmfObject<AGProgramManualExecution> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString
			.format(//
				"%s: %s - %s",
				Programs.getProgramName(getProgram().getProgramUuid().getUuid()),
				getUser().toDisplayWithoutId(),
				getTime());
	}
}
