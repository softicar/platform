package com.softicar.platform.core.module.file.stored.set.attribute;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.attribute.upload.IStoredFileUploadSaveHook;
import com.softicar.platform.core.module.file.stored.attribute.upload.StoredFileUploadDiv;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class StoredFileSetInput extends AbstractEmfInputDiv<AGStoredFileSet> {

	private final StoredFileUploadDiv uploadDiv;
	private final SaveHook saveHook;

	public StoredFileSetInput() {

		this.uploadDiv = new StoredFileUploadDiv();
		this.saveHook = new SaveHook();
		this.uploadDiv.setSaveHook(saveHook);

		appendChild(this.uploadDiv);
	}

	@Override
	public Optional<AGStoredFileSet> getValue() {

		return Optional.ofNullable(saveHook.getFileSet());
	}

	@Override
	public void setValue(AGStoredFileSet fileSet) {

		uploadDiv.setFiles(getFiles(fileSet));
	}

	@Override
	public void executePostSaveHook() {

		for (AGStoredFile file: getFiles(saveHook.getFileSet())) {
			file.updateRemoveAtToNever();
		}
	}

	private Set<AGStoredFile> getFiles(AGStoredFileSet fileSet) {

		return Optional//
			.ofNullable(fileSet)
			.map(AGStoredFileSet::getElements)
			.orElse(Collections.emptySet());
	}

	private class SaveHook implements IStoredFileUploadSaveHook {

		private AGStoredFileSet fileSet;

		public SaveHook() {

			this.fileSet = null;
		}

		@Override
		public void handleFileAddition(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> newFiles) {

			Set<AGStoredFile> files = new TreeSet<>();
			files.addAll(existingFiles);
			files.addAll(newFiles);
			renewFileSet(files);
		}

		@Override
		public void handleFileRemoval(Collection<AGStoredFile> existingFiles, AGStoredFile removedFile) {

			Set<AGStoredFile> files = new TreeSet<>();
			files.addAll(existingFiles);
			files.remove(removedFile);
			renewFileSet(files);
		}

		public AGStoredFileSet getFileSet() {

			return fileSet;
		}

		private void renewFileSet(Set<AGStoredFile> files) {

			this.fileSet = AGStoredFileSet.TABLE.getOrInsert(files);
		}
	}
}
