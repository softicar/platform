package com.softicar.platform.common.io.stream.string;

public class StringInputStreamFactory {

	private final String text;

	public StringInputStreamFactory(String text) {

		this.text = text;
	}

	public StringInputStream create() {

		return new StringInputStream(text);
	}
}
