package com.softicar.platform.core.module.program.execution.status;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface ProgramExecutionStatusCssPseudoClasses {

	ICssClass FAILED = new CssClass("programExecutionStatusFailed");
	ICssClass ORPHANED = new CssClass("programExecutionStatusOrphaned");
	ICssClass RUNNING = new CssClass("programExecutionStatusRunning");
	ICssClass RUNTIME_EXCEEDED = new CssClass("programExecutionStatusRuntimeExceeded");
	ICssClass SUCCESSFUL = new CssClass("programExecutionStatusSuccessful");
	ICssClass UNKNOWN = new CssClass("programExecutionStatusUnknown");
}
