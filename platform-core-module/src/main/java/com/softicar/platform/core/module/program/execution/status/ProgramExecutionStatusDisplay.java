package com.softicar.platform.core.module.program.execution.status;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

public class ProgramExecutionStatusDisplay extends DomDiv {

	public ProgramExecutionStatusDisplay(ProgramExecutionStatus status) {

		addCssClass(CoreCssClasses.PROGRAM_EXECUTION_STATUS_DISPLAY);
		addCssClass(status.getPseudoClass());
		appendText(status.toDisplay());
	}
}
