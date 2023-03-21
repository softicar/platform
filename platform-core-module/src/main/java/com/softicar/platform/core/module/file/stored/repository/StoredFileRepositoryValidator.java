package com.softicar.platform.core.module.file.stored.repository;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class StoredFileRepositoryValidator extends AbstractEmfValidator<AGStoredFileRepository> {

	@Override
	protected void validate() {

		if (tableRow.isPrimary() && !tableRow.isActive()) {
			addError(AGStoredFileRepository.ACTIVE, CoreI18n.CANNOT_DEACTIVATE_THE_PRIMARY_FILE_REPOSITORY);
		}
	}
}
