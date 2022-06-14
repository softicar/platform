package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.string.hexadecimal.Hexadecimal;

/**
 * This class takes care of the mapping between the hash of the file content and
 * the associated filename on the file store.
 *
 * @author Oliver Richers
 */
public class StoredFileContentName {

	private final static int SUFFIX_SIZE = 38;
	private final static int PREFIX_SIZE = 2;
	private final static String NIBBLE = "[a-zA-Z0-9]";
	private final static String PATTERN = String.format("/%s{%d}/%s{%d}", NIBBLE, PREFIX_SIZE, NIBBLE, SUFFIX_SIZE);
	private final static String TMP_PATTERN = "/tmp/[1-9][0-9]*";
	private final String hashString;

	public StoredFileContentName(byte[] hash) {

		this(Hexadecimal.getHexStringUC(hash));
	}

	public StoredFileContentName(String hashString) {

		this.hashString = hashString;
	}

	public static boolean isCorrectFilename(String filename) {

		return filename.matches(PATTERN);
	}

	public static boolean isTmpFilename(String filename) {

		return filename.matches(TMP_PATTERN);
	}

	public static StoredFileContentName createFromFilename(String filename) {

		if (isCorrectFilename(filename)) {
			String prefix = filename.substring(1, 1 + PREFIX_SIZE);
			String suffix = filename.substring(1 + PREFIX_SIZE + 1);
			return new StoredFileContentName(prefix + suffix);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {

		return getFullFilename();
	}

	public byte[] getHash() {

		return Hexadecimal.getBytesFromHexString(hashString);
	}

	public String getHashString() {

		return hashString;
	}

	public String getFolderName() {

		return "/" + getHashPrefix();
	}

	public String getFullFilename() {

		return getFolderName() + "/" + getHashSuffix();
	}

	private String getHashPrefix() {

		return hashString.substring(0, PREFIX_SIZE);
	}

	private String getHashSuffix() {

		return hashString.substring(PREFIX_SIZE);
	}
}
