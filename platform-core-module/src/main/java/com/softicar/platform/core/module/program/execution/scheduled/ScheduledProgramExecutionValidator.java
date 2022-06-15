package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.core.module.cron.CronExpression;
import com.softicar.platform.core.module.cron.CronParser;
import com.softicar.platform.emf.validation.AbstractEmfValidator;
import java.util.Optional;

class ScheduledProgramExecutionValidator extends AbstractEmfValidator<AGScheduledProgramExecution> {

	@Override
	protected void validate() {

		Optional//
			.ofNullable(tableRow.getCronExpression())
			.ifPresent(this::validateCronString);
		validateAutoKill();
	}

	private void validateCronString(String cronString) {

		CronExpression cronExpression = CronParser.parse(cronString);
		for (CronExpression.Part part: CronExpression.Part.values()) {
			cronExpression//
				.getErrorMessage(part)
				.ifPresent(errorMessage -> addError(AGScheduledProgramExecution.CRON_EXPRESSION, errorMessage));
		}
	}

	private void validateAutoKill() {

		if (tableRow.isAutoKill()) {
			assertNotNull(AGScheduledProgramExecution.MAXIMUM_RUNTIME);
		}
	}
}
