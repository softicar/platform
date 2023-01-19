package com.softicar.platform.common.ui.image;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

/**
 * Convenience methods for {@link ImageIO}.
 *
 * @author Oliver Richers
 */
public class Images {

	/**
	 * Reads the image contained in the given {@link InputStream}.
	 * <p>
	 * Some image formats like TIFF support multiple images per image file. If
	 * multiple images exist, this method only return the first image.
	 *
	 * @param inputStreamFactory
	 *            the factory for the {@link InputStream} (never <i>null</i>)
	 * @return the loaded {@link BufferedImage}
	 */
	public static BufferedImage readImage(Supplier<InputStream> inputStreamFactory) {

		var iterator = readImages(inputStreamFactory).iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			throw new SofticarException("Failed to read image.");
		}
	}

	/**
	 * Reads the images contained in the given {@link InputStream}.
	 * <p>
	 * Some image formats like TIFF support multiple images per image file.
	 *
	 * @param inputStreamFactory
	 *            the factory for the {@link InputStream} (never <i>null</i>)
	 * @return the {@link List} of loaded {@link BufferedImage} objects
	 */
	public static List<BufferedImage> readImages(Supplier<InputStream> inputStreamFactory) {

		var images = new ArrayList<BufferedImage>();
		try (var inputStream = inputStreamFactory.get()) {
			try (var imageInputStream = ImageIO.createImageInputStream(inputStream)) {
				var readers = ImageIO.getImageReaders(imageInputStream);
				if (readers.hasNext()) {
					var reader = readers.next();
					reader.setInput(imageInputStream);
					var count = reader.getNumImages(true);
					for (int i = 0; i < count; i++) {
						images.add(reader.read(i));
					}
				}
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
		return images;
	}
}
