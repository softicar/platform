package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.string.hash.Hash;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;

public class StoredFileUtils {

	public static final Charset STORE_CHARSET = Charsets.UTF8;
	public static final Hash FILE_HASH = Hash.SHA1;

	public static StoredFileBuilder createFileBuilder() {

		return new StoredFileBuilder();
	}

	/**
	 * Reads all input from the given {@link InputStream} and stores it as the
	 * content of the specified {@link AGStoredFile}.
	 * <p>
	 * The {@link InputStream} is closed when all data has been transfered.
	 *
	 * @param storedFile
	 *            the stored file
	 * @param inputStream
	 *            the stream to read the content from
	 */
	public static void uploadFileContent(AGStoredFile storedFile, InputStream inputStream) {

		try (OutputStream outputStream = storedFile.getFileContentOutputStream()) {
			StreamUtils.copyAndClose(inputStream, outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Writes the complete content of the given {@link AGStoredFile} to the
	 * specified {@link OutputStream}.
	 * <p>
	 * The {@link OutputStream} is closed when all data has been transfered.
	 *
	 * @param storedFile
	 *            the stored file
	 * @param outputStream
	 *            the stream to write the content to
	 */
	public static void downloadFileContent(AGStoredFile storedFile, OutputStream outputStream) {

		Objects.requireNonNull(storedFile, "file missing");
		try (InputStream inputStream = storedFile.getFileContentInputStream()) {
			StreamUtils.copyAndClose(inputStream, outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
