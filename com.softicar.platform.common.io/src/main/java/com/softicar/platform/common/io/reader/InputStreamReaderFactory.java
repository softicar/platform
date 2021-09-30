package com.softicar.platform.common.io.reader;

import com.softicar.platform.common.io.file.FileInputStreamFactory;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.function.Supplier;

/**
 * A static factory for {@link InputStreamReader}.
 *
 * @author Oliver Richers
 */
public class InputStreamReaderFactory {

	/**
	 * Creates a new {@link Reader} for the given {@link InputStream} and the
	 * given {@link Charset}.
	 *
	 * @param inputStream
	 *            the {@link InputStream} to read from (never <i>null</i>)
	 * @param charset
	 *            the {@link Charset} of the data of the {@link InputStream}
	 * @return a new {@link Reader} decoding the {@link InputStream} (never
	 *         <i>null</i>)
	 */
	public static InputStreamReader read(InputStream inputStream, Charset charset) {

		return new InputStreamReader(inputStream, charset);
	}

	/**
	 * Creates a new {@link Reader} for the given {@link Supplier} of
	 * {@link InputStream} and the given {@link Charset}.
	 *
	 * @param streamSupplier
	 *            the {@link Supplier} of {@link InputStream} to read from
	 *            (never <i>null</i>)
	 * @param charset
	 *            the {@link Charset} of the data of the {@link InputStream}
	 * @return a new {@link Reader} decoding the {@link InputStream} (never
	 *         <i>null</i>)
	 */
	public static InputStreamReader read(Supplier<InputStream> streamSupplier, Charset charset) {

		return new InputStreamReader(streamSupplier.get(), charset);
	}

	/**
	 * Creates a new UTF-8 {@link Reader} for the given {@link InputStream}.
	 *
	 * @param inputStream
	 *            the {@link InputStream} to read from (never <i>null</i>)
	 * @return a new {@link Reader} decoding the {@link InputStream} (never
	 *         <i>null</i>)
	 */
	public static InputStreamReader readUtf8(InputStream inputStream) {

		return read(inputStream, Charsets.UTF8);
	}

	/**
	 * Creates a new UTF-8 {@link Reader} for the given {@link Supplier} of
	 * {@link InputStream}.
	 *
	 * @param streamSupplier
	 *            the {@link Supplier} of {@link InputStream} to read from
	 *            (never <i>null</i>)
	 * @return a new {@link Reader} decoding the {@link InputStream} (never
	 *         <i>null</i>)
	 */
	public static InputStreamReader readUtf8(Supplier<InputStream> streamSupplier) {

		return read(streamSupplier, Charsets.UTF8);
	}

	/**
	 * Creates a new UTF-8 reader for the given {@link File}.
	 *
	 * @param file
	 *            the {@link File} to read from (never <i>null</i>)
	 * @return a new {@link InputStreamReader} decoding the file content as
	 *         UTF-8 (never <i>null</i>)
	 */
	public static InputStreamReader readUtf8(File file) {

		return new InputStreamReader(FileInputStreamFactory.create(file), Charsets.UTF8);
	}
}
