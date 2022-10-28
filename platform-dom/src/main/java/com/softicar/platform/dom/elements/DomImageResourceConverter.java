package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.AbstractHashableResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;

/**
 * Converts a given image {@link IResource} into a PNG {@link IResource}.
 * <p>
 * This {@link IResource} is used as a fallback for browsers not supporting the
 * original image format, e.g. TIFF.
 *
 * @author Oliver Richers
 */
public class DomImageResourceConverter extends AbstractHashableResource {

	private static final MimeType TARGET_MIME_TYPE = MimeType.IMAGE_PNG;
	private static final String TARGET_FORMAT_NAME = "png";
	private final IResource originalResource;

	public DomImageResourceConverter(IResource originalResource) {

		this.originalResource = Objects.requireNonNull(originalResource);
	}

	@Override
	public InputStream getResourceAsStream() {

		var buffer = new ByteArrayOutputStream();
		try (var stream = originalResource.getResourceAsStream()) {
			ImageIO.write(ImageIO.read(stream), TARGET_FORMAT_NAME, buffer);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		return new ByteArrayInputStream(buffer.toByteArray());
	}

	@Override
	public IMimeType getMimeType() {

		return TARGET_MIME_TYPE;
	}

	@Override
	public Optional<Charset> getCharset() {

		return Optional.empty();
	}

	@Override
	public Optional<String> getFilename() {

		return originalResource.getFilename().map(filename -> filename + "." + TARGET_FORMAT_NAME);
	}
}
