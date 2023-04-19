package com.softicar.platform.core.module.program.execution.deleter;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionLog;
import java.util.Optional;

/**
 * Deletes {@link AGProgramExecution} records and their corresponding
 * {@link AGProgramExecutionLog} records if they are older than the return value
 * of {@link AGProgram#getExecutionRetentionDays()}.
 *
 * @author Thees Köster
 */
@SourceCodeReferencePointUuid("3cf637ec-952d-455d-a1be-7a324fff02a6")
public class OldProgramExecutionsDeleterProgram implements IProgram {

	private static final int THROTTLING_MILLISECONDS = 3000;

	@Override
	public void executeProgram() {

		new OldProgramExecutionsDeleter(THROTTLING_MILLISECONDS).delete();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional
			.of(
				CoreI18n.DELETES_EXECUTION_RECORDS_OF_ALL_PROGRAMS_ACCORDING_TO_THEIR_EXECUTION_RETENTION_DAYS_VALUE
					.concatSentence(CoreI18n.ALSO_CLEANS_UP_ANY_ORPHANED_RECORDS));
	}
}
