package com.softicar.platform.core.module.file.smb;

import java.io.OutputStream;

/**
 * Common interface of all SMB client implementations.
 *
 * @author Alexander Schmidt
 */
public interface ISmbClient {

	// TODO
	ISmbEntry getEntry(String url, SmbCredentials credentials);

	/**
	 * Creates a new {@link ISmbFile} from the given URL and
	 * {@link SmbCredentials}.
	 * <p>
	 * Note that if the file at the given URL does not exist, it won't get
	 * physically created until until you create an {@link OutputStream} via
	 * {@link ISmbFile#createOutputStream()} and write the corresponding data to
	 * it.
	 *
	 * @param url
	 *            the URL to the file on the SMB share (never <i>null</i>)
	 * @param credentials
	 *            the credentials to log in to the SMB share (never <i>null</i>)
	 * @return the new {@link ISmbFile} (never <i>null</i>)
	 */
	ISmbFile getFile(String url, SmbCredentials credentials);

	/**
	 * Creates a new {@link ISmbDirectory} from the given URL and
	 * {@link SmbCredentials}.
	 * <p>
	 * If the directory at the given URL does not exist, you need to physically
	 * create it using {@link ISmbDirectory#mkdirs()}.
	 *
	 * @param url
	 *            the URL to the directory on the SMB share (never <i>null</i>)
	 * @param credentials
	 *            the credentials to log in to the SMB share (never <i>null</i>)
	 * @return the new {@link ISmbDirectory} (never <i>null</i>)
	 */
	ISmbDirectory getDirectory(String url, SmbCredentials credentials);
}
