package com.softicar.platform.ajax.dom.image.viewer;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineOutput;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.in.memory.InMemoryImageResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssPixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import org.junit.Test;

public class DomImageViewerTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int PAGE_COUNT = 3;
	private static final int VIEWER_WIDTH = 300;
	private static final int IMAGE_WIDTH = 500;
	private static final int IMAGE_HEIGHT = 700;
	private final AjaxSeleniumLowLevelTestEngineInput input;
	private final AjaxSeleniumLowLevelTestEngineOutput output;
	private final IDomNode image;
	private final IDomNode imageDiv;

	public DomImageViewerTest() {

		openTestNode(() -> new DomImageViewer(createImageList(), new CssPixel(VIEWER_WIDTH)));

		this.input = getTestEngine().getInput();
		this.output = getTestEngine().getOutput();
		this.image = output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_IMAGE);
		this.imageDiv = output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_IMAGE_DIV);
	}

	@Test
	public void testInitialDisplay() {

		assertPage(1);
		output.assertCssClasses(List.of(DomCssClasses.DOM_IMAGE_VIEWER_IMAGE), image);
		output.assertCssMaxWidth(VIEWER_WIDTH + "px", image);
		output.assertSize(VIEWER_WIDTH, IMAGE_HEIGHT * VIEWER_WIDTH / IMAGE_WIDTH, image);
		assertRotated(false);
	}

	@Test
	public void testRotate() {

		clickRotateButton();
		assertRotated(true);

		clickRotateButton();
		assertRotated(false);
	}

	@Test
	public void testPagingBeforeFirstPage() {

		clickPreviousPageButton();
		assertPage(1);
	}

	@Test
	public void testPagingAfterLastPage() {

		goToLastPage();

		clickNextPageButton();
		assertPage(PAGE_COUNT);
	}

	private void goToLastPage() {

		for (int i = 0; i < PAGE_COUNT - 1; i++) {
			clickNextPageButton();
		}
	}

	private void assertPage(int index) {

		assertPageDisplay("%s / %s".formatted(index, PAGE_COUNT));
		assertEquals("enabled", index > 1, getPreviousPageButton().isEnabled());
		assertEquals("enabled", index < PAGE_COUNT, getNextPageButton().isEnabled());
	}

	private void assertPageDisplay(String expectedText) {

		var pageDisplay = output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_PAGE_DISPLAY);
		assertEquals(expectedText, output.getText(pageDisplay));
	}

	private void clickNextPageButton() {

		input.click(getNextPageButton());
	}

	private DomButton getNextPageButton() {

		return (DomButton) output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_NEXT_PAGE_BUTTON);
	}

	private void clickPreviousPageButton() {

		input.click(getPreviousPageButton());
	}

	private DomButton getPreviousPageButton() {

		return (DomButton) output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_PREVIOUS_PAGE_BUTTON);
	}

	private void clickRotateButton() {

		input.click(output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_ROTATE_BUTTON));
	}

	private void assertRotated(boolean rotated) {

		if (rotated) {
			output.assertCssTransform("matrix(-1, 0, 0, -1, 0, 0)", imageDiv);
		} else {
			output.assertCssTransform("none", imageDiv);
		}
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
