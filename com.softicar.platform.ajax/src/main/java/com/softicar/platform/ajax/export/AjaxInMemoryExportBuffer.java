package com.softicar.platform.ajax.export;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.stream.EmptyInputStream;
import com.softicar.platform.common.io.stream.SwitchingOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

/**
 * A limited in-memory {@link IAjaxExportBuffer}.
 * <p>
 * This buffer stores the data up to a specific limit in memory. If the limit is
 * exceeded, a alternative {@link IAjaxExportBuffer} is used.
 *
 * @author Oliver Richers
 */
public class AjaxInMemoryExportBuffer implements IAjaxExportBuffer {

	private final int maximumSize;
	private final Supplier<IAjaxExportBuffer> alternativeBufferFactory;
	private ByteArrayOutputStream byteArrayBuffer;
	private IAjaxExportBuffer alternativeBuffer;

	public AjaxInMemoryExportBuffer(int maximumSize, Supplier<IAjaxExportBuffer> alternativeBufferFactory) {

		if (maximumSize < 0) {
			throw new IllegalArgumentException("Buffer size may not be negative.");
		}

		this.maximumSize = maximumSize;
		this.alternativeBufferFactory = alternativeBufferFactory;
		this.byteArrayBuffer = new ByteArrayOutputStream();
		this.alternativeBuffer = null;
	}

	@Override
	public OutputStream openForWriting() {

		return new SwitchingOutputStream(maximumSize, byteArrayBuffer, this::switchToAlternativeBuffer);
	}

	@Override
	public InputStream openForReading() {

		if (alternativeBuffer != null) {
			return alternativeBuffer.openForReading();
		} else if (byteArrayBuffer != null) {
			return new ByteArrayInputStream(byteArrayBuffer.toByteArray());
		} else {
			return EmptyInputStream.get();
		}
	}

	@Override
	public void dispose() {

		if (alternativeBuffer != null) {
			alternativeBuffer.dispose();
		} else {
			this.byteArrayBuffer = null;
		}
	}

	private OutputStream switchToAlternativeBuffer() {

		this.alternativeBuffer = alternativeBufferFactory.get();

		OutputStream alternativeOutputStream = alternativeBuffer.openForWriting();
		copy(byteArrayBuffer, alternativeOutputStream);
		this.byteArrayBuffer = null;

		return alternativeOutputStream;
	}

	private static void copy(ByteArrayOutputStream byteArrayOutputStream, OutputStream outputStream) {

		try {
			byteArrayOutputStream.writeTo(outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
