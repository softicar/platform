package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import java.util.Collection;

@FunctionalInterface
public interface IStoredFileUploadFileChangeHandler {

	// TODO simplify with PLAT-887
	void handleChanged(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> changedFiles);
}
