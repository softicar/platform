package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionLog;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

/**
 * Deletes program execution records ({@link AGProgramExecution}) and their log
 * records records ({@link AGProgramExecutionLog}) if they are older than the
 * return value of related method
 * {@link com.softicar.platform.core.module.program.AGProgram#getRetentionDaysOfExecutions()}
 *
 * @author Thees Koester
 */
@EmfSourceCodeReferencePointUuid("3cf637ec-952d-455d-a1be-7a324fff02a6")
public class ProgramExecutionCleanupProgram implements IProgram {

	private static final int THROTTLING_MILLISECONDS = 3000;

	@Override
	public void executeProgram() {

		new ProgramExecutionDeleter(THROTTLING_MILLISECONDS).execute();
	}
}
