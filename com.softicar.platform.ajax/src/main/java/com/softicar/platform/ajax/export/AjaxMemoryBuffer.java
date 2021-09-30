package com.softicar.platform.ajax.export;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Simple implementation of {@link IAjaxExportBuffer} using
 * {@link ByteArrayOutputStream}.
 * 
 * @author Oliver Richers
 */
public class AjaxMemoryBuffer implements IAjaxExportBuffer {

	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@Override
	public OutputStream openForWriting() {

		return outputStream;
	}

	@Override
	public InputStream openForReading() {

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	@Override
	public void dispose() {

		outputStream.reset();
	}
}
