package com.softicar.platform.workflow.module.standard.configuration;

import com.softicar.platform.core.module.configuration.AbstractStandardConfiguration;
import com.softicar.platform.core.module.program.execution.scheduled.AGScheduledProgramExecution;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.workflow.module.workflow.transition.program.WorkflowAutoTransitionExecutionProgram;
import java.util.UUID;

public class WorkflowProgramStandardConfiguration extends AbstractStandardConfiguration {

	@Override
	public void createAndSaveAll() {

		UUID programUuid = EmfSourceCodeReferencePoints.getUuidOrThrow(WorkflowAutoTransitionExecutionProgram.class);

		new AGScheduledProgramExecution()//
			.setActive(true)
			.setCronExpression("* * * * *")
			.setProgramUuid(programUuid)
			.save();
	}
}
