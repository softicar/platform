package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import java.util.Collection;

@FunctionalInterface
public interface IStoredFileUploadFileChangeHandler {

	void handleChanged(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> changedFiles);
}
