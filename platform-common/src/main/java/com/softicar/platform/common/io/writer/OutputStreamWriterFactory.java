package com.softicar.platform.common.io.writer;

import com.softicar.platform.common.io.file.FileOutputStreamFactory;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * A static factory for {@link OutputStreamWriter}.
 *
 * @author Oliver Richers
 */
public class OutputStreamWriterFactory {

	/**
	 * Creates a new writer for the given {@link OutputStream} and character
	 * set.
	 *
	 * @param outputStream
	 *            the output stream to use
	 * @param charset
	 *            the character set of the data in the output stream
	 * @return a new {@link OutputStreamWriter} decoding the output stream
	 */
	public static OutputStreamWriter write(OutputStream outputStream, Charset charset) {

		return new OutputStreamWriter(outputStream, charset);
	}

	/**
	 * Creates a new UTF-8 writer for the given {@link OutputStream}.
	 *
	 * @param outputStream
	 *            the {@link OutputStream} to use (never <i>null</i>)
	 * @return a new {@link OutputStreamWriter} decoding the
	 *         {@link OutputStream} as UTF-8 (never <i>null</i>)
	 */
	public static OutputStreamWriter writeUtf8(OutputStream outputStream) {

		return new OutputStreamWriter(outputStream, Charsets.UTF8);
	}

	/**
	 * Creates a new UTF-8 writer for the given {@link File}.
	 *
	 * @param file
	 *            the {@link File} to write to (never <i>null</i>)
	 * @return a new {@link OutputStreamWriter} writing to the {@link File} as
	 *         UTF-8 (never <i>null</i>)
	 */
	public static OutputStreamWriter writeUtf8(File file) {

		return new OutputStreamWriter(FileOutputStreamFactory.create(file), Charsets.UTF8);
	}
}
