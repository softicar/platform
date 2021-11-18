package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.IDomStringInputNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.text.IDomTextNode;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;

public class AbstractDomNodeTester<N extends IDomNode> implements IDomNodeTesterFindMethods {

	private final IDomTestEngine engine;
	protected final N node;

	public AbstractDomNodeTester(IDomTestEngine engine, N node) {

		this.engine = engine;
		this.node = node;
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Override
	public N getNode() {

		return node;
	}

	// ------------------------------ input ------------------------------ //

	public void setInputValue(IStaticObject marker, String value) {

		this//
			.findNode(marker)
			.findNode(DomTextInput.class)
			.setInputValue(value);
	}

	public AbstractDomNodeTester<N> setInputValue(String text) {

		if (node instanceof IDomStringInputNode) {
			engine.setInputValue((IDomStringInputNode) node, text);
		} else {
			throw new AssertionError(String.format("The node must be of type %s.", IDomStringInputNode.class.getSimpleName()));
		}
		return this;
	}

	public String getInputValue() {

		if (node instanceof IDomStringInputNode) {
			return engine.getInputValue((IDomStringInputNode) node);
		} else {
			throw new AssertionError(String.format("The node must be of type %s.", IDomStringInputNode.class.getSimpleName()));
		}
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

	public void sendEvent(DomEventType type) {

		engine.sendEvent(node, type);
	}

	public AbstractDomNodeTester<N> click() {

		engine.sendEvent(node, DomEventType.CLICK);
		return this;
	}

	public AbstractDomNodeTester<N> doubleClick() {

		engine.sendEvent(node, DomEventType.DBLCLICK);
		return this;
	}

	public AbstractDomNodeTester<N> rightClick() {

		engine.sendEvent(node, DomEventType.CONTEXTMENU);
		return this;
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

	public AbstractDomNodeTester<N> assertDoesNotContainText(String expectedText) {

		if (containsText(expectedText)) {
			Assert
				.fail(
					String
						.format(//
							"Unexpectedly found the text '%s' in the following: '%s'",
							expectedText,
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

	// ------------------------------ predicates ------------------------------ //

	public boolean isParentOf(IDomNode potentialChild) {

		IDomParentElement parent = potentialChild.getParent();
		if (parent != null) {
			return parent == node || this.isParentOf(parent);
		} else {
			return false;
		}
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

	// ------------------------------ retrieve textual content ------------------------------ //

	// TODO this method should be renamed to getAllText (or similar)
	public String getAllTextInDocument(String delimiter) {

		return getAllTextsInTree().collect(Collectors.joining(delimiter));
	}

	// TODO this method should be renamed to getAllTextAsStream (or similar)
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
