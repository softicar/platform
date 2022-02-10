package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.List;

public class WorkflowUserSettingsValidator extends AbstractEmfValidator<AGWorkflowUserSettings> {

	@Override
	protected void validate() {

		assertSourceAndTargetUserNotEqual();
		validateThisUserIsNotASubstitute();
		if (tableRow.getSubstituteFrom() != null && tableRow.getSubstituteTo() != null) {
			verifyValidFromBeforeValidTo();
		}
		if (tableRow.getSubstitute() == null) {
			verifyEmptyValidFromAndValidTo();
		}
	}

	private void assertSourceAndTargetUserNotEqual() {

		if (tableRow.getUser().is(tableRow.getSubstitute())) {
			addError(AGWorkflowUserSettings.SUBSTITUTE, WorkflowI18n.YOU_CAN_NOT_SUBSTITUTE_YOURSELF);
		}
	}

	private void validateThisUserIsNotASubstitute() {

		List<AGWorkflowUserSettings> substitutes = AGWorkflowUserSettings.TABLE
			.createSelect()
			.where(AGWorkflowUserSettings.SUBSTITUTE.isEqual(tableRow.getUser()))
			.where(AGWorkflowUserSettings.SUBSTITUTE_FROM.isLessEqual(tableRow.getSubstituteTo()))
			.where(AGWorkflowUserSettings.SUBSTITUTE_TO.isGreaterEqual(tableRow.getSubstituteFrom()))
			.list();

		if (!substitutes.isEmpty()) {
			addError(AGWorkflowUserSettings.USER, WorkflowI18n.YOU_ARE_SUBSTITUTE_FOR_ARG1.toDisplay(getSubstitutes(substitutes)));
		}
	}

	private String getSubstitutes(List<AGWorkflowUserSettings> substitutes) {

		StringBuilder substitutesText = new StringBuilder();
		substitutes.forEach(it -> substitutesText.append(it.getUser().toDisplayWithoutId().toString()).append(", "));
		return substitutesText.toString();
	}

	private void verifyValidFromBeforeValidTo() {

		if (tableRow.getSubstituteFrom().compareTo(tableRow.getSubstituteTo()) > 0) {
			addError(AGWorkflowUserSettings.SUBSTITUTE_FROM, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
			addError(AGWorkflowUserSettings.SUBSTITUTE_TO, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
		}
	}

	private void verifyEmptyValidFromAndValidTo() {

		if (tableRow.getSubstituteFrom() != null) {
			addError(AGWorkflowUserSettings.SUBSTITUTE_FROM, WorkflowI18n.SUBSTITUTE_MUST_BE_DEFINED);
		}
		if (tableRow.getSubstituteTo() != null) {
			addError(AGWorkflowUserSettings.SUBSTITUTE_TO, WorkflowI18n.SUBSTITUTE_MUST_BE_DEFINED);
		}
	}
}
