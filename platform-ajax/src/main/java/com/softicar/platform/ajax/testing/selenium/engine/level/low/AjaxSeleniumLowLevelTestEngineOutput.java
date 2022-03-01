package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.ajax.testing.selenium.engine.common.IAjaxSeleniumTestEngineConstants;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestRectangle;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineOutput;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.dialog.DomModalDialogMarker;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class AjaxSeleniumLowLevelTestEngineOutput implements IAjaxSeleniumLowLevelTestEngineOutput {

	private final Supplier<WebDriver> webDriverSupplier;
	private final Function<IDomNode, WebElement> webElementResolver;
	private final Supplier<WebElement> sessionTimeoutDivSupplier;

	public AjaxSeleniumLowLevelTestEngineOutput(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.webDriverSupplier = parameters.getWebDriverSupplier();
		this.webElementResolver = parameters.getWebElementResolver();
		this.sessionTimeoutDivSupplier = parameters.getSessionTimeoutDivSupplier();
	}

	@Override
	public AjaxSeleniumTestPoint getLocation(IDomNode node) {

		Point point = webElementResolver.apply(node).getLocation();
		return new AjaxSeleniumTestPoint(point.getX(), point.getY());
	}

	@Override
	public AjaxSeleniumTestSegment getSize(IDomNode node) {

		Dimension dimension = webElementResolver.apply(node).getSize();
		return new AjaxSeleniumTestSegment(dimension.getWidth(), dimension.getHeight());
	}

	@Override
	public AjaxSeleniumTestRectangle getRectangle(IDomNode node) {

		Rectangle rectangle = webElementResolver.apply(node).getRect();
		return new AjaxSeleniumTestRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	@Override
	public String getText(IDomNode node) {

		if (node instanceof IDomTextualInput) {
			return webElementResolver.apply(node).getAttribute("value");
		} else {
			return webElementResolver.apply(node).getText();
		}
	}

	@Override
	public boolean isDisplayed(IDomNode node) {

		return webElementResolver.apply(node).isDisplayed();
	}

	@Override
	public boolean isFocused(IDomNode node) {

		return webElementResolver.apply(node).equals(webDriverSupplier.get().switchTo().activeElement());
	}

	@Override
	public String getAttributeValue(IDomNode node, String attributeName) {

		return webElementResolver.apply(node).getAttribute(attributeName);
	}

	@Override
	public String getCssAttributeValue(IDomNode node, String attributeName) {

		return webElementResolver.apply(node).getCssValue(attributeName);
	}

	@Override
	public boolean isWaitingDivDisplayed() {

		return webDriverSupplier.get().findElement(By.className(AjaxCssClasses.AJAX_WORKING_INDICATOR.getName())).isDisplayed();
	}

	@Override
	public boolean isSessionTimeoutDivDisplayed() {

		return sessionTimeoutDivSupplier.get().isDisplayed();
	}

	@Override
	public Optional<String> getFileDownloadSource() {

		List<WebElement> iframes = webDriverSupplier.get().findElements(By.tagName(IAjaxSeleniumTestEngineConstants.FILE_DOWNLOAD_ELEMENT_TAG));
		if (iframes.size() == 1) {
			return Optional.ofNullable(iframes.get(0).getAttribute("src"));
		} else {
			throw new AssertionError();
		}
	}

	@Override
	public String getCookie(String cookieName) {

		return webDriverSupplier.get().manage().getCookieNamed(cookieName).toString();
	}

	@Override
	public void assertFocused(IDomNode node) {

		String elementId = webElementResolver.apply(node).getAttribute("id");
		String activeElementId = webDriverSupplier.get().switchTo().activeElement().getAttribute("id");
		Assert.assertEquals(elementId, activeElementId);
	}

	@Override
	public Optional<IDomNode> getFocusedNode() {

		String idAttribute = webDriverSupplier.get().switchTo().activeElement().getAttribute("id");
		return Optional.ofNullable(CurrentDomDocument.get().getNode(idAttribute));
	}

	@Override
	public void assertChildTags(IDomNode node, String...tags) {

		List<WebElement> children = webElementResolver.apply(node).findElements(By.xpath("*"));
		Assert.assertEquals(tags.length, children.size());
		for (int i = 0; i < tags.length; i++) {
			Assert.assertEquals(tags[i], children.get(i).getTagName());
		}
	}

	@Override
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

	@Override
	public Optional<IDomNode> findNode(IStaticObject marker) {

		Collection<IDomNode> nodes = CurrentDomDocument.get().getNodesWithMarker(marker);
		if (nodes.isEmpty()) {
			return Optional.empty();
		} else if (nodes.size() == 1) {
			return findNode(nodes.iterator().next());
		} else {
			throw new AssertionError(String.format("Found multiple elements with marker '%s'.", marker));
		}
	}

	@Override
	public IDomModalDialogNodes<IDomNode> findModalDialogOrFail() {

		return new DomModalDialogNodes<>(this::findNodeOrFail);
	}

	@Override
	public void assertNoModalDialog() {

		assertNoNode(DomModalDialogMarker.FRAME);
		assertNoNode(DomModalDialogMarker.POPUP);
		assertNoNode(DomModalDialogMarker.CONTENT);
		assertNoNode(DomModalDialogMarker.BACKDROP);
	}

	@Override
	public IDomModalAlertNodes<IDomNode> findModalAlertOrFail() {

		return new DomModalAlertNodes<>(this::findNodeOrFail);
	}

	@Override
	public IDomModalConfirmNodes<IDomNode> findModalConfirmOrFail() {

		return new DomModalConfirmNodes<>(this::findNodeOrFail);
	}

	@Override
	public IDomModalPromptNodes<IDomNode> findModalPromptOrFail() {

		return new DomModalPromptNodes<>(this::findNodeOrFail);
	}
}
