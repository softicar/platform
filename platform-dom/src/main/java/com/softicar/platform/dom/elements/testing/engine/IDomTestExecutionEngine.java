package com.softicar.platform.dom.elements.testing.engine;

import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Supplier;
import org.junit.Rule;
import org.junit.rules.TestRule;

/**
 * Common engine interface to execute UI test.
 * <p>
 * Extends {@link TestRule}, so that it can be used as a JUnit4 {@link Rule} in
 * a test class:
 *
 * <pre>
 * &#64;Rule public IDomTestExecutionEngine engine = ...
 * </pre>
 *
 * @author Alexander Schmidt
 */
public interface IDomTestExecutionEngine extends TestRule {

	/**
	 * Specifies a {@link Supplier} that provides the node-under-test.
	 * <p>
	 * The supplied node will be retrieved, and appended to the body when
	 * {@link #getBodyNode()} is called for the first time.
	 *
	 * @param nodeSupplier
	 *            a {@link Supplier} of the node-under-test (never <i>null</i>)
	 * @see #getBodyNode()
	 */
	void setNodeSupplier(Supplier<IDomNode> nodeSupplier);

	/**
	 * Returns the HTML body node which contains the node-under-test.
	 * <p>
	 * When first invoked, it employs the {@link Supplier} given to
	 * {@link #setNodeSupplier(Supplier)}.
	 *
	 * @return the HTML body node (never <i>null</i>)
	 * @see #setNodeSupplier(Supplier)
	 */
	IDomNode getBodyNode();

	/**
	 * Sends an event of the given {@link DomEventType} to the given
	 * {@link IDomNode}.
	 *
	 * @param node
	 *            the {@link IDomNode} to receive the event (never <i>null</i>)
	 * @param eventType
	 *            the {@link DomEventType} to send (never <i>null</i>)
	 */
	void sendEvent(IDomNode node, DomEventType eventType);

	/**
	 * Inputs the specified text into the given {@link IDomTextualInput} and
	 * triggers two {@link IDomEvent}s: one of type {@link DomEventType#TAB} and
	 * one of type {@link DomEventType#CHANGE}.
	 *
	 * @param node
	 *            the {@link IDomNode} to receive the text (never <i>null</i>)
	 * @param text
	 *            the text to send (never <i>null</i>)
	 */
	void setValueText(IDomTextualInput node, String text);

	/**
	 * Retrieves the content of the given text input element.
	 *
	 * @param node
	 *            the {@link IDomNode} to retrieve the text from (never
	 *            <i>null</i>)
	 * @return the the textual content (may be <i>null</i>)
	 */
	String getValueText(IDomTextualInput node);

	/**
	 * Tests whether the given {@link IDomNode} is displayed in the browser.
	 *
	 * @return <i>true</i> if the {@link IDomNode} is displayed; <i>false</i>
	 *         otherwise
	 */
	boolean isDisplayed(IDomNode node);
}
