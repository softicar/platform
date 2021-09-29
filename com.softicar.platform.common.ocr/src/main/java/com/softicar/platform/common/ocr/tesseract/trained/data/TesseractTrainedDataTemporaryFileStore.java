package com.softicar.platform.common.ocr.tesseract.trained.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A Tesseract4 trained data file store which saves files to a temporary
 * directory.
 * <p>
 * More specifically, the files are stored in a randomly-named sub-directory of
 * the the general temporary directory of the system on which the JVM instance
 * runs (that is, "<code>/tmp/</code>" on Linux systems).
 * <p>
 * The created temporary directory (e.g.
 * "<code>/tmp/tesseract-trained-data-917fe340-bc0f-45d9-a1de-b45d9fa80937</code>"),
 * and all files it contains, are scheduled for removal upon JVM termination.
 *
 * @author Alexander Schmidt
 */
public class TesseractTrainedDataTemporaryFileStore implements ITesseractTrainedDataFileStore {

	private static final String TESSERACT_DATA_TEMPORARY_DIRECTORY_PREFIX = "tesseract-trained-data-";

	private File temporaryDirectory;

	public TesseractTrainedDataTemporaryFileStore() {

		this.temporaryDirectory = null;
	}

	@Override
	public boolean put(String fileName, byte[] content) {

		try {
			File directory = getDirectory();
			File file = new File(directory, fileName);
			if (file.createNewFile()) {
				try (FileOutputStream outputStream = new FileOutputStream(file)) {
					outputStream.write(content);
				}
				file.deleteOnExit();
				return true;
			} else {
				return false;
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public File get(String fileName) {

		File directory = getDirectory();
		File file = new File(directory, fileName);
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}

	@Override
	public File getDirectory() {

		if (temporaryDirectory == null) {
			this.temporaryDirectory = createTemporaryDirectory().toFile();
			this.temporaryDirectory.deleteOnExit();
		}
		return temporaryDirectory;
	}

	private Path createTemporaryDirectory() {

		try {
			return Files.createTempDirectory(TESSERACT_DATA_TEMPORARY_DIRECTORY_PREFIX);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
