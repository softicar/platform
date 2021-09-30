package com.softicar.platform.common.io.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility methods for {@link BufferedReader}.
 *
 * @author Oliver Richers
 */
public class BufferedReaders {

	/**
	 * Reads all lines from the given {@link BufferedReader}.
	 *
	 * @param reader
	 *            the {@link BufferedReader} instance to read from (never
	 *            <i>null</i>)
	 * @return all read lines (never <i>null</i>)
	 */
	public static Collection<String> readLines(BufferedReader reader) {

		var lines = new ArrayList<String>();
		while (true) {
			String line = readLine(reader);
			if (line != null) {
				lines.add(line);
			} else {
				break;
			}
		}
		return lines;
	}

	/**
	 * Calls {@link BufferedReader#readLine()} and wraps any {@link IOException}
	 * into {@link RuntimeException}.
	 *
	 * @param reader
	 *            the reader (never <i>null</i>)
	 * @return the line as returned by {@link BufferedReader#readLine()} (may be
	 *         <i>null</i>)
	 */
	public static String readLine(BufferedReader reader) {

		try {
			return reader.readLine();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
