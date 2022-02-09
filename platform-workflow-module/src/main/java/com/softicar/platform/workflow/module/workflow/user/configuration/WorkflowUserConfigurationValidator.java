package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.List;

public class WorkflowUserConfigurationValidator extends AbstractEmfValidator<AGWorkflowUserConfiguration> {

	@Override
	protected void validate() {

		assertSourceAndTargetUserNotEqual();
		validateThisUserIsNotASubstitute();
		if (tableRow.getValidFrom() != null && tableRow.getValidTo() != null) {
			verifyValidFromBeforeValidTo();
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
			.where(AGWorkflowUserConfiguration.VALID_FROM.isLessEqual(tableRow.getValidTo()))
			.where(AGWorkflowUserConfiguration.VALID_TO.isGreaterEqual(tableRow.getValidFrom()))
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

		if (tableRow.getValidFrom().compareTo(tableRow.getValidTo()) > 0) {
			addError(AGWorkflowUserConfiguration.VALID_FROM, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
			addError(AGWorkflowUserConfiguration.VALID_TO, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
		}
	}
}
