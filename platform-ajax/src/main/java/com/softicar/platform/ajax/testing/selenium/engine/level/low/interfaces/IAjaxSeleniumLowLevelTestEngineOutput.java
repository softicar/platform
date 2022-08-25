package com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestRectangle;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalConfirmNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalDialogNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalPromptNodes;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Facilitates the examination of the current content and state of a
 * UI-under-test, and provides assertion methods.
 *
 * @see IAjaxSeleniumLowLevelTestEngine
 * @author Alexander Schmidt
 */
public interface IAjaxSeleniumLowLevelTestEngineOutput {

	AjaxSeleniumTestPoint getLocation(IDomNode node);

	AjaxSeleniumTestSegment getSize(IDomNode node);

	AjaxSeleniumTestRectangle getRectangle(IDomNode node);

	String getText(IDomNode node);

	boolean isDisplayed(IDomNode node);

	boolean isFocused(IDomNode node);

	String getAttributeValue(IDomNode node, String attributeName);

	String getCssAttributeValue(IDomNode node, String attributeName);

	boolean isWaitingDivDisplayed();

	boolean isSessionTimeoutDivDisplayed();

	Optional<String> getFileDownloadSource();

	String getCookie(String cookieName);

	void assertFocused(IDomNode node);

	default void assertFocused(ITestMarker marker) {

		assertFocused(findNodeOrFail(marker));
	}

	Optional<IDomNode> getFocusedNode();

	void assertChildTags(IDomNode node, String...tags);

	Optional<IDomNode> findNode(IDomNode node);

	Optional<IDomNode> findNode(ITestMarker marker);

	default IDomNode findNodeOrFail(ITestMarker marker) {

		return findNode(marker).orElseThrow();
	}

	default void assertNodeWithText(ITestMarker marker, String expectedText) {

		assertNode(//
			marker,
			node -> getText(node).contains(expectedText),
			"The node with marker '%s' did not contain '%s'.".formatted(marker, expectedText));
	}

	default void assertNode(ITestMarker marker, Predicate<IDomNode> assertion, String errorMessage) {

		if (!assertion.test(findNodeOrFail(marker))) {
			throw new AssertionError(errorMessage);
		}
	}

	default void assertNoNode(ITestMarker marker) {

		if (findNode(marker).isPresent()) {
			throw new AssertionError("Unexpectedly encountered a node with marker: %s".formatted(marker));
		}
	}

	IDomModalDialogNodes<IDomNode> findModalDialogOrFail();

	void assertNoModalDialog();

	IDomModalAlertNodes<IDomNode> findModalAlertOrFail();

	IDomModalConfirmNodes<IDomNode> findModalConfirmOrFail();

	IDomModalPromptNodes<IDomNode> findModalPromptOrFail();
}
