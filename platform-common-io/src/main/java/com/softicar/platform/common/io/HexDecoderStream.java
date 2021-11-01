package com.softicar.platform.common.io;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.stream.hexadecimal.UnexpectedEndOfHexadecimalStreamException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A special {@link InputStream}, which converts hexadecimal encodings to
 * binary.
 *
 * @author Oliver Richers
 */
public class HexDecoderStream extends InputStream {

	private final InputStream stream;

	public HexDecoderStream(InputStream stream) {

		this.stream = stream;
	}

	@Override
	public int read() {

		int nibble1 = readNextNibble();
		if (nibble1 < 0) {
			return -1;
		}

		int nibble2 = readNextNibble();
		if (nibble2 < 0) {
			throw new UnexpectedEndOfHexadecimalStreamException();
		}

		return decodeNibbles(nibble1, nibble2);
	}

	@Override
	public void close() {

		StreamUtils.close(stream);
	}

	private int readNextNibble() {

		try {
			return stream.read();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private int decodeNibbles(int nibble1, int nibble2) {

		return (decodeNibble(nibble1) << 4) + decodeNibble(nibble2);
	}

	private int decodeNibble(int nibble) {

		if (nibble >= '0' && nibble <= '9') {
			return nibble - '0';
		}

		if (nibble >= 'A' && nibble <= 'F') {
			return 10 + nibble - 'A';
		}

		if (nibble >= 'a' && nibble <= 'f') {
			return 10 + nibble - 'a';
		}

		throw new SofticarDeveloperException("Invalid hexadecimal nibble %d.", nibble);
	}
}
