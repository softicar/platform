package com.softicar.platform.common.io.writer;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.IOException;

/**
 * Default implementation of {@link IManagedWriter}.
 *
 * @author Oliver Richers
 */
public class ManagedWriter implements IManagedWriter {

	private final Appendable appendable;

	public ManagedWriter(Appendable appendable) {

		this.appendable = appendable;
	}

	@Override
	public void write(char character) {

		try {
			appendable.append(character);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void write(CharSequence sequence) {

		try {
			appendable.append(sequence);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void write(CharSequence sequence, int start, int end) {

		try {
			appendable.append(sequence, start, end);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
