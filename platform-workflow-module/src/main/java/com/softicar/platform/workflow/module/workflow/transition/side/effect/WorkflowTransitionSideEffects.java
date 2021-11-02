package com.softicar.platform.workflow.module.workflow.transition.side.effect;

import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoint;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoints;
import java.util.Collection;

public interface WorkflowTransitionSideEffects {

	public static Collection<AGUuidBasedSourceCodeReferencePoint> getAllSideEffectsAsIndirectEntities() {

		return AGUuidBasedSourceCodeReferencePoints.getAll(IWorkflowTransitionSideEffect.class);
	}
}
