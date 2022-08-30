package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestRectangle;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Modifier;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalAlertNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalConfirmNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalDialogNodes;
import com.softicar.platform.dom.elements.dialog.testing.IDomModalPromptNodes;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Provides convenience methods for unit tests that use
 * {@link AjaxSeleniumLowLevelTestEngine}.
 *
 * @author Alexander Schmidt
 */
public interface IAjaxSeleniumLowLevelTestEngineMethods {

	public static final int MOUSE_WHEEL_ROTATION_STEP_PIXELS = 200;

	/**
	 * The {@link AjaxSeleniumLowLevelTestEngine} that contains the methods for
	 * which this interface serves as a wrapper.
	 *
	 * @return the {@link AjaxSeleniumLowLevelTestEngine} (never <i>null</i>)
	 */
	AjaxSeleniumLowLevelTestEngine getTestEngine();

	// -------------------------------- setup -------------------------------- //

	/**
	 * Creates an {@link IDomNode} using the given factory, and initializes it
	 * as the UI-under-test.
	 *
	 * @param <T>
	 *            the exact type of the {@link IDomNode}
	 * @param factory
	 *            the factory that creates the {@link IDomNode}
	 * @return the initialized {@link IDomNode} (never <i>null</i>)
	 */
	default <T extends IDomNode> T openTestNode(Supplier<T> factory) {

		return getTestEngine().openTestNode(factory);
	}

	/**
	 * Creates an {@link IDomNode} using the given factory, and initializes it
	 * as the UI-under-test.
	 *
	 * @param <T>
	 *            the exact type of the {@link IDomNode}
	 * @param factory
	 *            the factory that creates the {@link IDomNode}
	 * @return the initialized {@link IDomNode} (never <i>null</i>)
	 */
	default <T extends IDomNode> T openTestNode(Function<IAjaxDocument, T> factory) {

		return getTestEngine().openTestNode(factory);
	}

	// -------------------------------- screenshots -------------------------------- //

	/**
	 * Takes a screenshot of the current browser window content.
	 * <p>
	 * For the screenshots to be saved to the temporary directory, either
	 * {@link AjaxSeleniumTestProperties#EXECUTION_SCREENSHOT_ON_FINISHED}
	 * and/or {@link AjaxSeleniumTestProperties#EXECUTION_SCREENSHOT_ON_FAILURE}
	 * must be <i>true</i>.
	 * <p>
	 * The given file name should <b>not</b> have an extension.
	 *
	 * @param fileName
	 *            the screenshot file name (never <i>null</i>)
	 */
	default void takeScreenshot(String fileName) {

		getTestEngine().takeScreenshot(fileName);
	}

	// -------------------------------- input -------------------------------- //

	/**
	 * Clicks the given {@link IDomNode}.
	 *
	 * @param node
	 *            the {@link IDomNode} to click (never <i>null</i>)
	 */
	default void click(IDomNode node) {

		getTestEngine().getInput().click(node);
	}

	/**
	 * Clicks the {@link IDomNode} with given {@link ITestMarker}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} of the {@link IDomNode} to click
	 *            (never <i>null</i>)
	 * @throws NoSuchElementException
	 *             if the document does not contain a node with the given
	 *             {@link ITestMarker}
	 */
	default void click(ITestMarker marker) {

		click(findNodeOrFail(marker));
	}

	/**
	 * Clicks the given {@link IDomNode}, while holding down the given
	 * {@link Modifier} keys.
	 * <p>
	 * The {@link Modifier} keys will be released after the click.
	 *
	 * @param node
	 *            the {@link IDomNode} to click (never <i>null</i>)
	 * @param modifiers
	 *            the {@link Modifier} keys to hold while clicking
	 */
	default void click(IDomNode node, Modifier...modifiers) {

		getTestEngine().getInput().click(node, modifiers);
	}

	/**
	 * Clicks at the specified position.
	 * <p>
	 * The given {@link IDomNode} provides a reference position. The coordinates
	 * for the click are derived from the coordinates of the top-left corner of
	 * the given {@link IDomNode}, combined with the given offsets.
	 *
	 * @param node
	 *            the {@link IDomNode} that provides the reference position
	 *            (never <i>null</i>)
	 * @param xOffset
	 *            the horizontal offset to be added to the X coordinate of the
	 *            top-left corner of the given {@link IDomNode}
	 * @param yOffset
	 *            the vertical offset to be added to the Y coordinate of the
	 *            top-left corner of the given {@link IDomNode}
	 */
	default void clickAt(IDomNode node, int xOffset, int yOffset) {

		getTestEngine().getInput().clickAt(node, xOffset, yOffset);
	}

	/**
	 * Clicks the HTML body node.
	 */
	default void clickBodyNode() {

		getTestEngine().getInput().clickBodyNode();
	}

	/**
	 * Clicks the element which displays the session timeout message.
	 *
	 * @throws RuntimeException
	 *             if the element is not displayed
	 */
	default void clickSessionTimeoutDivReturnToLoginButton() {

		getTestEngine().getInput().clickSessionTimeoutDivReturnToLoginButton();
	}

	/**
	 * Double-clicks at the specified position.
	 * <p>
	 * The given {@link IDomNode} provides a reference position. The coordinates
	 * for the double-click are derived from the coordinates of the top-left
	 * corner of the given {@link IDomNode}, combined with the given offsets.
	 *
	 * @param node
	 *            the {@link IDomNode} that provides the reference position
	 *            (never <i>null</i>)
	 * @param xOffset
	 *            the horizontal offset to be added to the X coordinate of the
	 *            top-left corner of the given {@link IDomNode}
	 * @param yOffset
	 *            the vertical offset to be added to the Y coordinate of the
	 *            top-left corner of the given {@link IDomNode}
	 */
	default void doubleClickAt(IDomNode node, int xOffset, int yOffset) {

		getTestEngine().getInput().doubleClickAt(node, xOffset, yOffset);
	}

	/**
	 * Presses and holds the left mouse button.
	 * <p>
	 * A corresponding, subsequent call to {@link #mouseUpUnsafe()} must be
	 * guaranteed.
	 * <p>
	 * <b>WARNING:</b> Unsafe by design: In the Selenium implementation, this
	 * will leave the web driver in an inconsistent state if the mouse button is
	 * not released afterwards.
	 *
	 * @see #mouseUpUnsafe()
	 */
	default void mouseDownUnsafe(IDomNode node) {

		getTestEngine().getInput().mouseDownUnsafe(node);
	}

	/**
	 * Releases the left mouse button.
	 * <p>
	 * A corresponding, previous call to {@link #mouseDownUnsafe(IDomNode)} must
	 * be guaranteed.
	 * <p>
	 * <b>WARNING:</b> Unsafe by design: In the Selenium implementation, this
	 * will leave the web driver in an inconsistent state if the mouse button
	 * was not held down before.
	 *
	 * @see #mouseDownUnsafe(IDomNode)
	 */
	default void mouseUpUnsafe() {

		getTestEngine().getInput().mouseUpUnsafe();
	}

	/**
	 * Rotates the mouse wheel downwards.
	 *
	 * @param node
	 *            the {@link IDomNode} to rotate the mouse wheel on (never
	 *            <i>null</i>)
	 */
	default void mouseWheelDown(IDomNode node) {

		simulateWheel(node, MOUSE_WHEEL_ROTATION_STEP_PIXELS);
	}

	/**
	 * Rotates the mouse wheel upwards.
	 *
	 * @param node
	 *            the {@link IDomNode} to rotate the mouse wheel on (never
	 *            <i>null</i>)
	 */
	default void mouseWheelUp(IDomNode node) {

		simulateWheel(node, -MOUSE_WHEEL_ROTATION_STEP_PIXELS);
	}

	/**
	 * Moves the mouse cursor by the specified offsets, from its current
	 * position.
	 *
	 * @param xOffset
	 *            the horizontal offset
	 * @param yOffset
	 *            the vertical offset
	 */
	default void moveCursorBy(int xOffset, int yOffset) {

		getTestEngine().getInput().moveCursorBy(xOffset, yOffset);
	}

	/**
	 * Performs a drag-and-drop operation on the given {@link IDomNode}, using
	 * the given offsets.
	 *
	 * @param target
	 *            the {@link IDomNode} which should be dragged (never
	 *            <i>null</i>)
	 * @param xOffset
	 *            the horizontal offset by which the given {@link IDomNode}
	 *            should be dragged
	 * @param yOffset
	 *            the vertical offset by which the given {@link IDomNode} should
	 *            be dragged
	 */
	default void dragAndDrop(IDomNode target, int xOffset, int yOffset) {

		getTestEngine().getInput().dragAndDrop(target, xOffset, yOffset);
	}

	/**
	 * Sends the given {@link Key}s.
	 *
	 * @param keys
	 *            the {@link Key}s to send (never <i>null</i>)
	 */
	default void send(Key...keys) {

		getTestEngine().getInput().send(keys);
	}

	/**
	 * Sends the keys represented by the given {@link String}
	 *
	 * @param keys
	 *            the keys to send (never <i>null</i>)
	 */
	default void send(String keys) {

		getTestEngine().getInput().send(keys);
	}

	/**
	 * Sends the given {@link Key}s to the given {@link IDomNode}.
	 * <p>
	 * The {@link IDomNode} is not necessarily an HTML input element.
	 *
	 * @param target
	 *            the {@link IDomNode} to which the keys shall be sent (never
	 *            <i>null</i>)
	 * @param keys
	 *            the {@link Key}s to send (never <i>null</i>)
	 */
	default void send(IDomNode target, Key...keys) {

		getTestEngine().getInput().send(target, keys);
	}

	/**
	 * Sends the given {@link Key}s to the {@link IDomNode} with the given
	 * {@link ITestMarker}.
	 * <p>
	 * The {@link IDomNode} is not necessarily an HTML input element.
	 *
	 * @param marker
	 *            the {@link ITestMarker} of the {@link IDomNode} to which the
	 *            keys shall be sent (never <i>null</i>)
	 * @param keys
	 *            the {@link Key}s to send (never <i>null</i>)
	 */
	default void send(ITestMarker marker, Key...keys) {

		send(findNodeOrFail(marker), keys);
	}

	/**
	 * Sends the keys represented by the given {@link String} to the given
	 * {@link IDomNode}.
	 * <p>
	 * The {@link IDomNode} is not necessarily an HTML input element.
	 *
	 * @param target
	 *            the {@link IDomNode} to which the keys shall be sent (never
	 *            <i>null</i>)
	 * @param keys
	 *            the keys to send (never <i>null</i>)
	 */
	default void send(IDomNode target, String keys) {

		getTestEngine().getInput().send(target, keys);
	}

	/**
	 * Sends the keys represented by the given {@link String} to the
	 * {@link IDomNode} with the given {@link ITestMarker}.
	 * <p>
	 * The {@link IDomNode} is not necessarily an HTML input element.
	 *
	 * @param marker
	 *            the {@link ITestMarker} of the {@link IDomNode} to which the
	 *            keys shall be sent (never <i>null</i>)
	 * @param keys
	 *            the keys to send (never <i>null</i>)
	 * @throws NoSuchElementException
	 *             if the document does not contain a node with the given
	 *             {@link ITestMarker}
	 */
	default void send(ITestMarker marker, String keys) {

		send(findNodeOrFail(marker), keys);
	}

	/**
	 * Clears the content of the given {@link IDomTextualInput}.
	 * <p>
	 * FIXME This is probably bad design. The current Selenium implementation
	 * would not fire events (and could hence be considered bugged). If we can't
	 * fix it, this method should be considered dangerous and therefore be
	 * removed. Possible alternative: repeated BACKSPACE presses.
	 *
	 * @param input
	 *            the {@link IDomTextualInput} to clear (never <i>null</i>)
	 */
	default void clear(IDomTextualInput input) {

		getTestEngine().getInput().clear(input);
	}

	// -------------------- waiter -------------------- //

	default void waitUntil(Supplier<Boolean> condition) {

		getTestEngine().waitUntil(condition);
	}

	default void waitUntil(Supplier<Boolean> condition, Duration duration) {

		getTestEngine().waitUntil(condition, duration);
	}

	default <T> void waitUntil(T object, Predicate<T> predicate) {

		getTestEngine().waitUntil(object, predicate);
	}

	default <T> void waitUntil(T object, Predicate<T> predicate, Duration duration) {

		getTestEngine().waitUntil(object, predicate, duration);
	}

	default void waitWhile(Supplier<Boolean> condition) {

		getTestEngine().waitWhile(condition);
	}

	default void waitWhile(Supplier<Boolean> condition, Duration duration) {

		getTestEngine().waitWhile(condition, duration);
	}

	default <T> void waitWhile(T object, Predicate<T> predicate) {

		getTestEngine().waitWhile(object, predicate);
	}

	default <T> void waitWhile(T object, Predicate<T> predicate, Duration duration) {

		getTestEngine().waitWhile(object, predicate, duration);
	}

	default void waitForServer() {

		getTestEngine().waitForServer();
	}

	default void waitForServer(Duration duration) {

		getTestEngine().waitForServer(duration);
	}

	// -------------------- output -------------------- //

	default AjaxSeleniumTestPoint getLocation(IDomNode node) {

		return getTestEngine().getOutput().getLocation(node);
	}

	default AjaxSeleniumTestSegment getSize(IDomNode node) {

		return getTestEngine().getOutput().getSize(node);
	}

	default AjaxSeleniumTestRectangle getRectangle(IDomNode node) {

		return getTestEngine().getOutput().getRectangle(node);
	}

	default String getText(IDomNode node) {

		return getTestEngine().getOutput().getText(node);
	}

	default boolean isDisplayed(IDomNode node) {

		return getTestEngine().getOutput().isDisplayed(node);
	}

	default boolean isFocused(IDomNode node) {

		return getTestEngine().getOutput().isFocused(node);
	}

	default String getAttributeValue(IDomNode node, String attributeName) {

		return getTestEngine().getOutput().getAttributeValue(node, attributeName);
	}

	default String getCssAttributeValue(IDomNode node, String attributeName) {

		return getTestEngine().getOutput().getCssAttributeValue(node, attributeName);
	}

	default boolean isWaitingDivDisplayed() {

		return getTestEngine().getOutput().isWaitingDivDisplayed();
	}

	default boolean isSessionTimeoutDivDisplayed() {

		return getTestEngine().getOutput().isSessionTimeoutDivDisplayed();
	}

	default Optional<String> getFileDownloadSource() {

		return getTestEngine().getOutput().getFileDownloadSource();
	}

	/**
	 * Asserts that the given {@link IDomNode} is focused.
	 *
	 * @param node
	 *            the {@link IDomNode} which shall be checked for being focused
	 *            (never <i>null</i>)
	 */
	default void assertFocused(IDomNode node) {

		getTestEngine().getOutput().assertFocused(node);
	}

	/**
	 * Asserts that the {@link IDomNode} with the given {@link ITestMarker} is
	 * focused.
	 *
	 * @param marker
	 *            the {@link ITestMarker} of the {@link IDomNode} which shall be
	 *            checked for being focused (never <i>null</i>)
	 */
	default void assertFocused(ITestMarker marker) {

		getTestEngine().getOutput().assertFocused(marker);
	}

	/**
	 * Returns the {@link IDomNode} that is currently focused.
	 *
	 * @return the focused {@link IDomNode}
	 */
	default Optional<IDomNode> getFocusedNode() {

		return getTestEngine().getOutput().getFocusedNode();
	}

	/**
	 * Asserts that the children of the given {@link IDomNode} correspond to the
	 * given tags.
	 * <p>
	 * That is:
	 * <ul>
	 * <li>The number of children of the given {@link IDomNode} must match the
	 * number of given tags.</li>
	 * <li>The children of the given {@link IDomNode} must possess the given
	 * tags, in the given sequence.</li>
	 * </ul>
	 *
	 * @param node
	 *            the {@link IDomNode} to check for children with the given tags
	 *            (never <i>null</i>)
	 * @param tags
	 *            the tags of the nodes which are expected as children of the
	 *            given {@link IDomNode} (never <i>null</i>; may be empty)
	 */
	default void assertChildTags(IDomNode node, String...tags) {

		getTestEngine().getOutput().assertChildTags(node, tags);
	}

	/**
	 * Checks whether the given {@link IDomNode} is actually displayed in the
	 * rendered document, and returns it as an {@link Optional}.
	 * <p>
	 * If the document does not contain a corresponding node,
	 * {@link Optional#empty()} is returned.
	 *
	 * @param node
	 *            the {@link IDomNode} to check (never <i>null</i>)
	 * @return the given {@link IDomNode} as an {@link Optional}
	 * @throws AssertionError
	 *             if the document contains more than one matching node
	 */
	default Optional<IDomNode> findNode(IDomNode node) {

		return getTestEngine().getOutput().findNode(node);
	}

	/**
	 * Checks whether the rendered document contains an {@link IDomNode} with
	 * the given {@link ITestMarker}, and returns the found {@link IDomNode} as
	 * an {@link Optional}, if any.
	 * <p>
	 * If the document does not contain a corresponding node,
	 * {@link Optional#empty()} is returned.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to check (never <i>null</i>)
	 * @return the found {@link IDomNode} as an {@link Optional}
	 * @throws AssertionError
	 *             if the document contains more than one matching node
	 */
	default Optional<IDomNode> findNode(ITestMarker marker) {

		return getTestEngine().getOutput().findNode(marker);
	}

	/**
	 * Checks whether the rendered document contains an {@link IDomNode} with
	 * the given {@link ITestMarker}, and returns the found {@link IDomNode}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to check (never <i>null</i>)
	 * @return the found {@link IDomNode} (never <i>null</i>)
	 * @throws AssertionError
	 *             if the document contains more than one matching node
	 * @throws NoSuchElementException
	 *             if the document does not contain a node with the given
	 *             {@link ITestMarker}
	 */
	default IDomNode findNodeOrFail(ITestMarker marker) {

		return getTestEngine().getOutput().findNodeOrFail(marker);
	}

	/**
	 * Asserts that the rendered document contains an {@link IDomNode} with the
	 * given {@link ITestMarker} , and that the sub-tree spanned by this node
	 * contains the expected text.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to check (never <i>null</i>)
	 * @param expectedText
	 *            the text that is expected to be present in the node or its
	 *            children (never <i>null</i>)
	 * @throws NoSuchElementException
	 *             if the document does not contain a node with the given
	 *             {@link ITestMarker}
	 * @throws AssertionError
	 *             if the node with the marker does not contain the expected
	 *             text
	 */
	default void assertNodeWithText(ITestMarker marker, String expectedText) {

		getTestEngine().getOutput().assertNodeWithText(marker, expectedText);
	}

	/**
	 * Asserts that the rendered document contains an {@link IDomNode} with the
	 * given {@link ITestMarker} , and that the given assertion
	 * {@link Predicate} evaluates to <i>true</i> for this {@link IDomNode}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to check (never <i>null</i>)
	 * @param assertion
	 *            the assertion {@link Predicate} to check (never <i>null</i>)
	 * @param errorMessage
	 *            the error message to be displayed if the assertion
	 *            {@link Predicate} evaluates to <i>false</i> (never
	 *            <i>null</i>)
	 * @throws NoSuchElementException
	 *             if the document does not contain a node with the given
	 *             {@link ITestMarker}
	 * @throws AssertionError
	 *             if the assertion {@link Predicate} evaluates to <i>false</i>
	 */
	default void assertNode(ITestMarker marker, Predicate<IDomNode> assertion, String errorMessage) {

		getTestEngine().getOutput().assertNode(marker, assertion, errorMessage);
	}

	/**
	 * Asserts that the rendered document does not contain an {@link IDomNode}
	 * with the given {@link ITestMarker}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to check (never <i>null</i>)
	 * @throws AssertionError
	 *             if the document contains one or several matching nodes
	 */
	default void assertNoNode(ITestMarker marker) {

		getTestEngine().getOutput().assertNoNode(marker);
	}

	/**
	 * Asserts that a modal dialog is displayed in the rendered document.
	 *
	 * @return an {@link IDomModalDialogNodes} which provides access to the
	 *         nodes of the modal dialog (never <i>null</i>)
	 * @throws AssertionError
	 *             if no modal dialog is displayed
	 */
	default IDomModalDialogNodes<IDomNode> findModalDialogOrFail() {

		return getTestEngine().getOutput().findModalDialogOrFail();
	}

	/**
	 * Asserts that no modal dialog is displayed in the rendered document.
	 *
	 * @throws AssertionError
	 *             if a modal dialog is displayed
	 */
	default void assertNoModalDialog() {

		getTestEngine().getOutput().assertNoModalDialog();
	}

	/**
	 * Asserts that a modal alert dialog is displayed in the rendered document.
	 *
	 * @return an {@link IDomModalAlertNodes} instance which provides access to
	 *         the nodes of the modal alert dialog (never <i>null</i>)
	 * @throws AssertionError
	 *             if no modal alert dialog is displayed
	 */
	default IDomModalAlertNodes<IDomNode> findModalAlertOrFail() {

		return getTestEngine().getOutput().findModalAlertOrFail();
	}

	/**
	 * Asserts that a modal confirm dialog is displayed in the rendered
	 * document.
	 *
	 * @return an {@link IDomModalConfirmNodes} instance which provides access
	 *         to the nodes of the modal confirm dialog (never <i>null</i>)
	 * @throws AssertionError
	 *             if no modal confirm dialog is displayed
	 */
	default IDomModalConfirmNodes<IDomNode> findModalConfirmOrFail() {

		return getTestEngine().getOutput().findModalConfirmOrFail();
	}

	/**
	 * Asserts that a modal prompt dialog is displayed in the rendered document.
	 *
	 * @return an {@link IDomModalPromptNodes} instance which provides access to
	 *         the nodes of the modal prompt dialog (never <i>null</i>)
	 * @throws AssertionError
	 *             if no modal prompt dialog is displayed
	 */
	default IDomModalPromptNodes<IDomNode> findModalPromptOrFail() {

		return getTestEngine().getOutput().findModalPromptOrFail();
	}

	// -------------------- viewport -------------------- //

	default AjaxSeleniumTestSegment getViewportSize() {

		return getTestEngine().getViewport().getViewportSize();
	}

	default void setViewportSize(int width, int height) {

		getTestEngine().getViewport().setViewportSize(width, height);
	}

	default void scrollTo(int x, int y) {

		getTestEngine().getViewport().scrollTo(x, y);
	}

	// -------------------- event simulator -------------------- //

	default void simulateKeyDown(IDomNode target, int keyCode) {

		getTestEngine().getEventSimulator().simulateKeyDown(target, keyCode);
	}

	default void simulateKeyUp(IDomNode target, int keyCode) {

		getTestEngine().getEventSimulator().simulateKeyUp(target, keyCode);
	}

	default void simulateChange(IDomNode target) {

		getTestEngine().getEventSimulator().simulateChange(target);
	}

	default void simulateInput(IDomNode target) {

		getTestEngine().getEventSimulator().simulateInput(target);
	}

	/**
	 * Simulates a vertical mouse wheel rotation by the given number of pixels.
	 * <p>
	 * A positive number corresponds to "wheel down", while a negative number
	 * corresponds to "wheel up".
	 *
	 * @param node
	 *            the target {@link IDomNode} (never <i>null</i>)
	 * @param deltaY
	 *            the number of pixels
	 */
	default void simulateWheel(IDomNode node, int deltaY) {

		getTestEngine().getEventSimulator().simulateWheel(node, deltaY);
	}
}
