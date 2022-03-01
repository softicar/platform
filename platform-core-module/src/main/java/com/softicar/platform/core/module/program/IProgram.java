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
	 * This method can be overridden to return an optional cron expression.
	 * <p>
	 * If defined, the cron expression will be used to schedule this {@link IProgram}
	 * automatically by {@link ProgramStandardConfiguration}.
	 *
	 * @return An Optional with a cron expression, or an empty Optional.
	 */
	default Optional<String> getDefaultCronExpression() {

		return Optional.empty();
	}
}
