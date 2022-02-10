package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.List;

public class WorkflowUserConfigurationValidator extends AbstractEmfValidator<AGWorkflowUserConfiguration> {

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
			addError(AGWorkflowUserConfiguration.SUBSTITUTE, WorkflowI18n.YOU_CAN_NOT_SUBSTITUTE_YOURSELF);
		}
	}

	private void validateThisUserIsNotASubstitute() {

		List<AGWorkflowUserConfiguration> substitutes = AGWorkflowUserConfiguration.TABLE
			.createSelect()
			.where(AGWorkflowUserConfiguration.SUBSTITUTE.isEqual(tableRow.getUser()))
			.where(AGWorkflowUserConfiguration.SUBSTITUTE_FROM.isLessEqual(tableRow.getSubstituteTo()))
			.where(AGWorkflowUserConfiguration.SUBSTITUTE_TO.isGreaterEqual(tableRow.getSubstituteFrom()))
			.list();

		if (!substitutes.isEmpty()) {
			addError(AGWorkflowUserConfiguration.USER, WorkflowI18n.YOU_ARE_SUBSTITUTE_FOR_ARG1.toDisplay(getSubstitutes(substitutes)));
		}
	}

	private String getSubstitutes(List<AGWorkflowUserConfiguration> substitutes) {

		StringBuilder substitutesText = new StringBuilder();
		substitutes.forEach(it -> substitutesText.append(it.getUser().toDisplayWithoutId().toString()).append(", "));
		return substitutesText.toString();
	}

	private void verifyValidFromBeforeValidTo() {

		if (tableRow.getSubstituteFrom().compareTo(tableRow.getSubstituteTo()) > 0) {
			addError(AGWorkflowUserConfiguration.SUBSTITUTE_FROM, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
			addError(AGWorkflowUserConfiguration.SUBSTITUTE_TO, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
		}
	}

	private void verifyEmptyValidFromAndValidTo() {

		if (tableRow.getSubstituteFrom() != null) {
			addError(AGWorkflowUserConfiguration.SUBSTITUTE_FROM, WorkflowI18n.SUBSTITUTE_MUST_BE_DEFINED);
		}
		if (tableRow.getSubstituteTo() != null) {
			addError(AGWorkflowUserConfiguration.SUBSTITUTE_TO, WorkflowI18n.SUBSTITUTE_MUST_BE_DEFINED);
		}
	}
}
