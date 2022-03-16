package com.softicar.platform.common.string;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to split given strings into parts.
 *
 * @author Marco Pilipovic
 * @author Oliver Richers
 */
public class Tokenizer {

	private final char delimiter;
	private final char escapeChar;

	/**
	 * Constructs the tokenizer with the given delimiter and escape char.
	 *
	 * @param delim
	 *            the delimiter character (char where the string will be split)
	 * @param escape
	 *            escape character (after that, the delimiter will not be
	 *            executed)
	 */
	public Tokenizer(char delim, char escape) {

		this.delimiter = delim;
		this.escapeChar = escape;
	}

	/**
	 * Splits the given string into tokens.
	 *
	 * @param text
	 *            the string to be split
	 * @return a list of strings, representing the tokens of the given string
	 */
	public List<String> tokenize(String text) {

		try {
			return tokenize(new StringReader(text));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Same as public {@link #tokenize(String)} but takes a {@link Reader}
	 * object.
	 *
	 * @param reader
	 *            the reader to read the text from
	 * @return the tokens
	 * @throws IOException
	 */
	public List<String> tokenize(Reader reader) throws IOException {

		List<String> tokens = new ArrayList<>();

		char prevChar = delimiter;
		StringBuilder tmp = new StringBuilder();
		int i;
		while ((i = reader.read()) != -1) {
			char c = (char) i;

			if (c != escapeChar) {
				if (c == delimiter) {
					if (prevChar == escapeChar) {
						tmp.append(delimiter);
					} else {
						tokens.add(tmp.toString());
						tmp = new StringBuilder();
					}
				} else {
					if (prevChar == escapeChar) {
						tmp.append(escapeChar);
					}
					tmp.append(c);
				}
			}

			prevChar = c;
		}

		// don't forget the last element
		tokens.add(tmp.toString());

		return tokens;
	}

	/**
	 * Convenience method to split a string into tokens.
	 *
	 * @param text
	 *            the text to split
	 * @param delim
	 *            the delimiter
	 * @param escape
	 *            the escape character
	 * @return a list of tokens
	 */
	public static List<String> tokenize(String text, char delim, char escape) {

		return new Tokenizer(delim, escape).tokenize(text);
	}
}
