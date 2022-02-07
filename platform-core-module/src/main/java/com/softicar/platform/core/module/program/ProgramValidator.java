package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class ProgramValidator extends AbstractEmfValidator<AGProgram> {

	@Override
	protected void validate() {

		Integer executionRetentionDays = tableRow.getExecutionRetentionDays();
		if (executionRetentionDays != null && executionRetentionDays < 0) {
			addError(AGProgram.EXECUTION_RETENTION_DAYS, CoreI18n.EXECUTION_RETENTION_DAYS_MUST_BE_AT_LEAST_0);
		}
	}
}
