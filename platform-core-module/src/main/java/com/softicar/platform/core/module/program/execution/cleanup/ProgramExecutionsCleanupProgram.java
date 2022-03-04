package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionLog;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Optional;

/**
 * Deletes {@link AGProgramExecution} records and their corresponding
 * {@link AGProgramExecutionLog} records if they are older than the return value
 * of related method
 * {@link com.softicar.platform.core.module.program.AGProgram#getExecutionRetentionDays()}
 *
 * @author Thees Köster
 */
@EmfSourceCodeReferencePointUuid("3cf637ec-952d-455d-a1be-7a324fff02a6")
public class ProgramExecutionsCleanupProgram implements IProgram {

	private static final int THROTTLING_MILLISECONDS = 3000;

	@Override
	public void executeProgram() {

		new ProgramExecutionsDeleter(THROTTLING_MILLISECONDS).delete();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.DELETES_EXECUTION_RECORDS_OF_ALL_PROGRAMS_ACCORDING_TO_THEIR_EXECUTION_RETENTION_DAYS_VALUE);
	}
}
