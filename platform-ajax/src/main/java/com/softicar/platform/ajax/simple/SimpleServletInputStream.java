package com.softicar.platform.ajax.simple;

import java.io.ByteArrayInputStream;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

public class SimpleServletInputStream extends ServletInputStream {

	private final ByteArrayInputStream inputStream;

	public SimpleServletInputStream(byte[] bytes) {

		this.inputStream = new ByteArrayInputStream(bytes);
	}

	@Override
	public int read() {

		return inputStream.read();
	}

	@Override
	public boolean isFinished() {

		return inputStream.available() == 0;
	}

	@Override
	public boolean isReady() {

		return true;
	}

	@Override
	public void setReadListener(ReadListener readListener) {

		throw new UnsupportedOperationException();
	}
}
