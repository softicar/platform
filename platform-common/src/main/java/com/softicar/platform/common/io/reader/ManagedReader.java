package com.softicar.platform.common.io.reader;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

/**
 * Default implementation of {@link IManagedReader}.
 *
 * @author Oliver Richers
 */
public class ManagedReader implements IManagedReader {

	private final Reader reader;

	public ManagedReader(String string) {

		this(new StringReader(string));
	}

	public ManagedReader(Reader reader) {

		this.reader = reader;
	}

	@Override
	public int read(char[] buffer) {

		try {
			return reader.read(buffer);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public Collection<String> readLines() {

		return BufferedReaders.readLines(new BufferedReader(reader));
	}
}
