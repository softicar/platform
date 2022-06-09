package com.softicar.platform.core.module.file.stored.attribute;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.upload.StoredFileUploadDiv;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class StoredFileInput extends AbstractDomValueInputDiv<AGStoredFile> implements IEmfInput<AGStoredFile> {

	private final StoredFileUploadDiv uploadDiv;
	private AGStoredFile file;

	public StoredFileInput() {

		this.uploadDiv = new StoredFileUploadDiv(this::handleAdd, this::handleRemove, false);
		this.file = null;

		appendChild(this.uploadDiv);
	}

	@Override
	public Optional<AGStoredFile> getValue() {

		return Optional.ofNullable(file);
	}

	@Override
	public void setValue(AGStoredFile file) {

		if (file != null) {
			uploadDiv.setFiles(List.of(file));
		} else {
			uploadDiv.setFiles(List.of());
		}
	}

	@Override
	public void executePostSaveHook() {

		if (file != null) {
			file.updateRemoveAtToNever();
		}
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		uploadDiv.setDisabled(disabled);
	}

	// TODO simplify code with PLAT-887
	private void handleAdd(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> addedFiles) {

		if (addedFiles.size() + existingFiles.size() > 1) {
			throw new SofticarUserException(EmfI18n.ONLY_ONE_SINGLE_FILE_MAY_BE_ATTACHED);
		}
		this.file = addedFiles.stream().findFirst().orElse(null);
	}

	private void handleRemove(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> removedFiles) {

		DevNull.swallow(existingFiles);
		DevNull.swallow(removedFiles);
		this.file = null;
	}
}
