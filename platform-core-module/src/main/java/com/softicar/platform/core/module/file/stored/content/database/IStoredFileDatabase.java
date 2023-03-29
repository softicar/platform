package com.softicar.platform.core.module.file.stored.content.database;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.hash.IStoredFileHash;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public interface IStoredFileDatabase {

	/**
	 * Saves the given hash and file size for the specified file in the
	 * database.
	 *
	 * @param storedFile
	 *            the associated file
	 * @param hash
	 *            the hash of the file content
	 * @param size
	 *            the size of the file content
	 */
	void saveFileHash(AGStoredFile storedFile, byte[] hash, long size);

	/**
	 * Fetches the file hash currently saved for the given file.
	 *
	 * @param storedFile
	 *            the associated file
	 * @return the hash of the file content or null, if none has been saved in
	 *         the database
	 */
	IStoredFileHash getFileHash(AGStoredFile storedFile);

	Collection<IStoredFileHash> getUnreferencedFileHashes();

	/**
	 * Creates an {@link InputStream} to read the file content from the data
	 * chunks.
	 *
	 * @param storedFile
	 *            the associated file
	 * @return new input stream from the data chunks
	 */
	InputStream createChunksInputStream(AGStoredFile storedFile);

	/**
	 * Creates an {@link OutputStream} for the given file into the database.
	 * <p>
	 * This method should be used if the usual file store is not accessible. The
	 * content is saved into small data chunks.
	 *
	 * @param storedFile
	 *            the associated file
	 * @return new output stream into the database
	 */
	OutputStream createChunksOutputStream(AGStoredFile storedFile);

	/**
	 * Returns the number of data chunks that are saved in the database.
	 *
	 * @param storedFile
	 *            the associated file
	 * @return number of data chunks
	 */
	int getChunkCount(AGStoredFile storedFile);
}
