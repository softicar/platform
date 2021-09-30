package com.softicar.platform.core.module.file.stored.overview;

import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import java.util.Optional;

class StoredFileOverviewRow {

	private final AGStoredFile file;
	private final AGStoredFileSha1 fileHash;

	public StoredFileOverviewRow(AGStoredFile file, AGStoredFileSha1 fileHash) {

		this.file = file;
		this.fileHash = fileHash;
	}

	public AGStoredFile getFile() {

		return file;
	}

	public ItemId getId() {

		return file.getItemId();
	}

	public String getFileName() {

		return file.toDisplayWithoutId().toString();
	}

	public Long getFileSize() {

		return file.getFileSize();
	}

	public String getFileHashString() {

		return Optional//
			.ofNullable(fileHash)
			.map(it -> it.getHashString())
			.orElse("");
	}
}
