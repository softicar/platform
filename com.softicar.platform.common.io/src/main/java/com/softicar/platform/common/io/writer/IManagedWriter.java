package com.softicar.platform.common.io.writer;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.reader.IManagedReader;
import com.softicar.platform.common.io.reader.ManagedReader;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.ResourceReader;
import com.softicar.platform.common.string.array.CharSequenceArray;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * An {@link IManagedWriter} is similar to a {@link Writer} but does not
 * implement {@link Closeable} or {@link AutoCloseable}.
 * <p>
 * The life-cycle of the underlying {@link Appendable} is managed outside the
 * scope of the {@link IManagedWriter}.
 *
 * @author Oliver Richers
 */
public interface IManagedWriter {

	/**
	 * Same semantics as {@link Appendable#append(char)}.
	 */
	void write(char character);

	/**
	 * Same semantics as {@link Appendable#append(CharSequence)}.
	 */
	void write(CharSequence sequence);

	/**
	 * Same semantics as {@link Appendable#append(CharSequence,int,int)}.
	 */
	void write(CharSequence sequence, int start, int end);

	/**
	 * Reads all characters from the given {@link IManagedReader} and writes
	 * them to this {@link IManagedWriter}.
	 *
	 * @param reader
	 *            the {@link IManagedReader} to read from (never <i>null</i> )
	 */
	default void write(IManagedReader reader) {

		int n;
		CharSequenceArray buffer = new CharSequenceArray(256 * 1024);
		while ((n = reader.read(buffer.getArray())) != -1) {
			write(buffer, 0, n);
		}
	}

	/**
	 * Reads all characters from the given {@link IResource} and writes them to
	 * this {@link IManagedWriter}.
	 *
	 * @param resource
	 *            the {@link IResource} to read from (never <i>null</i> )
	 */
	default void write(IResource resource) {

		try (Reader reader = new ResourceReader(resource)) {
			write(new ManagedReader(reader));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
