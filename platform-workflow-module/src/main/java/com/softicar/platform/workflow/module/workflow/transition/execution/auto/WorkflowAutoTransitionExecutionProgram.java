package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.Optional;

/**
 * Executes all possible auto-transitions on all workflow items.
 */
@SourceCodeReferencePointUuid("6e79dc47-e910-4726-b789-a570d2ce4b61")
public class WorkflowAutoTransitionExecutionProgram implements IProgram {

	@Override
	public void executeProgram() {

		new WorkflowAutoTransitionsExecutor().executeTransitions();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("* * * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(WorkflowI18n.EXECUTES_WORKFLOW_TRANSITIONS);
	}
}
