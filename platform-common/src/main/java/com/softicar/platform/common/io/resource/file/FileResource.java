package com.softicar.platform.common.io.resource.file;

import com.softicar.platform.common.io.file.FileInputStreamFactory;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.AbstractHashableResource;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * An {@link IResource} implementation based on a {@link File} object.
 *
 * @author Oliver Richers
 */
public class FileResource extends AbstractHashableResource {

	private final File file;
	private final MimeType mimeType;

	public FileResource(File file) {

		this(file, MimeType.getByFilenameOrDefault(file.getName()));
	}

	public FileResource(File file, MimeType mimeType) {

		this.file = file;
		this.mimeType = mimeType;
	}

	@Override
	public InputStream getResourceAsStream() {

		return FileInputStreamFactory.create(file);
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

		return Optional.of(file.getName());
	}
}
