package com.softicar.platform.core.module.file.stored.attribute;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.attribute.upload.IStoredFileUploadSaveHook;
import com.softicar.platform.core.module.file.stored.attribute.upload.StoredSingleFileUploadDiv;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import com.softicar.platform.emf.attribute.input.EmfInputException;
import java.util.Collection;

public class StoredFileInput extends AbstractEmfInputDiv<AGStoredFile> {

	private final StoredSingleFileUploadDiv uploadDiv;
	private final SaveHook saveHook;

	public StoredFileInput() {

		this.saveHook = new SaveHook();
		this.uploadDiv = new StoredSingleFileUploadDiv(saveHook);

		appendChild(this.uploadDiv);
	}

	@Override
	public AGStoredFile getValueOrThrow() throws EmfInputException {

		return saveHook.getFile();
	}

	@Override
	public void setValue(AGStoredFile file) {

		uploadDiv.setFile(file);
	}

	@Override
	public void executePostSaveHook() {

		if (saveHook.getFile() != null) {
			saveHook.getFile().updateRemoveAtToNever();
		}
	}

	private class SaveHook implements IStoredFileUploadSaveHook {

		private AGStoredFile file;

		public SaveHook() {

			this.file = null;
		}

		public AGStoredFile getFile() {

			return file;
		}

		@Override
		public void handleFileAddition(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> newFiles) {

			if (newFiles.size() + existingFiles.size() > 1) {
				throw new SofticarUserException(EmfI18n.ONLY_ONE_SINGLE_FILE_MAY_BE_ATTACHED);
			}
			this.file = newFiles.stream().findFirst().orElse(null);
		}

		@Override
		public void handleFileRemoval(Collection<AGStoredFile> existingFiles, AGStoredFile removedFile) {

			this.file = null;
		}
	}
}
