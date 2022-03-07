package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Optional;

/**
 * This class is only used manually on occasion.
 *
 * @author Oliver Richers
 */
@EmfSourceCodeReferencePointUuid("04e5d280-83a1-4464-b376-3a2fa0ab5473")
public class UnreferencedStoredFileCleanerProgram implements IProgram {

	@Override
	public void executeProgram() {

		new UnreferencedStoredFileCleaner().cleanAll();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional
			.of(//
				CoreI18n.DELETES_FILES_OLDER_THAN_ARG1_DAYS_FROM_FILE_SERVERS_THAT_ARE_NO_LONGER_REFERENCED_IN_THE_DATABASE
					.toDisplay(UnreferencedStoredFileCleaner.MINIMUM_DAYS_BEFORE_REMOVAL));
	}
}
