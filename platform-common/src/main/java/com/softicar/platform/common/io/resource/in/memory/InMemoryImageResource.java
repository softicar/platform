package com.softicar.platform.common.io.resource.in.memory;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.resource.IResource;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

/**
 * An {@link InMemoryResource} for images.
 *
 * @author Oliver Richers
 */
public class InMemoryImageResource extends InMemoryResource {

	/**
	 * Constructs this {@link InMemoryImageResource} from a
	 * {@link RenderedImage}.
	 *
	 * @param image
	 *            the {@link BufferedImage} (never <i>null</i>)
	 * @param imageFormat
	 *            the image format as given to {@link ImageIO#write} (never
	 *            <i>null</i>)
	 * @param mimeType
	 *            the {@link IMimeType} of this {@link IResource} (never
	 *            <i>null</i>)
	 */
	public InMemoryImageResource(RenderedImage image, String imageFormat, IMimeType mimeType) {

		super(convertImageToByteArray(image, imageFormat), mimeType);
	}

	private static byte[] convertImageToByteArray(RenderedImage image, String imageFormat) {

		Objects.requireNonNull(image);
		Objects.requireNonNull(imageFormat);

		try {
			var buffer = new ByteArrayOutputStream();
			ImageIO.write(image, imageFormat, buffer);
			return buffer.toByteArray();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
