package com.softicar.platform.ajax.engine;

import com.softicar.platform.ajax.AjaxTestResources;
import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.resource.AjaxResourceUrlParser;
import com.softicar.platform.ajax.resource.registry.AjaxResourceRegistry;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.in.memory.InMemoryImageResource;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssDisplay;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Optional;
import org.junit.Test;

public class AjaxDomEngineTest extends AbstractAjaxSeleniumLowLevelTest {

	@Test
	public void testSetAlternateResourceOnErrorWithPng() {

		var png = openTestNode(() -> new DomImage(AjaxTestResources.TEST_PNG.getResource()));

		// PNG remains PNG
		var pngResource = assertOne(getResourceByHash(getAttributeValue(png, "src")));
		assertEquals(MimeType.IMAGE_PNG, pngResource.getMimeType());
		assertEquals("test.png", assertOne(pngResource.getFilename()));
	}

	/**
	 * Most browsers do not support the TIFF image format, so we assert that it
	 * was converted to PNG.
	 */
	@Test
	public void testSetAlternateResourceOnErrorWithTiff() {

		var tiff = openTestNode(() -> new DomImage(AjaxTestResources.TEST_TIFF.getResource()));

		// TIFF converted to PNG
		var tiffResource = assertOne(getResourceById(getAttributeValue(tiff, "src")));
		assertEquals(MimeType.IMAGE_PNG, tiffResource.getMimeType());
		assertEquals("test.tiff.png", assertOne(tiffResource.getFilename()));
	}

	@Test
	public void testSetHeightAndWidthOnLoad() {

		var testDiv = openTestNode(() -> new ImageTestDiv());

		var width = getStyleValue(testDiv.getInnerDiv(), "width");
		var height = getStyleValue(testDiv.getInnerDiv(), "height");

		assertEquals(ImageTestDiv.IMAGE_WIDTH + "px", width);
		assertEquals(ImageTestDiv.IMAGE_HEIGHT + "px", height);
	}

	private Optional<IResource> getResourceByHash(String resourceUrl) {

		var document = AjaxDocument.getCurrentDocument().get();
		var resourceRegistry = AjaxResourceRegistry.getInstance(document.getHttpSession());
		var resourceHash = assertOne(new AjaxResourceUrlParser(resourceUrl).getResourceHash());
		return resourceRegistry.getResourceByHash(resourceHash);
	}

	private Optional<IResource> getResourceById(String resourceUrl) {

		var document = AjaxDocument.getCurrentDocument().get();
		var resourceRegistry = AjaxResourceRegistry.getInstance(document.getHttpSession());
		var resourceId = assertOne(new AjaxResourceUrlParser(resourceUrl).getResourceId());
		return resourceRegistry.getResourceById(resourceId);
	}

	private static class ImageTestDiv extends DomDiv {

		public static final int IMAGE_HEIGHT = 123;
		public static final int IMAGE_WIDTH = 321;
		private final DomDiv innerDiv;

		public ImageTestDiv() {

			setBackgroundColor(DomColorEnum.DARK_GREEN);
			setStyle(CssDisplay.FLEX);
			setStyle(CssStyle.ALIGN_ITEMS, "flex-start");

			this.innerDiv = new DomDiv();
			this.innerDiv.setBackgroundColor(DomColorEnum.YELLOW);
			this.innerDiv.setStyle(CssDisplay.FLEX);
			this.innerDiv.setStyle(CssStyle.ALIGN_ITEMS, "flex-start");

			var image = new DomImage(createImage(IMAGE_WIDTH, IMAGE_HEIGHT, new Color(0x0000FF)));

			appendChild(innerDiv);
			innerDiv.appendChild(image);

			getDomEngine().setHeightAndWidthOnLoad(image, innerDiv);
		}

		public DomDiv getInnerDiv() {

			return innerDiv;
		}

		private IResource createImage(int width, int height, Color color) {

			var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			var graphics = image.createGraphics();
			graphics.setPaint(color);
			graphics.fillRect(0, 0, width, height);
			graphics.dispose();
			return new InMemoryImageResource(image, "jpg", MimeType.IMAGE_JPEG);
		}
	}
}
