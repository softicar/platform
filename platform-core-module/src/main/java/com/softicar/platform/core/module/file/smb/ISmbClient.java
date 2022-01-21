package com.softicar.platform.core.module.file.smb;

/**
 * Common interface of all SMB client implementations.
 *
 * @author Alexander Schmidt
 */
public interface ISmbClient {

	/**
	 * Initializes this SMB client implementation.
	 * <p>
	 * Can be used to set related configuration parameters.
	 */
	void initialize();

	/**
	 * Creates a new {@link ISmbFile} from the given URL and
	 * {@link SmbCredentials}.
	 *
	 * @param url
	 *            the URL to the file on the SMB share (never <i>null</i>)
	 * @param credentials
	 *            the credentials to log in to the SMB share (never <i>null</i>)
	 * @return the new {@link ISmbFile} (never <i>null</i>)
	 */
	ISmbFile createFile(String url, SmbCredentials credentials);
}
