package com.softicar.platform.common.ui.image;

import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.testing.Asserts;
import java.awt.Color;
import java.awt.image.BufferedImage;
import org.junit.Test;

public class ImagesTest extends Asserts {

	@Test
	public void testReadImage() {

		assertImage(300, 150, 0xFF9D7400, readImage(ImagesTestFiles.JPG_IMAGE_300X150));
		assertImage(350, 200, 0xFF10900C, readImage(ImagesTestFiles.PNG_IMAGE_350X200));
		assertImage(400, 200, 0xFFCD4990, readImage(ImagesTestFiles.TIFF_IMAGE_400X200));
	}

	@Test
	public void testReadImages() {

		var images = Images.readImages(() -> ImagesTestFiles.TIFF_MULTI_IMAGE.getResourceAsStream());

		assertNotNull(images);
		assertEquals(3, images.size());
		assertImage(300, 150, 0xFF9D7400, images.get(0));
		assertImage(350, 200, 0xFF10900C, images.get(1));
		assertImage(400, 200, 0xFFCD4990, images.get(2));
	}

	@Test
	public void testIsSingleColor() {

		Color expectedColor = new Color(16, 144, 12);
		BufferedImage image = readImage(ImagesTestFiles.PNG_IMAGE_350X200_SINGLE_COLOR);
		assertTrue(Images.isSingleColor(image, expectedColor));
	}

	@Test
	public void testIsSingleColorWithNonMatchingColor() {

		Color expectedColor = new Color(255, 255, 255);
		BufferedImage image = readImage(ImagesTestFiles.PNG_IMAGE_350X200_SINGLE_COLOR);
		assertFalse(Images.isSingleColor(image, expectedColor));
	}

	@Test
	public void testIsSingleColorWithMultiColorImage() {

		Color expectedColor = new Color(16, 144, 12);
		BufferedImage image = readImage(ImagesTestFiles.PNG_IMAGE_350X200);
		assertFalse(Images.isSingleColor(image, expectedColor));
	}

	@Test
	public void testCountPixelsWithColor() {

		Color backgroundColor = new Color(16, 144, 12);
		Color textColor = new Color(185, 124, 46);
		BufferedImage image = readImage(ImagesTestFiles.PNG_IMAGE_350X200);
		assertEquals(63165, Images.countPixelsWithColor(image, backgroundColor));
		assertEquals(3067, Images.countPixelsWithColor(image, textColor));
	}

	private BufferedImage readImage(IResourceSupplier resourceSupplier) {

		return Images.readImage(resourceSupplier::getResourceAsStream);
	}

	private void assertImage(int expectedWidth, int expectedHeight, int expectedColor, BufferedImage image) {

		assertNotNull(image);
		assertEquals(expectedColor, image.getRGB(0, 0));
		assertEquals("width", expectedWidth, image.getWidth());
		assertEquals("height", expectedHeight, image.getHeight());
	}
}
