package com.softicar.platform.ajax.simple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class SimpleServletOutputStream extends ServletOutputStream {

	private final ByteArrayOutputStream buffer;

	public SimpleServletOutputStream() {

		buffer = new ByteArrayOutputStream();
	}

	public byte[] getData() {

		return buffer.toByteArray();
	}

	@Override
	public void write(int theByte) {

		buffer.write(theByte);
	}

	@Override
	public void write(byte[] bytes) throws IOException {

		buffer.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) {

		buffer.write(bytes, offset, length);
	}

	public void reset() {

		buffer.reset();
	}

	@Override
	public boolean isReady() {

		return true;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {

		throw new UnsupportedOperationException();
	}
}
