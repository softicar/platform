package com.softicar.platform.core.module.file.stored.attribute.upload;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import java.util.Collection;

public interface IStoredFileUploadSaveHook {

	void handleFileAddition(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> newFiles);

	void handleFileRemoval(Collection<AGStoredFile> existingFiles, AGStoredFile removedFile);
}
