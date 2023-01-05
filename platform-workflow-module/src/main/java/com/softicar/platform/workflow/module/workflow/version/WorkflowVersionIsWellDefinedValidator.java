package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.validation.result.EmfDiagnosticCollection;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import java.util.Optional;
import java.util.function.Function;

/**
 * Checks that the {@link AGWorkflowVersion} is well-defined, that is, it has a
 * valid root node and all source code reference points can be found.
 *
 * @author Oliver Richers
 */
public class WorkflowVersionIsWellDefinedValidator {

	private final AGWorkflowVersion version;
	private EmfDiagnosticCollection diagnostics;

	public WorkflowVersionIsWellDefinedValidator(AGWorkflowVersion version) {

		this.version = version;
	}

	public EmfDiagnosticCollection validate() {

		this.diagnostics = new EmfDiagnosticCollection();
		validateRootNode();
		validateNodeActions();
		validateNodeActionPermissions();
		validateNodePreconditions();
		validateTransitions();
		validateTransitionPermissions();
		return diagnostics;
	}

	// ------------------------------ root node ------------------------------ //

	private void validateRootNode() {

		if (version.getRootNode() == null) {
			diagnostics.addError(WorkflowI18n.MISSING_WORKFLOW_ROOT_NODE);
		}
	}

	// ------------------------------ node actions ------------------------------ //

	private void validateNodeActions() {

		version.getAllActiveWorkflowNodeActions().forEach(this::validateNodeAction);
	}

	private void validateNodeAction(AGWorkflowNodeAction action) {

		validateReferencePoint(//
			action.getAction(),
			uuid -> WorkflowI18n.ACTION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING.toDisplay(uuid, action.getWorkflowNode().getName()));
	}

	// ------------------------------ node action permissions ------------------------------ //

	private void validateNodeActionPermissions() {

		version.getAllActiveWorkflowNodeActionPermissions().forEach(this::validateNodeActionPermission);
	}

	private void validateNodeActionPermission(AGWorkflowNodeActionPermission permission) {

		validatePermission(//
			permission.getPermissionUuid(),
			uuid -> WorkflowI18n.ACTION_PERMISSION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING
				.toDisplay(uuid, permission.getWorkflowNodeAction().getWorkflowNode().getName()));
	}

	// ------------------------------ node preconditions ------------------------------ //

	private void validateNodePreconditions() {

		version.getAllActiveWorkflowNodePreconditions().forEach(this::validateNodePrecondition);
	}

	private void validateNodePrecondition(AGWorkflowNodePrecondition precondition) {

		validateReferencePoint(//
			precondition.getFunction(),
			uuid -> WorkflowI18n.PRECONDITION_FUNCTION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING.toDisplay(uuid, precondition.getWorkflowNode().getName()));
	}

	// ------------------------------ transitions ------------------------------ //

	private void validateTransitions() {

		version.getAllActiveTransitions().forEach(this::validateTransition);
	}

	private void validateTransition(AGWorkflowTransition transition) {

		Optional//
			.ofNullable(transition.getSideEffect())
			.ifPresent(sideEffect -> {
				validateReferencePoint(//
					sideEffect,
					uuid -> WorkflowI18n.SIDE_EFFECT_ARG1_OF_WORKFLOW_TRANSITION_ARG2_IS_MISSING.toDisplay(uuid, transition.getName()));
			});
	}

	// ------------------------------ transition permissions ------------------------------ //

	private void validateTransitionPermissions() {

		version.getAllActiveTransitionPermissions().forEach(this::validateTransitionPermission);
	}

	private void validateTransitionPermission(AGWorkflowTransitionPermission permission) {

		validatePermission(//
			permission.getPermission(),
			uuid -> WorkflowI18n.PERMISSION_ARG1_OF_WORKFLOW_TRANSITION_ARG2_IS_MISSING.toDisplay(uuid, permission.getTransition().getName()));
	}

	// ------------------------------ utilities ------------------------------ //

	private void validatePermission(AGUuid uuid, Function<String, IDisplayString> errorMessageFactory) {

		if (!CurrentEmfPermissionRegistry.get().getStaticPermission(uuid.getUuid()).isPresent()) {
			diagnostics.addError(errorMessageFactory.apply(uuid.getUuidString()));
		}
	}

	private void validateReferencePoint(AGUuid uuid, Function<String, IDisplayString> errorMessageFactory) {

		if (!SourceCodeReferencePoints.getReferencePoint(uuid.getUuid()).isPresent()) {
			diagnostics.addError(errorMessageFactory.apply(uuid.getUuidString()));
		}
	}
}
