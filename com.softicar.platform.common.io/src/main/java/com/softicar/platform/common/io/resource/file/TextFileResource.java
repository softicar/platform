package com.softicar.platform.common.io.resource.file;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * A textual {@link IResource} implementation based on a {@link File} object.
 *
 * @author Oliver Richers
 */
public class TextFileResource extends FileResource {

	private final Charset charset;

	public TextFileResource(File file, Charset charset) {

		this(file, MimeType.getByFilenameOrDefault(file.getName()), charset);
	}

	public TextFileResource(File file, MimeType mimeType, Charset charset) {

		super(file, mimeType);

		this.charset = charset;
	}

	@Override
	public Optional<Charset> getCharset() {

		return Optional.of(charset);
	}
}
