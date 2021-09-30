package com.softicar.platform.common.io.stream;

import java.io.OutputStream;

public class NullOutputStream extends OutputStream {

	@Override
	public void write(int b) {

		// ignored by design
	}

	@Override
	public void close() {

		// ignored by design
	}
}
