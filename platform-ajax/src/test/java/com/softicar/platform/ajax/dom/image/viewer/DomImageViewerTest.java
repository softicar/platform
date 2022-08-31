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

	private static final int VIEWER_WIDTH = 300;
	private static final int IMAGE_WIDTH = 500;
	private static final int IMAGE_HEIGHT = 700;
	private final List<IResource> images;
	private final AjaxSeleniumLowLevelTestEngineInput input;
	private final AjaxSeleniumLowLevelTestEngineOutput output;

	public DomImageViewerTest() {

		this.images = createImageList();
		this.input = getTestEngine().getInput();
		this.output = getTestEngine().getOutput();

		openTestNode(() -> new DomImageViewer(images, new CssPixel(VIEWER_WIDTH)));
	}

	@Test
	public void testInitialDisplay() {

		var image = findImage();

		assertShownPage(1);
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
	public void testPagingToNextPage() {

		for (int index = 2; index <= images.size(); index++) {
			clickNextPageButton();
			assertShownPage(index);
		}
	}

	@Test
	public void testPagingToPreviousPage() {

		goToLastPage();

		for (int index = images.size() - 1; index >= 1; index--) {
			clickPreviousPageButton();
			assertShownPage(index);
		}
	}

	@Test
	public void testPagingBeforeFirstPage() {

		clickPreviousPageButton();
		assertShownPage(1);
	}

	@Test
	public void testPagingAfterLastPage() {

		goToLastPage();

		clickNextPageButton();
		assertShownPage(images.size());
	}

	@Test
	public void testZoomIn() {

		var image = findImage();
		click(image);

		assertShownPage(1);
		output.assertCssMaxWidth("none", image);
		output.assertSize(IMAGE_WIDTH, IMAGE_HEIGHT, image);
	}

	@Test
	public void testZoomInAndGoToNextPage() {

		click(findImage());
		clickNextPageButton();

		assertShownPage(2);
		output.assertCssMaxWidth("none", findImage());
		output.assertSize(IMAGE_WIDTH, IMAGE_HEIGHT, findImage());
	}

	@Test
	public void testZoomInAndOut() {

		var image = findImage();
		click(image);
		click(image);

		assertShownPage(1);
		output.assertCssMaxWidth(VIEWER_WIDTH + "px", image);
		output.assertSize(VIEWER_WIDTH, IMAGE_HEIGHT * VIEWER_WIDTH / IMAGE_WIDTH, image);
	}

	// ------------------------------ paging ------------------------------ //

	private void goToLastPage() {

		for (int i = 2; i <= images.size(); i++) {
			clickNextPageButton();
		}
	}

	private void assertShownPage(int index) {

		assertShownImage(index);
		assertPageIndicator("%s / %s".formatted(index, images.size()));
		assertEquals("enabled", index > 1, getPreviousPageButton().isEnabled());
		assertEquals("enabled", index < images.size(), getNextPageButton().isEnabled());
	}

	private void assertPageIndicator(String expectedText) {

		var pageIndicator = output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_PAGE_INDICATOR);
		assertEquals(expectedText, output.getText(pageIndicator));
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

	// ------------------------------ image ------------------------------ //

	private void assertShownImage(int index) {

		var resourceHash = images.get(index - 1).getContentHash().get().toString();
		assertContains("?resourceHash=" + resourceHash, getAttributeValue(findImage(), "src"));
	}

	private IDomNode findImage() {

		return output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_IMAGE);
	}

	private IDomNode findImageDiv() {

		return output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_IMAGE_DIV);
	}

	// ------------------------------ rotation ------------------------------ //

	private void clickRotateButton() {

		input.click(output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_ROTATE_BUTTON));
	}

	private void assertRotated(boolean rotated) {

		if (rotated) {
			output.assertCssTransform("matrix(-1, 0, 0, -1, 0, 0)", findImageDiv());
		} else {
			output.assertCssTransform("none", findImageDiv());
		}
	}

	// ------------------------------ setup ------------------------------ //

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
