package com.softicar.platform.common.io.resource.static_;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.hash.AbstractHashableResource;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of {@link IStaticResource}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class StaticResource extends AbstractHashableResource implements IStaticResource {

	private final Class<?> anchorClass;
	private final String filename;
	private final Charset charset;

	/**
	 * Constructs a new {@link StaticResource}.
	 *
	 * @param anchorClass
	 *            a {@link Class} that resides in the package in which the
	 *            resource file is located (never <i>null</i>)
	 * @param filename
	 *            the name of the resource file (never <i>null</i>)
	 * @param charset
	 *            the character set of the resource file, if applicable (may be
	 *            <i>null</i>)
	 */
	public StaticResource(Class<?> anchorClass, String filename, Charset charset) {

		this.anchorClass = Objects.requireNonNull(anchorClass);
		this.filename = Objects.requireNonNull(filename);
		this.charset = charset;
	}

	@Override
	public Class<?> getAnchorClass() {

		return anchorClass;
	}

	@Override
	public InputStream getResourceAsStream() {

		return anchorClass.getResourceAsStream(filename);
	}

	@Override
	public IMimeType getMimeType() {

		return MimeType.getByFilenameOrDefault(filename);
	}

	@Override
	public Optional<Charset> getCharset() {

		return Optional.ofNullable(charset);
	}

	@Override
	public Optional<String> getFilename() {

		return Optional.of(filename);
	}
}
