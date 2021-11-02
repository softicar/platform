package com.softicar.platform.common.io.stream;

import java.io.InputStream;

/**
 * The empty {@link InputStream}.
 *
 * @author Oliver Richers
 */
public class EmptyInputStream extends InputStream {

	private static final EmptyInputStream SINGLETON = new EmptyInputStream();

	private EmptyInputStream() {

		// singleton
	}

	public static EmptyInputStream get() {

		return SINGLETON;
	}

	@Override
	public int read() {

		return -1;
	}
}
