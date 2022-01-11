package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class ProgramValidator extends AbstractEmfValidator<AGProgram> {

	@Override
	protected void validate() {

		if (tableRow.getRetentionDaysOfExecutions() < 0) {
			addError(AGProgram.RETENTION_DAYS_OF_EXECUTIONS, CoreI18n.RETENTION_DAYS_OF_EXECUTIONS_MUST_NOT_BE_SMALLER_THAN_0);
		}
	}
}
