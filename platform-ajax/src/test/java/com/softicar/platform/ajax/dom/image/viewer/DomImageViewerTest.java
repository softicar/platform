package com.softicar.platform.ajax.dom.image.viewer;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineOutput;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.in.memory.InMemoryImageResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssPixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import org.junit.Test;

public class DomImageViewerTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int VIEWER_WIDTH = 300;
	private static final int IMAGE_WIDTH = 500;
	private static final int IMAGE_HEIGHT = 700;
	private final AjaxSeleniumLowLevelTestEngineInput input;
	private final AjaxSeleniumLowLevelTestEngineOutput output;
	private final IDomNode imageNode;

	public DomImageViewerTest() {

		openTestNode(() -> new DomImageViewer(createImageList(), new CssPixel(VIEWER_WIDTH)));

		this.input = getTestEngine().getInput();
		this.output = getTestEngine().getOutput();
		this.imageNode = output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_IMAGE);
	}

	@Test
	public void testInitialDisplay() {

		var rect = output.getRectangle(imageNode);
		assertEquals(VIEWER_WIDTH, rect.getWidth());
		assertEquals(IMAGE_HEIGHT * VIEWER_WIDTH / IMAGE_WIDTH, rect.getHeight());
		output.assertCssClasses(List.of(DomCssClasses.DOM_IMAGE_VIEWER_IMAGE), imageNode);
		output.assertCssMaxWidth(VIEWER_WIDTH + "px", imageNode);
		output.assertCssTransform("none", imageNode);
	}

	@Test
	public void testRotate() {

		clickRotateButton();
		output.assertCssTransform("matrix(-1, 0, 0, -1, 0, 0)", imageNode);

		clickRotateButton();
		output.assertCssTransform("none", imageNode);
	}

	private void clickRotateButton() {

		input.click(output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_ROTATE_BUTTON));
	}

	private List<IResource> createImageList() {

		return List
			.of(//
				createImage(IMAGE_WIDTH, IMAGE_HEIGHT, Color.RED),
				createImage(IMAGE_WIDTH, IMAGE_HEIGHT, Color.GREEN),
				createImage(IMAGE_WIDTH, IMAGE_HEIGHT, Color.BLUE));
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
