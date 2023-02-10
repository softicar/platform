package com.softicar.platform.core.module.program.execution.status;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.style.ICssClass;

public enum ProgramExecutionStatus implements IDisplayable {

	FAILED(CoreI18n.FAILED, ProgramExecutionStatusCssPseudoClasses.FAILED),
	ORPHANED(CoreI18n.ORPHANED, ProgramExecutionStatusCssPseudoClasses.ORPHANED),
	RUNNING(CoreI18n.RUNNING, ProgramExecutionStatusCssPseudoClasses.RUNNING),
	RUNTIME_EXCEEDED(CoreI18n.RUNTIME_EXCEEDED, ProgramExecutionStatusCssPseudoClasses.RUNTIME_EXCEEDED),
	SUCCESSFUL(CoreI18n.SUCCESSFUL, ProgramExecutionStatusCssPseudoClasses.SUCCESSFUL),
	UNKNOWN(CoreI18n.UNKNOWN, ProgramExecutionStatusCssPseudoClasses.UNKNOWN)
	//
	;

	private final IDisplayString label;
	private final ICssClass pseudoClass;

	private ProgramExecutionStatus(IDisplayString label, ICssClass pseudoClass) {

		this.label = label;
		this.pseudoClass = pseudoClass;
	}

	@Override
	public IDisplayString toDisplay() {

		return label;
	}

	public ICssClass getPseudoClass() {

		return pseudoClass;
	}
}
