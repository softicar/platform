package com.softicar.platform.common.io.file.name;

import java.util.Objects;

/**
 * Utility to replace problematic characters in filenames.
 * <p>
 * TODO The list of replaced characters is arbitrary and incomplete.
 */
public class FilenameCleaner {

	private final String filename;

	/**
	 * Constructs this {@link FilenameCleaner}.
	 *
	 * @param filename
	 *            the filename to clean (never null)
	 */
	public FilenameCleaner(String filename) {

		this.filename = Objects.requireNonNull(filename);
	}

	/**
	 * Replaces various problematic characters in the filename.
	 * <p>
	 * German non-Latin characters are replaced with their Latin standard
	 * replacement. While special symbol characters are replaced with
	 * underscore.
	 *
	 * @return the clean filename (never null)
	 */
	public String getCleanFilename() {

		int n = filename.length();
		StringBuilder builder = new StringBuilder(n);
		for (int i = 0; i < n; ++i) {
			char c = filename.charAt(i);
			switch (c) {
			case '\u00C4':
				builder.append("Ae");
				break;
			case '\u00E4':
				builder.append("ae");
				break;
			case '\u00D6':
				builder.append("Oe");
				break;
			case '\u00F6':
				builder.append("oe");
				break;
			case '\u00DC':
				builder.append("Ue");
				break;
			case '\u00FC':
				builder.append("ue");
				break;
			case '\u00DF':
				builder.append("ss");
				break;
			case '\r':
			case '\n':
				builder.append("");
				break;
			case ' ':
			case '"':
			case '+':
			case '/':
			case '\'':
			case '\\':
				builder.append("_");
				break;
			default:
				builder.append(c < 0 || c > 127? "_" : c);
				break;
			}
		}
		return builder.toString();
	}
}
