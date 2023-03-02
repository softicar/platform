package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Provides functionality to store and retrieve the actual files that are
 * represented by {@link AGStoredFile} instances.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IStoredFileContentStore {

	/**
	 * Returns a String that informally describes the location of this content
	 * store.
	 *
	 * @return the informal location of this content store (never <i>null</i>)
	 */
	String getLocation();

	/**
	 * Determines whether this content store should be used, according to some
	 * configuration.
	 *
	 * @return <i>true</i> if this content store should be used; <i>false</i>
	 *         otherwise
	 */
	boolean isEnabled();

	/**
	 * Determines whether this content store is ready to be used, from a
	 * technical perspective.
	 *
	 * @return <i>true</i> if this content store is ready to be used;
	 *         <i>false</i> otherwise
	 */
	boolean isReady();

	/**
	 * @deprecated use {@link #isReady()} instead
	 */
	@Deprecated
	default boolean isAvailable() {

		return isReady();
	}

	/**
	 * Determines the disk space in bytes that is still available in this
	 * content store.
	 *
	 * @return the free disk space in bytes
	 */
	long getFreeDiskSpace();

	/**
	 * Creates the directory identified by the given relative path in this
	 * content store.
	 * <p>
	 * Creates parent directories if necessary.
	 * <p>
	 * If the directory already exists, nothing will happen.
	 *
	 * @param directoryPath
	 *            the relative path of a directory in this content store (never
	 *            <i>null</i>)
	 */
	void createDirectories(String directoryPath);

	/**
	 * Creates an {@link OutputStream} for the file identified by the given
	 * relative path in this content store.
	 * <p>
	 * If the file does not exist, it will be created.
	 * <p>
	 * If the file already exists, an {@link OutputStream} for the existing file
	 * is returned. Writing to that {@link OutputStream} will replace/overwrite
	 * the content of the existing file.
	 * <p>
	 * Throws an {@link Exception} if the relative path refers to a file in a
	 * non-existent directory, or if it refers to a directory, or if a referred
	 * non-existent file cannot be created.
	 * <p>
	 * The caller is obliged to close the returned {@link OutputStream} after
	 * use.
	 *
	 * @param filePath
	 *            the relative path of a file in this content store (never
	 *            <i>null</i>)
	 * @return an {@link OutputStream} for the file (never <i>null</i>)
	 */
	OutputStream getFileOutputStream(String filePath);

	/**
	 * Creates an {@link InputStream} for the file identified by the given
	 * relative path in this content store.
	 * <p>
	 * Throws an {@link Exception} if the relative path refers to a non-existent
	 * file.
	 * <p>
	 * The caller is obliged to close the returned {@link InputStream} after
	 * use.
	 *
	 * @param filePath
	 *            the relative path of a file in this content store (never
	 *            <i>null</i>)
	 * @return an {@link InputStream} for the file (never <i>null</i>)
	 */
	InputStream getFileInputStream(String filePath);

	/**
	 * Moves the file identified by the given relative source path in the
	 * content store to the given relative target path.
	 * <p>
	 * Throws an {@link Exception} if the source file does not exist, or if the
	 * target file already exists, or if the target directory does not exist.
	 *
	 * @param sourceFilePath
	 *            the relative path of a source file in this content store
	 *            (never <i>null</i>)
	 * @param targetFilePath
	 *            the relative path of a target file in this content store
	 *            (never <i>null</i>)
	 */
	void moveFile(String sourceFilePath, String targetFilePath);

	/**
	 * Deletes the file identified by the given relative path from this content
	 * store.
	 * <p>
	 * Throws an {@link Exception} if the relative path refers to a non-existent
	 * file.
	 *
	 * @param filePath
	 *            the relative path of a file in this content store (never
	 *            <i>null</i>)
	 */
	void deleteFile(String filePath);

	/**
	 * Determines whether a file or directory with the given relative path
	 * exists in this content store.
	 *
	 * @param path
	 *            the relative path of a file or directory in this content store
	 *            (never <i>null</i>)
	 * @return <i>true</i> if the file or directory exists; <i>false</i>
	 *         otherwise
	 */
	boolean exists(String path);

	/**
	 * Determines the size in bytes of the file with the given relative path in
	 * this content store.
	 * <p>
	 * Returns 0 if the file does not exist.
	 *
	 * @param filePath
	 *            the relative path of a file in this content store (never
	 *            <i>null</i>)
	 * @return the file size in bytes
	 */
	long getFileSize(String filePath);

	/**
	 * Returns the modification time of the given relative path of a file or
	 * directory in this content store.
	 * <p>
	 * Returns {@link DayTime#get1970()} if the given file or directory does not
	 * exist.
	 *
	 * @param path
	 *            the relative path of a file or directory in this content store
	 *            (never <i>null</i>)
	 * @return the modification time (never <i>null</i>)
	 */
	DayTime getLastModified(String path);

	/**
	 * Recursively determines the relative paths of the files in this content
	 * store.
	 *
	 * @return all relative file paths (never <i>null</i>)
	 */
	Collection<String> getAllFilePaths();

	/**
	 * Recursively determines the relative paths of the files in this content
	 * store. Only includes files that reside in the sub directory identified by
	 * the given relative path.
	 *
	 * @param directoryPath
	 *            the relative path of a directory in this content store (never
	 *            <i>null</i>)
	 * @return all file paths, relative to the content store root, in the given
	 *         sub directory (never <i>null</i>)
	 */
	Collection<String> getAllFilePaths(String directoryPath);
}
