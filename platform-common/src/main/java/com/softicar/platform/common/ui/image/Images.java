package com.softicar.platform.common.ui.image;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.mime.IMimeType;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

/**
 * Convenience methods for {@link ImageIO}.
 *
 * @author Alexander Schmidt
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

	/**
	 * Determines whether all pixels in the {@link BufferedImage} have the given
	 * {@link Color}.
	 *
	 * @param image
	 *            the image to probe (never <i>null</i>)
	 * @param expectedColor
	 *            the color to probe with (never <i>null</i>)
	 * @return <i>true</i> if all pixels have the given {@link Color};
	 *         <i>false</i> otherwise
	 */
	public static boolean isSingleColor(BufferedImage image, Color expectedColor) {

		Objects.requireNonNull(image);
		Objects.requireNonNull(expectedColor);

		int height = image.getHeight();
		int width = image.getWidth();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (image.getRGB(x, y) != expectedColor.getRGB()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Counts the number of pixels in the {@link BufferedImage} which have the
	 * given {@link Color}.
	 *
	 * @param image
	 *            the image to probe (never <i>null</i>)
	 * @param expectedColor
	 *            the color to probe with (never <i>null</i>)
	 * @return the number if pixels with the given {@link Color}
	 */
	public static long countPixelsWithColor(BufferedImage image, Color expectedColor) {

		Objects.requireNonNull(image);
		Objects.requireNonNull(expectedColor);

		int height = image.getHeight();
		int width = image.getWidth();

		long matchingPixels = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (image.getRGB(x, y) == expectedColor.getRGB()) {
					matchingPixels++;
				}
			}
		}

		return matchingPixels;
	}

	/**
	 * Determines whether {@link #readImages(Supplier)} can handle images with
	 * the given {@link IMimeType}.
	 *
	 * @param mimeType
	 *            the image {@link IMimeType} (never <i>null</i>)
	 * @return <i>true</i> if the given image {@link IMimeType} is supported;
	 *         <i>false</i> otherwise
	 */
	public static boolean isReadable(IMimeType mimeType) {

		Objects.requireNonNull(mimeType);
		return ImageIO.getImageReadersByMIMEType(mimeType.getIdentifier()).hasNext();
	}

	/**
	 * Determines whether {@link #readImages(Supplier)} can handle images with
	 * the given suffix (i.e. file name extension).
	 *
	 * @param suffix
	 *            the image file name suffix (never <i>null</i>)
	 * @return <i>true</i> if the given file name suffix is supported;
	 *         <i>false</i> otherwise
	 */
	public static boolean isReadable(String suffix) {

		Objects.requireNonNull(suffix);
		return ImageIO.getImageReadersBySuffix(suffix).hasNext();
	}
}
