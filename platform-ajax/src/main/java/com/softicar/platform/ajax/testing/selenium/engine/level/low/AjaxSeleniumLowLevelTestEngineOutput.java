package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import static org.junit.Assert.assertEquals;
import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.ajax.testing.selenium.engine.common.IAjaxSeleniumTestEngineConstants;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestRectangle;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.dialog.testing.DomModalAlertNodes;
import com.softicar.platform.dom.elements.dialog.testing.DomModalConfirmNodes;
import com.softicar.platform.dom.elements.dialog.testing.DomModalDialogNodes;
import com.softicar.platform.dom.elements.dialog.testing.DomModalPromptNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalConfirmNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalDialogNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalPromptNodes;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Facilitates the examination of the current content and state of a
 * UI-under-test, and provides assertion methods.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumLowLevelTestEngineOutput {

	private final Supplier<WebDriver> webDriverSupplier;
	private final Function<IDomNode, WebElement> webElementResolver;
	private final Supplier<WebElement> sessionTimeoutDivSupplier;

	AjaxSeleniumLowLevelTestEngineOutput(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.webDriverSupplier = parameters.getWebDriverSupplier();
		this.webElementResolver = parameters.getWebElementResolver();
		this.sessionTimeoutDivSupplier = parameters.getSessionTimeoutDivSupplier();
	}

	public AjaxSeleniumTestPoint getLocation(IDomNode node) {

		Point point = webElementResolver.apply(node).getLocation();
		return new AjaxSeleniumTestPoint(point.getX(), point.getY());
	}

	public AjaxSeleniumTestSegment getSize(IDomNode node) {

		Dimension dimension = webElementResolver.apply(node).getSize();
		return new AjaxSeleniumTestSegment(dimension.getWidth(), dimension.getHeight());
	}

	public void assertSize(int expectedWidth, int expectedHeight, IDomNode node) {

		var dimension = getSize(node);
		assertEquals("width", expectedWidth, dimension.getWidth());
		assertEquals("height", expectedHeight, dimension.getHeight());
	}

	public AjaxSeleniumTestRectangle getRectangle(IDomNode node) {

		Rectangle rectangle = webElementResolver.apply(node).getRect();
		return new AjaxSeleniumTestRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public String getText(IDomNode node) {

		if (node instanceof IDomTextualInput) {
			return webElementResolver.apply(node).getAttribute("value");
		} else {
			return webElementResolver.apply(node).getText();
		}
	}

	public boolean isDisplayed(IDomNode node) {

		return webElementResolver.apply(node).isDisplayed();
	}

	public boolean isFocused(IDomNode node) {

		return webElementResolver.apply(node).equals(webDriverSupplier.get().switchTo().activeElement());
	}

	public String getAttributeValue(IDomNode node, String attributeName) {

		return webElementResolver.apply(node).getAttribute(attributeName);
	}

	public String getCssAttributeValue(IDomNode node, String attributeName) {

		return webElementResolver.apply(node).getCssValue(attributeName);
	}

	public boolean isWaitingDivDisplayed() {

		return webDriverSupplier.get().findElement(By.className(AjaxCssClasses.AJAX_WORKING_INDICATOR.getName())).isDisplayed();
	}

	public boolean isSessionTimeoutDivDisplayed() {

		return sessionTimeoutDivSupplier.get().isDisplayed();
	}

	public Optional<String> getFileDownloadSource() {

		List<WebElement> iframes = webDriverSupplier.get().findElements(By.tagName(IAjaxSeleniumTestEngineConstants.FILE_DOWNLOAD_ELEMENT_TAG));
		if (iframes.size() == 1) {
			return Optional.ofNullable(iframes.get(0).getAttribute("src"));
		} else {
			throw new AssertionError();
		}
	}

	public String getCookie(String cookieName) {

		return webDriverSupplier.get().manage().getCookieNamed(cookieName).toString();
	}

	public void assertFocused(IDomNode node) {

		String elementId = webElementResolver.apply(node).getAttribute("id");
		String activeElementId = webDriverSupplier.get().switchTo().activeElement().getAttribute("id");
		Assert.assertEquals(elementId, activeElementId);
	}

	public void assertFocused(ITestMarker marker) {

		assertFocused(findNodeOrFail(marker));
	}

	public Optional<IDomNode> getFocusedNode() {

		String idAttribute = webDriverSupplier.get().switchTo().activeElement().getAttribute("id");
		return Optional.ofNullable(CurrentDomDocument.get().getNode(idAttribute));
	}

	public void assertChildTags(IDomNode node, String...tags) {

		List<WebElement> children = webElementResolver.apply(node).findElements(By.xpath("*"));
		Assert.assertEquals(tags.length, children.size());
		for (int i = 0; i < tags.length; i++) {
			Assert.assertEquals(tags[i], children.get(i).getTagName());
		}
	}

	public Optional<IDomNode> findNode(IDomNode node) {

		String nodeId = node.getNodeIdString();
		List<WebElement> elements = webDriverSupplier.get().findElements(By.id(nodeId));
		if (elements.isEmpty()) {
			return Optional.empty();
		} else if (elements.size() == 1) {
			return Optional.of(node);
		} else {
			throw new AssertionError(String.format("Found multiple elements with ID '%s'.", nodeId));
		}
	}

	public Optional<IDomNode> findNode(ITestMarker marker) {

		Collection<IDomNode> nodes = CurrentDomDocument.get().getNodesWithMarker(marker);
		if (nodes.isEmpty()) {
			return Optional.empty();
		} else if (nodes.size() == 1) {
			return findNode(nodes.iterator().next());
		} else {
			throw new AssertionError(String.format("Found multiple elements with marker '%s'.", marker));
		}
	}

	public IDomNode findNodeOrFail(ITestMarker marker) {

		return findNode(marker).orElseThrow();
	}

	public void assertNodeWithText(ITestMarker marker, String expectedText) {

		assertNode(//
			marker,
			node -> getText(node).contains(expectedText),
			"The node with marker '%s' did not contain '%s'.".formatted(marker, expectedText));
	}

	public void assertNode(ITestMarker marker, Predicate<IDomNode> assertion, String errorMessage) {

		if (!assertion.test(findNodeOrFail(marker))) {
			throw new AssertionError(errorMessage);
		}
	}

	public void assertNoNode(ITestMarker marker) {

		if (findNode(marker).isPresent()) {
			throw new AssertionError("Unexpectedly encountered a node with marker: %s".formatted(marker));
		}
	}

	public IDomModalDialogNodes<IDomNode> findModalDialogOrFail() {

		return new DomModalDialogNodes<>(this::findNodeOrFail);
	}

	public void assertNoModalDialog() {

		assertNoNode(DomTestMarker.MODAL_DIALOG_FRAME);
		assertNoNode(DomTestMarker.MODAL_DIALOG_POPUP);
		assertNoNode(DomTestMarker.MODAL_DIALOG_CONTENT);
		assertNoNode(DomTestMarker.POPUP_BACKDROP);
	}

	public IDomModalAlertNodes<IDomNode> findModalAlertOrFail() {

		return new DomModalAlertNodes<>(this::findNodeOrFail);
	}

	public IDomModalConfirmNodes<IDomNode> findModalConfirmOrFail() {

		return new DomModalConfirmNodes<>(this::findNodeOrFail);
	}

	public IDomModalPromptNodes<IDomNode> findModalPromptOrFail() {

		return new DomModalPromptNodes<>(this::findNodeOrFail);
	}

	// ------------------------------ CSS assert methods ------------------------------ //

	public void assertCssClasses(Collection<ICssClass> expectedClasses, IDomNode node) {

		var expectedValue = expectedClasses//
			.stream()
			.map(ICssClass::getName)
			.sorted()
			.collect(Collectors.joining(" "));
		assertEquals(expectedValue, getAttributeValue(node, "class"));
	}

	public void assertCssMaxWidth(String expectedMaxWidth, IDomNode node) {

		assertEquals(expectedMaxWidth, getCssAttributeValue(node, "max-width"));
	}

	public void assertCssTransform(String expectedTransform, IDomNode node) {

		assertEquals(expectedTransform, getCssAttributeValue(node, "transform"));
	}

	public void assertCssWidth(String expectedWidth, IDomNode node) {

		assertEquals(expectedWidth, getCssAttributeValue(node, "width"));
	}
}
