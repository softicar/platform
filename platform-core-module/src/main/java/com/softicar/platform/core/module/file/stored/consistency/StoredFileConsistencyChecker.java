package com.softicar.platform.core.module.file.stored.consistency;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.hash.HashingOutputStream;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StoredFileConsistencyChecker {

	public void checkAll() {

		ExceptionsCollector collector = new ExceptionsCollector();
		for (var file: AGStoredFile.createSelect().list()) {
			try {
				checkFile(file);
			} catch (Exception exception) {
				collector.add(exception);
			}
		}
		collector.throwExceptionIfNotEmpty();
	}

	private void checkFile(AGStoredFile file) {

		try {
			long storedSize = file.getFileSize();
			String storedHash = file.getFileHashString();
			if (storedHash != null) {

				try (InputStream fileContentInputStream = file.getFileContentInputStream();
						OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						HashingOutputStream hashingOutputStream = new HashingOutputStream(() -> byteArrayOutputStream, StoredFileUtils.FILE_HASH)) {

					StreamUtils.copy(fileContentInputStream, hashingOutputStream);
					hashingOutputStream.close();

					List<String> messages = new ArrayList<>();

					// check size
					long processedSize = hashingOutputStream.getTotalLength();
					if (storedSize != processedSize) {
						Log.ferror("Size mismatch: '%s' (stored) != '%s' (processed)", storedSize, processedSize);
					}

					// check hash
					String computedHash = Hexadecimal.getHexStringUC(hashingOutputStream.getFinalHash());
					if (!storedHash.equalsIgnoreCase(computedHash)) {
						Log.ferror("Hash mismatch: '%s' (stored) != '%s' (computed)", storedHash, computedHash);
					}

					// print errors
					if (!messages.isEmpty()) {
						Log.ferror("File '%s' has errors:", file.toDisplay());
						messages.forEach(message -> Log.ferror("  " + message));
					}
				}
			}
		} catch (Exception exception) {
			throw new RuntimeException(String.format("Caught exception for file '%s'.", file.toDisplay()), exception);
		}
	}
}
