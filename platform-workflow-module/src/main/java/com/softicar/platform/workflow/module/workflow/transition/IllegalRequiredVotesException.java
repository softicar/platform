package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class IllegalRequiredVotesException extends SofticarUserException {

	public IllegalRequiredVotesException(AGWorkflowTransition transition) {

		super(
			WorkflowI18n.REQUIRED_VOTES_NOT_VALID
				.concatSentence(WorkflowI18n.TRANSITION_FROM_WORKFLOW_NODE_ARG1_TO_ARG2.toDisplay(transition.getSourceNodeID(), transition.getTargetNodeID())));
	}
}
