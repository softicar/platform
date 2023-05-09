package com.softicar.platform.core.module.file.stored.check;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.hash.HashingOutputStream;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.core.module.file.stored.StoredFileUtils;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentName;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StoredFileConsistencyChecker {

	private final StoredFilesConsistencyChecker checker;
	private final AGStoredFileSha1 sha1;
	private final StoredFileContentName contentName;

	public StoredFileConsistencyChecker(StoredFilesConsistencyChecker checker, AGStoredFileSha1 sha1) {

		this.checker = checker;
		this.sha1 = sha1;
		this.contentName = new StoredFileContentName(sha1.getHash());
	}

	public void check() {

		var contentStores = findContentStoresWithFile();
		if (contentStores.isEmpty()) {
			checker.addError("no content store for '%s'".formatted(contentName));
		} else {
			contentStores.forEach(this::check);
		}
	}

	private List<IStoredFileContentStore> findContentStoresWithFile() {

		return checker//
			.getAvailableContentStores()
			.stream()
			.filter(store -> store.exists(contentName.getFullFilename()))
			.collect(Collectors.toList());
	}

	private void check(IStoredFileContentStore contentStore) {

		try (var hashingOutputStream = new HashingOutputStream(ByteArrayOutputStream::new, StoredFileUtils.FILE_HASH)) {
			try (var inputStream = contentStore.getFileInputStream(contentName.getFullFilename())) {
				StreamUtils.copyAndClose(inputStream, hashingOutputStream);
			}

			long expectedSize = sha1.getSize();
			long computedSize = hashingOutputStream.getTotalLength();
			String expectedHash = Hexadecimal.getHexStringUC(sha1.getHash());
			String computedHash = Hexadecimal.getHexStringUC(hashingOutputStream.getFinalHash());
			if (!Objects.equals(computedSize, expectedSize) || !Objects.equals(computedHash, expectedHash)) {
				checker.addError("corrupted file '%s' on store '%s':".formatted(contentName, contentStore.getLocation()));
				checker.addError("\texpected hash: %s".formatted(expectedHash));
				checker.addError("\tcomputed hash: %s".formatted(computedHash));
				checker.addError("\texpected size: %s".formatted(expectedSize));
				checker.addError("\tcomputed size: %s".formatted(computedSize));
			}
		} catch (IOException exception) {
			checker.addException(exception);
		}
	}
}
