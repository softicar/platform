package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import com.softicar.platform.emf.table.IEmfTable;
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
			if (!requiredVotesStringIsValid(tableRow.getRequiredVotes())) {
				addError(AGWorkflowTransition.REQUIRED_VOTES, WorkflowI18n.REQUIRED_VOTES_NOT_VALID);
			}
		}

		if (tableRow.isAutoTransition()) {
			if (!tableRow.getRequiredVotes().equals("0")) {
				addError(AGWorkflowTransition.REQUIRED_VOTES, WorkflowI18n.AUTO_TRANSITIONS_MAY_NOT_DEFINE_REQUIRED_VOTES);
			}
			if (tableRow.isNotify()) {
				addError(AGWorkflowTransition.NOTIFY, WorkflowI18n.AUTO_TRANSITIONS_CANNOT_NOTIFY_ANYONE);
			}
			if (!tableRow.impermanent() && AGWorkflowTransitionPermission
				.createSelect()
				.where(AGWorkflowTransitionPermission.TRANSITION.equal(tableRow))
				.where(AGWorkflowTransitionPermission.ACTIVE)
				.exists()) {
				addError(AGWorkflowTransition.AUTO_TRANSITION, WorkflowI18n.AUTO_TRANSITIONS_MAY_NOT_DEFINE_PERMISSIONS);
			}
		}

		validateSideEffectAttribute();
	}

	private boolean requiredVotesStringIsValid(String requiredVotesString) {

		return IntegerParser.isInteger(requiredVotesString) || isValidPercentageString(requiredVotesString);
	}

	private boolean isValidPercentageString(String requiredVotesString) {

		if (requiredVotesString.endsWith("%")) {
			String percentageString = Trim.trimSuffix(requiredVotesString, "%");
			if (IntegerParser.isInteger(percentageString)) {
				int percentage = Integer.parseInt(percentageString);
				return percentage > 0 && percentage <= 100;
			}
		}

		return false;
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

		EmfSourceCodeReferencePoints//
			.getReferencePoint(sideEffectUuid.getUuid())
			.ifPresentOrElse(//
				this::assertValidSideEffect,
				() -> addError(AGWorkflowTransition.SIDE_EFFECT, WorkflowI18n.MISSING_SOURCE_CODE_REFERENCE_POINT));
	}

	private void assertValidSideEffect(IEmfSourceCodeReferencePoint sideEffect) {

		CastUtils//
			.tryCast(sideEffect, IWorkflowTransitionSideEffect.class)
			.ifPresentOrElse(//
				this::assertValidSideEffect,
				() -> addError(AGWorkflowTransition.SIDE_EFFECT, WorkflowI18n.SOURCE_CODE_REFERENCE_POINT_IS_NOT_A_VALID_SIDE_EFFECT));
	}

	private void assertValidSideEffect(IWorkflowTransitionSideEffect<?> sideEffect) {

		IEmfTable<?, ?, ?> table = tableRow.getWorkflowVersion().getWorkflow().getTableReferencePointOrThrow().getTable();
		if (!sideEffect.getValueClass().isAssignableFrom(table.getValueClass())) {
			addError(AGWorkflowTransition.SIDE_EFFECT, WorkflowI18n.SIDE_EFFECT_IS_NOT_COMPATIBLE_WITH_ARG1.toDisplay(table.getTitle()));
		}
	}
}
