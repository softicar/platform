package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.resource.hash.AbstractHashableResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

class ImageByteArrayResource extends AbstractHashableResource {

	private final ByteArrayOutputStream outputStream;
	private final String fileName;
	private final IMimeType mimeType;

	public ImageByteArrayResource(ByteArrayOutputStream outputStream, String fileName, IMimeType mimeType) {

		this.outputStream = outputStream;
		this.fileName = fileName;
		this.mimeType = mimeType;
	}

	@Override
	public InputStream getResourceAsStream() {

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	@Override
	public IMimeType getMimeType() {

		return mimeType;
	}

	@Override
	public Optional<Charset> getCharset() {

		return Optional.empty();
	}

	@Override
	public Optional<String> getFilename() {

		return Optional.ofNullable(fileName);
	}
}
