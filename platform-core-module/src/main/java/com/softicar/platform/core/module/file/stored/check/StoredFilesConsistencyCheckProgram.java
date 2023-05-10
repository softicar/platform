package com.softicar.platform.core.module.file.stored.check;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.IProgram;
import java.util.Optional;

@SourceCodeReferencePointUuid("8f838db0-5dfd-443a-b19f-6ee8d0de134d")
public class StoredFilesConsistencyCheckProgram implements IProgram {

	@Override
	public void executeProgram() {

		new StoredFilesConsistencyChecker().checkAll();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.VERIFIES_FILE_CONTENT_ON_CONTENT_STORE);
	}
}
