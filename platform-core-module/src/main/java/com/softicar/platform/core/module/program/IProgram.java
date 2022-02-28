package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.standard.configuration.ProgramStandardConfiguration;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import java.util.Optional;

public interface IProgram extends IEmfSourceCodeReferencePoint {

	@Override
	default IDisplayString toDisplay() {

		return IDisplayString.create(getClass().getSimpleName());
	}

	void executeProgram();

	/**
	 * This method can be overwritten in that way that it returns an Optional
	 * with a cron expression. In this case the program will be scheduled
	 * automatically with it (see {@link ProgramStandardConfiguration}).
	 *
	 * @return An Optional with a cron expression, or an empty Optional.
	 */
	default Optional<String> getDefaultCronExpression() {

		return Optional.empty();
	}
}
