package com.softicar.platform.ajax.dom.image.viewer;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineOutput;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.in.memory.InMemoryImageResource;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssPixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.Test;

public class DomImageViewerTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int VIEWER_WIDTH = 1000;
	private static final int IMAGE_WIDTH = 1800;
	private static final int IMAGE_HEIGHT = 700;
	private static final int IMAGE_PADDING = 10;
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

		assertShownPage(1);
		assertZoomLevel(100);
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

		clickZoomInButton();

		assertShownPage(1);
		assertZoomLevel(125);
	}

	@Test
	public void testZoomOut() {

		clickZoomOutButton();

		assertShownPage(1);
		assertZoomLevel(75);
	}

	@Test
	public void testZoomInAndOut() {

		// --- zoom in --- //

		clickZoomInButton();
		assertZoomLevel(125);

		clickZoomInButton();
		assertZoomLevel(150);

		clickZoomInButton();
		assertZoomLevel(175);

		// --- zoom out --- //

		clickZoomOutButton();
		assertZoomLevel(150);

		clickZoomOutButton();
		assertZoomLevel(125);

		clickZoomOutButton();
		assertZoomLevel(100);

		clickZoomOutButton();
		assertZoomLevel(75);
	}

	@Test
	public void testZoomInAndGoToNextPage() {

		clickZoomInButton();
		clickNextPageButton();

		assertShownPage(2);
		assertZoomLevel(125);
	}

	// ------------------------------ paging ------------------------------ //

	private void goToLastPage() {

		for (int i = 2; i <= images.size(); i++) {
			clickNextPageButton();
		}
		waitForServer();
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
		waitForServer();
	}

	private DomButton getNextPageButton() {

		return (DomButton) output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_NEXT_PAGE_BUTTON);
	}

	private void clickPreviousPageButton() {

		input.click(getPreviousPageButton());
		waitForServer();
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

	private IDomNode findImageHolder() {

		return output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_IMAGE_HOLDER);
	}

	// ------------------------------ rotation ------------------------------ //

	private void clickRotateButton() {

		input.click(output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_ROTATE_BUTTON));
		waitForServer();
	}

	private void assertRotated(boolean rotated) {

		if (rotated) {
			output.assertCssTransform("matrix(-1, 0, 0, -1, 0, 0)", findImageHolder());
		} else {
			output.assertCssTransform("matrix(1, 0, 0, 1, 0, 0)", findImageHolder());
		}
	}

	// ------------------------------ zooming ------------------------------ //

	private void clickZoomInButton() {

		input.click(output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_ZOOM_IN_BUTTON));
		waitForServer();
	}

	private void clickZoomOutButton() {

		input.click(output.findNodeOrFail(DomTestMarker.IMAGE_VIEWER_ZOOM_OUT_BUTTON));
		waitForServer();
	}

	private int getZoomedHolderWidth(int zoomPercentage) {

		return (int) Math.round(zoomPercentage / 100.0 * VIEWER_WIDTH);
	}

	private int getZoomedHolderHeight(int zoomPercentage) {

		var imageElementWidth = getZoomedHolderWidth(zoomPercentage) - 2 * IMAGE_PADDING;
		var imageElementHeight = imageElementWidth * IMAGE_HEIGHT / (double) IMAGE_WIDTH;

		return (int) Math.round(imageElementHeight + 2 * IMAGE_PADDING);
	}

	private void assertZoomLevel(int zoomPercentage) {

		var imageHolder = findImageHolder();
		var expectedWidth = new BigDecimal(getZoomedHolderWidth(zoomPercentage));
		var expectedHeight = new BigDecimal(getZoomedHolderHeight(zoomPercentage));

		var compensator = new ChromeQuirksCompensator(zoomPercentage);
		expectedWidth = compensator.calculateCompensatedExpectedWidth(expectedWidth);
		expectedHeight = compensator.calculateCompensatedExpectedHeight(expectedHeight);

		output.assertCssWidth(expectedWidth + "px", imageHolder);
		output
			.assertSize(//
				expectedWidth.setScale(0, RoundingMode.HALF_UP).intValue(),
				expectedHeight.setScale(0, RoundingMode.HALF_UP).intValue(),
				imageHolder);
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

	/**
	 * Compensates for Google Chrome specific quirks in the size calculation of
	 * scrollable elements.
	 */
	private static class ChromeQuirksCompensator {

		private static final BigDecimal REDUCED_WIDTH_OFFSET = new BigDecimal(3);
		private static final BigDecimal REDUCED_WIDTH_PER_PERCENT = new BigDecimal("0.03");
		private static final BigDecimal REDUCED_HEIGHT_OFFSET = new BigDecimal("0.01");
		private static final BigDecimal REDUCED_HEIGHT_PER_PERCENT = new BigDecimal("0.03");
		private final int zoomPercentage;

		public ChromeQuirksCompensator(int zoomPercentage) {

			this.zoomPercentage = zoomPercentage;
		}

		public BigDecimal calculateCompensatedExpectedWidth(BigDecimal expectedWidth) {

			if (zoomPercentage > 100) {
				var percentsAboveHundred = new BigDecimal(zoomPercentage - 100);
				var reducedWidth = REDUCED_WIDTH_OFFSET.add(percentsAboveHundred.multiply(REDUCED_WIDTH_PER_PERCENT));
				return expectedWidth.subtract(reducedWidth).stripTrailingZeros();
			} else {
				return expectedWidth;
			}
		}

		public BigDecimal calculateCompensatedExpectedHeight(BigDecimal expectedHeight) {

			if (zoomPercentage > 100) {
				var percentsAboveHundred = new BigDecimal(zoomPercentage - 100);
				var reducedWidth = REDUCED_HEIGHT_OFFSET.add(percentsAboveHundred.multiply(REDUCED_HEIGHT_PER_PERCENT));
				return expectedHeight.subtract(reducedWidth).stripTrailingZeros();
			} else {
				return expectedHeight;
			}
		}
	}
}
