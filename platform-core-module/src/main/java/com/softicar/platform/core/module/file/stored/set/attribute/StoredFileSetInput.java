package com.softicar.platform.core.module.file.stored.set.attribute;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.core.module.file.stored.upload.StoredFileUploadDiv;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class StoredFileSetInput extends AbstractEmfInputDiv<AGStoredFileSet> {

	private final StoredFileUploadDiv uploadDiv;
	private AGStoredFileSet fileSet;

	public StoredFileSetInput() {

		this.uploadDiv = new StoredFileUploadDiv(this::handleAdd, this::handleRemove, true);
		this.fileSet = null;

		appendChild(this.uploadDiv);
	}

	@Override
	public Optional<AGStoredFileSet> getValue() {

		return Optional.ofNullable(fileSet);
	}

	@Override
	public void setValue(AGStoredFileSet fileSet) {

		uploadDiv.setFiles(getFiles(fileSet));
	}

	@Override
	public void executePostSaveHook() {

		for (AGStoredFile file: getFiles(fileSet)) {
			file.updateRemoveAtToNever();
		}
	}

	@Override
	public StoredFileSetInput setDisabled(boolean disabled) {

		uploadDiv.setDisabled(disabled);
		return this;
	}

	@Override
	public boolean isDisabled() {

		return uploadDiv.isDisabled();
	}

	private Set<AGStoredFile> getFiles(AGStoredFileSet fileSet) {

		return Optional//
			.ofNullable(fileSet)
			.map(AGStoredFileSet::getElements)
			.orElse(Collections.emptySet());
	}

	private void handleAdd(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> addedFiles) {

		var files = new TreeSet<AGStoredFile>();
		files.addAll(existingFiles);
		files.addAll(addedFiles);
		updateFileSet(files);
	}

	private void handleRemove(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> removedFiles) {

		var files = new TreeSet<AGStoredFile>();
		files.addAll(existingFiles);
		files.removeAll(removedFiles);
		updateFileSet(files);
	}

	private void updateFileSet(Set<AGStoredFile> files) {

		this.fileSet = AGStoredFileSet.TABLE.getOrInsert(files);
	}
}
