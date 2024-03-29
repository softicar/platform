package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileContentStores;
import com.softicar.platform.core.module.program.IProgram;
import java.util.Optional;

/**
 * This class is only used manually on occasion.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@SourceCodeReferencePointUuid("04e5d280-83a1-4464-b376-3a2fa0ab5473")
public class UnreferencedStoredFileCleanerProgram implements IProgram {

	@Override
	public void executeProgram() {

		StoredFileContentStores//
			.getPrimaryContentStore()
			.ifPresent(store -> new UnreferencedStoredFileCleaner(store).cleanAll());
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional
			.of(//
				CoreI18n.DELETES_FILES_FROM_THE_PRIMARY_FILE_REPOSITORY_IF_THEY_ARE_OLDER_THAN_ARG1_DAYS_AND_NO_LONGER_REFERENCED_FROM_THE_DATABASE
					.toDisplay(UnreferencedStoredFileCleaner.MINIMUM_DAYS_BEFORE_REMOVAL));
	}
}
