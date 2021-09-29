package com.softicar.platform.common.code.classpath.iterable;

import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import com.softicar.platform.common.io.zip.entry.ZipFileEntry;
import java.io.InputStream;

class InternalZipClasspathFile implements IClasspathFile {

	private final ZipFileEntry entry;
	private final String name;

	public InternalZipClasspathFile(ZipFileEntry entry) {

		this.entry = entry;
		this.name = determineSimpleName(getRelativePath());
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public InputStream getInputStream() {

		return entry.getInputStream();
	}

	@Override
	public String getRelativePath() {

		return entry.getName();
	}

	@Override
	public boolean isDirectory() {

		return entry.isDirectory();
	}

	@Override
	public String toString() {

		return String.format("%s (%s)", getRelativePath(), entry.getZipFilename());
	}

	private static String determineSimpleName(String relativePath) {

		relativePath = trimTrailingCharacters(relativePath, '/');

		int indexOfLastSlash = relativePath.lastIndexOf("/");
		if (indexOfLastSlash > -1) {
			return relativePath.substring(indexOfLastSlash + 1);
		} else {
			return relativePath;
		}
	}

	private static String trimTrailingCharacters(String text, char character) {

		int length = text.length();
		while (length > 0 && text.charAt(length - 1) == character) {
			length--;
		}
		return text.substring(0, length);
	}
}
