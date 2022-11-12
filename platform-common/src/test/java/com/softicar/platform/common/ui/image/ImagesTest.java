package com.softicar.platform.common.ui.image;

import com.softicar.platform.common.testing.Asserts;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import org.junit.Test;

public class ImagesTest extends Asserts {

	private static final String IMAGE_300x150_JPG = "image300x150.jpg";
	private static final String IMAGE_350x200_PNG = "image350x200.png";
	private static final String IMAGE_400x200_TIF = "image400x200.tif";
	private static final String MULTI_IMAGE_TIF = "multi-image.tif";

	@Test
	public void testReadImage() {

		assertImage(300, 150, 0xFF9D7400, readImage(IMAGE_300x150_JPG));
		assertImage(350, 200, 0xFF10900C, readImage(IMAGE_350x200_PNG));
		assertImage(400, 200, 0xFFCD4990, readImage(IMAGE_400x200_TIF));
	}

	@Test
	public void testReadImages() {

		var images = Images.readImages(() -> getClass().getResourceAsStream(MULTI_IMAGE_TIF));

		assertNotNull(images);
		assertEquals(3, images.size());
		assertImage(300, 150, 0xFF9D7400, images.get(0));
		assertImage(350, 200, 0xFF10900C, images.get(1));
		assertImage(400, 200, 0xFFCD4990, images.get(2));
	}

	private BufferedImage readImage(String name) {

		return Images.readImage(() -> getClass().getResourceAsStream(name));
	}

	private void assertImage(int expectedWidth, int expectedHeight, int expectedColor, BufferedImage image) {

		assertNotNull(image);
		assertEquals(expectedColor, image.getRGB(0, 0));
		assertEquals("width", expectedWidth, image.getWidth());
		assertEquals("height", expectedHeight, image.getHeight());
	}

	public static void main(String[] args) throws IOException {

		var image1 = Images.readImage(() -> ImagesTest.class.getResourceAsStream(IMAGE_300x150_JPG));
		var image2 = Images.readImage(() -> ImagesTest.class.getResourceAsStream(IMAGE_350x200_PNG));
		var image3 = Images.readImage(() -> ImagesTest.class.getResourceAsStream(IMAGE_400x200_TIF));

		var writers = ImageIO.getImageWritersByFormatName("tif");
		if (writers.hasNext()) {
			var writer = writers.next();
			try (var output = ImageIO.createImageOutputStream(new File("/home/richers/tmp/multi-image.tif"))) {
				writer.setOutput(output);
				ImageWriteParam params = writer.getDefaultWriteParam();
				params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				params.setCompressionType("Deflate");
				writer.prepareWriteSequence(null);
				writer.writeToSequence(new IIOImage(image1, null, null), params);
				writer.writeToSequence(new IIOImage(image2, null, null), params);
				writer.writeToSequence(new IIOImage(image3, null, null), params);
				writer.endWriteSequence();
			}
		}
	}
}
