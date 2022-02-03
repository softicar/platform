package com.softicar.platform.core.module.file.smb;

/**
 * Common interface of all SMB client implementations.
 * <p>
 * Provides functionality to access entries (files and folders) on an SMB share.
 * <p>
 * {@link CurrentSmbClient} can be used to retrieve an instance.
 *
 * @author Alexander Schmidt
 */
public interface ISmbClient {

	/**
	 * Creates a new {@link ISmbEntry} handle from the given URL and
	 * {@link SmbCredentials}.
	 * <p>
	 * The entry referenced by the given URL can either be a file or a
	 * directory.
	 * <p>
	 * This method does <b>not</b> physically create a file or directory on the
	 * respective SMB share.
	 *
	 * @param url
	 *            the URL to the entry on the SMB share (never <i>null</i>)
	 * @param credentials
	 *            the credentials to log in to the SMB share (never <i>null</i>)
	 * @return the {@link ISmbEntry} handle (never <i>null</i>)
	 */
	ISmbEntry getEntry(String url, SmbCredentials credentials);

	/**
	 * Creates a new {@link ISmbFile} handle from the given URL and
	 * {@link SmbCredentials}.
	 * <p>
	 * If the given URL refers to a directory, {@link SmbNoFileException} is
	 * thrown. For a URL that refers to a directory,
	 * {@link #getDirectory(String, SmbCredentials)} or
	 * {@link #getEntry(String, SmbCredentials)} can be used.
	 * <p>
	 * This method does <b>not</b> physically create a file on the respective
	 * SMB share.
	 * <p>
	 * To physically create the file, use {@link ISmbFile#touch()} or
	 * {@link ISmbFile#createOutputStream()}.
	 *
	 * @param url
	 *            the URL to the file on the SMB share (never <i>null</i>)
	 * @param credentials
	 *            the credentials to log in to the SMB share (never <i>null</i>)
	 * @return the {@link ISmbFile} handle (never <i>null</i>)
	 * @throws SmbNoFileException
	 *             if the given URL refers to a directory
	 */
	ISmbFile getFile(String url, SmbCredentials credentials);

	/**
	 * Creates a new {@link ISmbDirectory} handle from the given URL and
	 * {@link SmbCredentials}.
	 * <p>
	 * If the given URL refers to a file, {@link SmbNoDirectoryException} is
	 * thrown. For a URL that refers to a file,
	 * {@link #getFile(String, SmbCredentials)} or
	 * {@link #getEntry(String, SmbCredentials)} can be used.
	 * <p>
	 * This method does <b>not</b> physically create a directory on the
	 * respective SMB share.
	 * <p>
	 * To physically create the directory, use {@link ISmbDirectory#mkdirs()}.
	 *
	 * @param url
	 *            the URL to the directory on the SMB share (never <i>null</i>)
	 * @param credentials
	 *            the credentials to log in to the SMB share (never <i>null</i>)
	 * @return the {@link ISmbDirectory} handle (never <i>null</i>)
	 * @throws SmbNoDirectoryException
	 *             if the given URL refers to a file
	 */
	ISmbDirectory getDirectory(String url, SmbCredentials credentials);
}
