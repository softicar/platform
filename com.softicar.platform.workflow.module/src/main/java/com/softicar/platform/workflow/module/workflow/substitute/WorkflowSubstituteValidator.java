package com.softicar.platform.workflow.module.workflow.substitute;

import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.List;

public class WorkflowSubstituteValidator extends AbstractEmfValidator<AGWorkflowSubstitute> {

	@Override
	protected void validate() {

		assertSourceAndTargetUserNotEqual();
		validateThisUserIsNotASubstitute();
		verifyValidFromBeforeValidTo();
	}

	private void assertSourceAndTargetUserNotEqual() {

		if (tableRow.getUser().is(tableRow.getSubstitute())) {
			addError(AGWorkflowSubstitute.SUBSTITUTE, WorkflowI18n.YOU_CAN_NOT_SUBSTITUTE_YOURSELF);
		}
	}

	private void validateThisUserIsNotASubstitute() {

		List<AGWorkflowSubstitute> substitutes = AGWorkflowSubstitute.TABLE
			.createSelect()
			.where(AGWorkflowSubstitute.SUBSTITUTE.isEqual(tableRow.getUser()))
			.where(AGWorkflowSubstitute.ACTIVE.isTrue())
			.where(AGWorkflowSubstitute.VALID_FROM.isLessEqual(tableRow.getValidTo()))
			.where(AGWorkflowSubstitute.VALID_TO.isGreaterEqual(tableRow.getValidFrom()))
			.list();

		if (!substitutes.isEmpty()) {
			addError(AGWorkflowSubstitute.USER, WorkflowI18n.YOU_ARE_SUBSTITUTE_FOR_ARG1.toDisplay(getSubstitutes(substitutes)));
		}
	}

	private String getSubstitutes(List<AGWorkflowSubstitute> substitutes) {

		StringBuilder substitutesText = new StringBuilder();
		substitutes.forEach(it -> substitutesText.append(it.getUser().toDisplayWithoutId().toString()).append(", "));
		return substitutesText.toString();
	}

	private void verifyValidFromBeforeValidTo() {

		if (!tableRow.validFromBeforeEqualValidToOrNull()) {
			addError(AGWorkflowSubstitute.VALID_FROM, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
			addError(AGWorkflowSubstitute.VALID_TO, WorkflowI18n.VALID_FROM_AFTER_VALID_TO);
		}
	}
}
