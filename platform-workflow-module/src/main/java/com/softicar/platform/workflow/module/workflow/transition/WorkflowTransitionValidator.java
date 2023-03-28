package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.emf.validation.EmfValidationException;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import com.softicar.platform.workflow.module.workflow.transition.side.effect.IWorkflowTransitionSideEffect;
import java.util.Optional;

public class WorkflowTransitionValidator extends AbstractEmfValidator<AGWorkflowTransition> {

	@Override
	protected void validate() {

		// FIXME this is completely awkward; this test tries to prevent modifications to a non-draft workflow through validation?!
		if (tableRow.impermanent() || tableRow.dataChanged()) {
			if (!tableRow.getWorkflowVersion().isDraft()) {
				addError(AGWorkflowTransition.WORKFLOW_VERSION, WorkflowI18n.WORKFLOW_VERSION_FINALIZED);
			}
		}

		if (!tableRow.isAutoTransition() && tableRow.getRequiredVotes() != null) {
			if (!new WorkflowTransitionRequiredVotesParser(tableRow).isValid()) {
				addError(AGWorkflowTransition.REQUIRED_VOTES, WorkflowI18n.REQUIRED_VOTES_NOT_VALID);
			}
		}

		if (tableRow.isAutoTransition()) {
			if (!new WorkflowTransitionRequiredVotesParser(tableRow).isZero()) {
				addError(AGWorkflowTransition.REQUIRED_VOTES, WorkflowI18n.AUTO_TRANSITIONS_MAY_NOT_DEFINE_REQUIRED_VOTES);
			}
			if (tableRow.isNotify()) {
				addError(AGWorkflowTransition.NOTIFY, WorkflowI18n.AUTO_TRANSITIONS_CANNOT_NOTIFY_ANYONE);
			}
			if (!tableRow.impermanent() && AGWorkflowTransitionPermission
				.createSelect()
				.where(AGWorkflowTransitionPermission.TRANSITION.isEqual(tableRow))
				.where(AGWorkflowTransitionPermission.ACTIVE)
				.exists()) {
				addError(AGWorkflowTransition.AUTO_TRANSITION, WorkflowI18n.AUTO_TRANSITIONS_MAY_NOT_DEFINE_PERMISSIONS);
			}
		}

		validateSideEffectAttribute();
	}

	// ------------------------------ side-effect ------------------------------ //

	private void validateSideEffectAttribute() {

		try {
			Optional//
				.ofNullable(tableRow.getSideEffect())
				.ifPresent(this::assertValidSideEffect);
		} catch (EmfValidationException exception) {
			result.addAll(exception.getValidationResult());
		}
	}

	private void assertValidSideEffect(AGUuid sideEffectUuid) {

		SourceCodeReferencePoints//
			.getReferencePoint(sideEffectUuid.getUuid())
			.ifPresentOrElse(//
				this::assertValidSideEffect,
				() -> addError(AGWorkflowTransition.SIDE_EFFECT, WorkflowI18n.MISSING_SOURCE_CODE_REFERENCE_POINT));
	}

	private void assertValidSideEffect(ISourceCodeReferencePoint sideEffect) {

		CastUtils//
			.tryCast(sideEffect, IWorkflowTransitionSideEffect.class)
			.ifPresentOrElse(//
				this::assertValidSideEffect,
				() -> addError(AGWorkflowTransition.SIDE_EFFECT, WorkflowI18n.SOURCE_CODE_REFERENCE_POINT_IS_NOT_A_VALID_SIDE_EFFECT));
	}

	private void assertValidSideEffect(IWorkflowTransitionSideEffect<?> sideEffect) {

		tableRow//
			.getWorkflowVersion()
			.getWorkflow()
			.getTableReferencePoint()
			.ifPresent(referencePoint -> {
				var table = referencePoint.getTable();
				if (!sideEffect.isCompatible(table)) {
					addError(AGWorkflowTransition.SIDE_EFFECT, WorkflowI18n.SIDE_EFFECT_IS_NOT_COMPATIBLE_WITH_ARG1.toDisplay(table.getTitle()));
				}
			});
	}
}
