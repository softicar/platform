package com.softicar.platform.common.io.classpath.file;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A proxy to the contents of a file inside a zip-compressed archive.
 *
 * @author Alexander Schmidt
 */
public class ZipClasspathFile implements IClasspathFile {

	private final File archive;
	private final String entryName;
	private final boolean directory;

	/**
	 * @param archive
	 *            a handle to a zip-compressed archive
	 * @param entryName
	 *            the path to an entry inside the zip-compressed file, e.g.
	 *            "foo/bar/baz.txt"
	 * @param directory
	 *            boolean flag indicating whether this is a directory
	 */
	public ZipClasspathFile(File archive, String entryName, boolean directory) {

		this.archive = Objects.requireNonNull(archive);
		this.entryName = Objects.requireNonNull(entryName);
		this.directory = directory;
	}

	@SuppressWarnings("resource")
	@Override
	public InputStream getInputStream() {

		try (ZipFile zipFile = new ZipFile(archive)) {
			ZipEntry zipEntry = zipFile.getEntry(stripLeadingSlash(entryName));
			InputStream inputStream = zipFile.getInputStream(zipEntry);
			return getCopiedInputStream(inputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public String getName() {

		int indexOfLastSlash = entryName.lastIndexOf("/");
		if (indexOfLastSlash > -1) {
			return entryName.substring(indexOfLastSlash + 1);
		} else {
			return entryName;
		}
	}

	@Override
	public String getRelativePath() {

		return entryName;
	}

	@Override
	public boolean isDirectory() {

		return directory;
	}

	/**
	 * Deeply copies the given {@link InputStream} to a new {@link InputStream}.
	 * This is necessary because the {@link InputStream} from a {@link ZipEntry}
	 * will be closed when the containing {@link ZipFile} is closed.
	 *
	 * @param inputStream
	 * @return a new {@link InputStream}, copied from the given one
	 */
	private ByteArrayInputStream getCopiedInputStream(InputStream inputStream) {

		return new ByteArrayInputStream(StreamUtils.toByteArray(inputStream));
	}

	private String stripLeadingSlash(String input) {

		if (input.startsWith("/")) {
			return input.substring(1);
		} else {
			return input;
		}
	}
}
