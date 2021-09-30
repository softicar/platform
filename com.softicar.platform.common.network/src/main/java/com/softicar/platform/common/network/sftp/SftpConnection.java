package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.softicar.platform.common.core.utils.CastUtils;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

/**
 * This class is a convenience wrapper around {@link ChannelSftp}.
 *
 * @author Oliver Richers
 */
public class SftpConnection implements Closeable {

	private static final int MAX_CONNECT_TRY_COUNT = 3;
	private static final int TIMEOUT = 10 * 1000;
	private ChannelSftp sftp;
	private Session session;

	public SftpConnection(String hostname, String username) {

		try {
			JSch jsch = new JSch();
			jsch.addIdentity(getPublicKeyFilePath());
			this.session = createSession(hostname, username, jsch);
			this.sftp = (ChannelSftp) session.openChannel("sftp");
			this.sftp.connect();
		} catch (JSchException exception) {
			throw new JschRuntimeException(exception);
		}
	}

	@Override
	public void close() {

		sftp.exit();
		session.disconnect();
	}

	/**
	 * Returns the internal {@link ChannelSftp} object.
	 * <p>
	 * This method should only be used if absolutely necessary. First check if
	 * this class already provides a public method that covers your use case.
	 *
	 * @return the internal {@link ChannelSftp} object (never null)
	 */
	public ChannelSftp getSftpChannel() {

		return sftp;
	}

	// ------------------------------ file content ------------------------------ //

	/**
	 * Opens an {@link InputStream} to the content of the given file.
	 *
	 * @param path
	 *            the path to the file (never null)
	 * @return the {@link InputStream} (never null)
	 */
	public InputStream getInputStream(String path) {

		try {
			return sftp.get(path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	/**
	 * Opens an {@link OutputStream} to the content of the given file.
	 * <p>
	 * If the target file already exists, it will be overwritten.
	 *
	 * @param path
	 *            the path to the file (never null)
	 * @return the {@link OutputStream} (never null)
	 */
	public OutputStream getOuputStream(String path) {

		try {
			return sftp.put(path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	// ------------------------------ move, rename and remove ------------------------------ //

	/**
	 * Renames or moves the given file or folder.
	 *
	 * @param oldPath
	 *            the old path (never null)
	 * @param newPath
	 *            the new path (never null)
	 */
	public void rename(String oldPath, String newPath) {

		try {
			sftp.rename(oldPath, newPath);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	/**
	 * Removes the given file.
	 *
	 * @param path
	 *            the path of the file to remove (never null)
	 */
	public void rm(String path) {

		try {
			sftp.rm(path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	// ------------------------------ folders ------------------------------ //

	/**
	 * Lists the content of the given folder.
	 *
	 * @param path
	 *            the path to the folder (never null)
	 * @return a list of the contained {@link LsEntry} objects (never null)
	 */
	public Vector<LsEntry> ls(String path) {

		try {
			return CastUtils.cast(sftp.ls(path));
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	/**
	 * Creates the given folder.
	 *
	 * @param path
	 *            the path of the folder to create (never null)
	 */
	public void mkdir(String path) {

		try {
			sftp.mkdir(path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	/**
	 * Removes the given folder.
	 *
	 * @param path
	 *            the path of the folder to remove (never null)
	 */
	public void rmdir(String path) {

		try {
			sftp.rmdir(path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	// ------------------------------ links ------------------------------ //

	/**
	 * Creates a new symbolic link.
	 *
	 * @param targetPath
	 *            the path to the link target (never null)
	 * @param path
	 *            the path to the link (never null)
	 */
	public void symlink(String targetPath, String path) {

		try {
			sftp.symlink(targetPath, path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	/**
	 * Returns the target of the given symbolic link.
	 *
	 * @param path
	 *            the path to the link (never null)
	 * @return the target path (never null)
	 */
	public String readlink(String path) {

		try {
			return sftp.readlink(path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	/**
	 * Returns the real path of the target of the given symbolic link.
	 *
	 * @param path
	 *            the path to the link (never null)
	 * @return the real path (never null)
	 */
	public String realpath(String path) {

		try {
			return sftp.readlink(path);
		} catch (SftpException exception) {
			throw SftpExceptionFatory.create(exception);
		}
	}

	// ------------------------------ private ------------------------------ //

	private Session createSession(String hostname, String username, JSch jsch) throws JSchException {

		JSchException finalException = null;

		for (int i = 0; i < MAX_CONNECT_TRY_COUNT; ++i) {
			try {
				Session session = jsch.getSession(username, hostname);
				session.setTimeout(TIMEOUT);
				session.setConfig("StrictHostKeyChecking", "no");
				session.connect();
				return session;
			} catch (JSchException exception) {
				finalException = exception;
			}
		}

		// connection failed
		assert MAX_CONNECT_TRY_COUNT > 0;
		assert finalException != null;
		throw finalException;
	}

	private static String getPublicKeyFilePath() {

		String userHome = System.getProperty("user.home");
		return String.format("%s/.ssh/id_rsa", userHome);
	}
}
