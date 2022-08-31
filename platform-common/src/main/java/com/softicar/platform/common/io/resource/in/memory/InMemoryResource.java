package com.softicar.platform.common.io.resource.in.memory;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.AbstractHashableResource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * An {@link IResource} that holds its content in a byte array.
 *
 * @author Oliver Richers
 */
public class InMemoryResource extends AbstractHashableResource {

	private final byte[] content;
	private final IMimeType mimeType;
	private String filename;
	private Charset charset;

	/**
	 * Constructs this {@link InMemoryResource}.
	 *
	 * @param content
	 *            the content of the {@link IResource} (never <i>null</i>)
	 * @param mimeType
	 *            the {@link MimeType} of the {@link IResource} (never
	 *            <i>null</i>)
	 */
	public InMemoryResource(byte[] content, IMimeType mimeType) {

		this.content = content;
		this.mimeType = mimeType;
		this.filename = null;
		this.charset = null;
	}

	@Override
	public InputStream getResourceAsStream() {

		return new ByteArrayInputStream(content);
	}

	@Override
	public IMimeType getMimeType() {

		return mimeType;
	}

	@Override
	public Optional<Charset> getCharset() {

		return Optional.ofNullable(charset);
	}

	@Override
	public Optional<String> getFilename() {

		return Optional.ofNullable(filename);
	}

	/**
	 * Defines the optional filename of this {@link IResource}.
	 *
	 * @param filename
	 *            the filename of this {@link IResource} (may be <i>null</i>)
	 * @return this
	 */
	public InMemoryResource setFilename(String filename) {

		this.filename = filename;
		return this;
	}

	/**
	 * Defines the optional character set of this {@link IResource}.
	 *
	 * @param charset
	 *            the character set of this {@link IResource} (may be
	 *            <i>null</i>)
	 * @return this
	 */
	public InMemoryResource setCharset(Charset charset) {

		this.charset = charset;
		return this;
	}
}
