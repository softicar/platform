package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.standard.configuration.ProgramStandardConfiguration;
import java.util.Optional;

public interface IProgram extends ISourceCodeReferencePoint {

	@Override
	default IDisplayString toDisplay() {

		return IDisplayString.create(getClass().getSimpleName());
	}

	void executeProgram();

	/**
	 * This method can be overridden to return an optional cron expression.
	 * <p>
	 * If defined, the cron expression will be used to schedule this
	 * {@link IProgram} automatically by {@link ProgramStandardConfiguration}.
	 *
	 * @return an optional cron expression
	 */
	default Optional<String> getDefaultCronExpression() {

		return Optional.empty();
	}

	/**
	 * This method can be overridden to return an optional description of this
	 * {@link IProgram}.
	 * <p>
	 * If defined, the description will be displayed in the table of
	 * {@link ProgramPage}.
	 *
	 * @return an optional description
	 */
	default Optional<IDisplayString> getDescription() {

		return Optional.empty();
	}
}
