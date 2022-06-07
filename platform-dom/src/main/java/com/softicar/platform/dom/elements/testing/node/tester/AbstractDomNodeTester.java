package com.softicar.platform.dom.elements.testing.node.tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.Tokenizer;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.input.IDomInput;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.text.IDomTextNode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;

public abstract class AbstractDomNodeTester<N extends IDomNode> implements IDomNodeTesterFindMethods {

	private final IDomTestExecutionEngine engine;
	protected final N node;

	public AbstractDomNodeTester(IDomTestExecutionEngine engine, N node) {

		this.engine = engine;
		this.node = node;
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Override
	public N getNode() {

		return node;
	}

	// ------------------------------ input ------------------------------ //

	public AbstractDomNodeTester<N> setInputValue(IStaticObject marker, String value) {

		findInput(marker).setInputValue(value);
		return this;
	}

	/**
	 * Searches for a node with the given marker and then applies the given time
	 * string to the respective inputs for hours, minutes and seconds.
	 *
	 * @param marker
	 *            the test marker
	 * @param timeString
	 *            the time string in the format <hours>:<minutes>:<seconds>
	 *            (never <i>null</i>)
	 * @return this
	 */
	public AbstractDomNodeTester<N> setTimeInputValue(IStaticObject marker, String timeString) {

		List<String> elements = new Tokenizer(':', '\\').tokenize(timeString);
		if (elements.size() != 3) {
			throw new IllegalArgumentException("Expected a time string in the form '<hours>:<minutes>:<seconds>' but got: %s".formatted(timeString));
		}
		findNode(marker)//
			.setInputValue(DomTestMarker.HOURS_INPUT, elements.get(0))
			.setInputValue(DomTestMarker.MINUTES_INPUT, elements.get(1))
			.setInputValue(DomTestMarker.SECONDS_INPUT, elements.get(2));
		return this;
	}

	/**
	 * Searches for a node with the given marker and then applies the given
	 * values to the respective inputs for day, hour, minutes and seconds.
	 *
	 * @param marker
	 *            the test marker
	 * @param dayString
	 *            the literal input value for the day input (never <i>null</i>)
	 * @param timeString
	 *            the time string in the format <hours>:<minutes>:<seconds>
	 *            (never <i>null</i>)
	 * @return this
	 */
	public AbstractDomNodeTester<N> setDayTimeInputValue(IStaticObject marker, String dayString, String timeString) {

		findNode(marker).setInputValue(DomTestMarker.DAY_INPUT, dayString);
		setTimeInputValue(marker, timeString);
		return this;
	}

	public AbstractDomNodeTester<N> setInputValue(String text) {

		var input = findNodes()//
			.withType(IDomTextualInput.class)
			.assertOne()
			.assertType(IDomTextualInput.class);
		engine.setInputValue(input, text);
		return this;
	}

	public String getInputValue() {

		var input = findNodes()//
			.withType(IDomTextualInput.class)
			.assertOne()
			.assertType(IDomTextualInput.class);
		return engine.getInputValue(input);
	}

	public AbstractDomNodeTester<N> assertInputValue(String expectedValue) {

		String inputValue = getInputValue();
		if (expectedValue != null) {
			if (!expectedValue.equals(inputValue)) {
				throw new AssertionError(String.format("Input value '%s' does not match expected value '%s'.", inputValue, expectedValue));
			}
		} else {
			if (inputValue != null) {
				throw new AssertionError(String.format("Expected null input value but encountered '%s'.", inputValue));
			}
		}
		return this;
	}

	// ------------------------------ events ------------------------------ //

	public AbstractDomNodeTester<N> sendEvent(DomEventType type) {

		Assert.assertFalse("Trying to send %s event to disabled node.".formatted(type), isNodeDisabled());
		engine.sendEvent(node, type);
		return this;
	}

	public AbstractDomNodeTester<N> click() {

		return sendEvent(DomEventType.CLICK);
	}

	public AbstractDomNodeTester<N> doubleClick() {

		return sendEvent(DomEventType.DBLCLICK);
	}

	public AbstractDomNodeTester<N> contextClick() {

		return sendEvent(DomEventType.CONTEXTMENU);
	}

	public AbstractDomNodeTester<N> rightClick() {

		return contextClick();
	}

	private boolean isNodeDisabled() {

		return CastUtils//
			.tryCast(node, IDomInput.class)
			.map(IDomInput::isDisabled)
			.orElse(false);
	}

	// ------------------------------ click child ------------------------------ //

	public void clickNode(IStaticObject marker) {

		findNodes()//
			.withInstanceOf(IDomClickEventHandler.class)
			.withMarker(marker)
			.assertOne()
			.click();
	}

	public void clickNode(IDisplayString text) {

		IDomNodeIterable<IDomNode> nodes = findNodes()//
			.withInstanceOf(IDomClickEventHandler.class)
			.withText(text);
		if (nodes.size() == 1) {
			nodes.first().click();
		} else if (nodes.size() == 0) {
			findNodes()//
				.withInstanceOf(IDomClickEventHandler.class)//
				.startingWithText(text)
				.assertOne()
				.click();
		} else {
			throw new AssertionError(String.format("Found more than one node with text '%s'", text));
		}
	}

	// ------------------------------ asserts ------------------------------ //

	public <T extends N> T assertType(Class<T> concreteType) {

		if (concreteType.isInstance(node)) {
			return concreteType.cast(node);
		} else {
			throw new AssertionError(
				String
					.format(//
						"Node of type %s is not instance of %s.",
						node.getClass().getCanonicalName(),
						concreteType.getCanonicalName()));
		}
	}

	public AbstractDomNodeTester<N> assertText(IDisplayString expectedText) {

		return assertText(expectedText.toString());
	}

	public AbstractDomNodeTester<N> assertText(String expectedText) {

		assertEquals(expectedText, getAllTextInDocument(""));
		return this;
	}

	public AbstractDomNodeTester<N> assertContainsText(IDisplayString expectedText) {

		return assertContainsText(expectedText.toString());
	}

	public AbstractDomNodeTester<N> assertContainsText(String expectedText) {

		if (!containsText(expectedText)) {
			Assert
				.fail(
					String
						.format(//
							"Failed to find the text '%s' in the following: '%s'",
							expectedText,
							getAllTextInDocument("|")));
		}
		return this;
	}

	public AbstractDomNodeTester<N> assertDoesNotContainText(IDisplayString unexpectedText) {

		return assertDoesNotContainText(unexpectedText.toString());
	}

	public AbstractDomNodeTester<N> assertDoesNotContainText(String unexpectedText) {

		if (containsText(unexpectedText)) {
			Assert
				.fail(
					String
						.format(//
							"Unexpectedly found the text '%s' in the following: '%s'",
							unexpectedText,
							getAllTextInDocument("|")));
		}
		return this;
	}

	public AbstractDomNodeTester<N> assertContainsNoText() {

		if (!containsNoText()) {
			Assert
				.fail(
					String
						.format(//
							"Did not expect to find text in the node but found: '%s'",
							getAllTextInDocument("|")));
		}
		return this;
	}

	public void assertDisplayed() {

		assertTrue("Expected node to be displayed.", isDisplayed());
	}

	public void assertNotDisplayed() {

		assertFalse("Expected node to not be displayed.", isDisplayed());
	}

	// ------------------------------ predicates ------------------------------ //

	public boolean isParentOf(IDomNode potentialChild) {

		IDomParentElement parent = potentialChild.getParent();
		if (parent != null) {
			return parent == node || this.isParentOf(parent);
		} else {
			return false;
		}
	}

	public boolean isDisplayed() {

		return engine.isDisplayed(node);
	}

	public boolean containsText(String expectedText) {

		if (IDomTextNode.class.isAssignableFrom(node.getClass())) {
			return ((IDomTextNode) node).getText().contains(expectedText);
		} else {
			return getAllTextsInTree().filter(text -> text.contains(expectedText)).findFirst().isPresent();
		}
	}

	public boolean containsNoText() {

		if (IDomTextNode.class.isAssignableFrom(node.getClass())) {
			return ((IDomTextNode) node).getText().equals("");
		} else {
			return getAllTextsInTree().collect(Collectors.joining()).equals("");
		}
	}

	// ------------------------------ DOM tree ------------------------------ //

	public Optional<DomNodeTester> getParent() {

		return Optional//
			.of(node)
			.map(N::getParent)
			.map(it -> new DomNodeTester(engine, it));
	}

	public DomNodeTester getParentOrThrow() {

		return getParent().get();
	}

	// ------------------------------ z-index ------------------------------ //

	public int getZIndex() {

		return node.getDomEngine().getNodeStyle(node, "zIndex").map(Integer::parseInt).orElseThrow();
	}

	// ------------------------------ retrieve textual content ------------------------------ //

	// TODO PLAT-568 This method should be renamed to getAllText (or similar).
	public String getAllTextInDocument(String delimiter) {

		return getAllTextsInTree().collect(Collectors.joining(delimiter));
	}

	// TODO PLAT-568 This method should be renamed to getAllTextAsStream (or similar).
	public Stream<String> getAllTextsInTree() {

		if (IDomTextNode.class.isAssignableFrom(node.getClass())) {
			return Collections.singleton(((IDomTextNode) node).getText()).stream();
		} else {
			return findNodes()//
				.stream()
				.filter(IDomTextNode.class::isInstance)
				.map(IDomTextNode.class::cast)
				.map(IDomTextNode::getText);
		}
	}
}
