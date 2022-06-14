package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Optional;

@EmfSourceCodeReferencePointUuid("934acfe5-f73f-414c-ae80-941f9773b0ef")
public class StoredFileContentMigrationProgram implements IProgram {

	@Override
	public void executeProgram() {

		new StoredFileContentMigrationExecutor().execute();
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.COPIES_MISSING_FILES_FROM_SECONDARY_FILE_SERVERS_TO_THE_PRIMARY_FILE_SERVER);
	}
}
