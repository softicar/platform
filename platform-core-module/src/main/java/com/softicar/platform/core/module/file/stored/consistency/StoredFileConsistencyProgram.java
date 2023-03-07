package com.softicar.platform.core.module.file.stored.consistency;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.IProgram;
import java.util.Optional;

@SourceCodeReferencePointUuid("09a63822-f5c0-460b-b3f8-8f476133630e")
public class StoredFileConsistencyProgram implements IProgram {

	@Override
	public void executeProgram() {

		new StoredFileConsistencyChecker().checkAll();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.CHECKS_FILES_AGAINST_METADATA_IN_DATABASE);
	}
}
