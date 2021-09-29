package com.softicar.platform.core.module.email.buffer.attachment;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.stream.limit.LimitedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

public class BufferedEmailAttachment {

	public static final int MAXIMUM_SIZE = 16 * 1024 * 1024;
	private final String name;
	private final IMimeType type;
	private final byte[] data;

	public BufferedEmailAttachment(String name, IMimeType type, Supplier<InputStream> dataSupplier) {

		this.name = name;
		this.type = type;
		this.data = copyData(dataSupplier);
	}

	public BufferedEmailAttachment(String name, IMimeType mimeType, byte[] data) {

		this.name = name;
		this.type = mimeType;
		this.data = data;
	}

	public String getName() {

		return name;
	}

	public IMimeType getType() {

		return type;
	}

	public byte[] getData() {

		return data;
	}

	private byte[] copyData(Supplier<InputStream> contentSupplier) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try (InputStream input = contentSupplier.get()) {
			try (OutputStream output = new LimitedOutputStream(buffer, MAXIMUM_SIZE)) {
				StreamUtils.copy(input, output);
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
		return buffer.toByteArray();
	}
}
