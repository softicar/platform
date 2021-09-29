package com.softicar.platform.common.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * A static factory for {@link BufferedReader}.
 *
 * @author Oliver Richers
 */
public class BufferedReaderFactory {

	public static BufferedReader read(InputStream inputStream, Charset charset) {

		return new BufferedReader(InputStreamReaderFactory.read(inputStream, charset));
	}

	public static BufferedReader readUtf8(InputStream inputStream) {

		return new BufferedReader(InputStreamReaderFactory.readUtf8(inputStream));
	}

	public static BufferedReader readUtf8(File file) {

		return new BufferedReader(InputStreamReaderFactory.readUtf8(file));
	}
}
