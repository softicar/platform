package com.softicar.platform.common.io.stream.string;

import java.io.ByteArrayInputStream;

public class StringInputStream extends ByteArrayInputStream {

	public StringInputStream(String text) {

		super(text.getBytes());
	}
}
