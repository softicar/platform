package com.softicar.platform.common.io.reader;

import com.softicar.platform.common.io.writer.ManagedWriter;
import java.io.Closeable;
import java.io.Reader;
import java.util.Collection;

/**
 * An {@link IManagedReader} is similar to a {@link Reader} but does not
 * implement {@link Closeable} or {@link AutoCloseable}.
 * <p>
 * The life-cycle of the underlying {@link Reader} is managed outside the scope
 * of the {@link ManagedReader}.
 *
 * @author Oliver Richers
 */
public interface IManagedReader {

	/**
	 * Reads characters from this {@link IManagedReader} into the given buffer.
	 *
	 * @param buffer
	 *            the buffer array (never <i>null</i>)
	 * @return the number of characters read; -1 indicates the end of this
	 *         {@link IManagedReader}
	 */
	int read(char[] buffer);

	/**
	 * Reads all lines from this {@link IManagedReader}.
	 *
	 * @return all lines of text (never <i>null</i>)
	 */
	Collection<String> readLines();

	/**
	 * Reads all remaining text from this {@link IManagedReader}.
	 *
	 * @return all remaining text (never <i>null</i>)
	 */
	default String readAll() {

		StringBuilder builder = new StringBuilder();
		new ManagedWriter(builder).write(this);
		return builder.toString();
	}
}
