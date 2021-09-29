package com.softicar.platform.dom.node;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Basic interface of a tree node.
 * <p>
 * Every node has an ID that is unique within the owning {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public interface IDomNode {

	// -------------------- basic methods -------------------- //

	/**
	 * Returns the ID of this node.
	 *
	 * @return the ID of this node
	 */
	int getNodeId();

	/**
	 * Returns the ID {@link String} of this node.
	 * <p>
	 * Corresponds to {@link #getNodeId()}, with a prefix of {@code "n"}, e.g.
	 * {@code "n123"}.
	 *
	 * @return the ID {@link String} of this node (never <i>null</i>)
	 */
	default String getNodeIdString() {

		return "n" + getNodeId();
	}

	/**
	 * Returns the parent node of this node.
	 *
	 * @return the parent node or null
	 */
	IDomParentElement getParent();

	/**
	 * Removes this node from its parent.
	 * <p>
	 * If this node has no parent, this method does nothing.
	 */
	void disappend();

	/**
	 * Returns the {@link IDomDocument} that this node belongs to.
	 *
	 * @return the {@link IDomDocument} of this node, never null
	 */
	IDomDocument getDomDocument();

	/**
	 * Returns the current event.
	 *
	 * @return the current event object or null
	 */
	default IDomEvent getCurrentEvent() {

		return getDomDocument().getCurrentEvent();
	}

	/**
	 * Provides access to the internals of this node.
	 *
	 * @return an instance of the accessor, never null
	 */
	IDomNodeAccessor getAccessor();

	/**
	 * Returns the {@link IDomEngine} that this {@link IDomNode} is associated
	 * with.
	 *
	 * @return the {@link IDomEngine} of this node, never null
	 */
	default IDomEngine getDomEngine() {

		return getDomDocument().getEngine();
	}

	// -------------------- attribute methods -------------------- //

	/**
	 * Returns all defined attributes of this node.
	 *
	 * @return an iterable over all defined attributes
	 */
	Iterable<IDomAttribute> getAttributes();

	/**
	 * Returns the attribute with the specified name.
	 *
	 * @return the corresponding attribute (never null)
	 */
	IDomAttribute getAttribute(String name);

	/**
	 * Returns the value of the attribute with the specified name or null if the
	 * attribute was never set.
	 *
	 * @return the corresponding attribute value or null
	 */
	Optional<String> getAttributeValue(String name);

	/**
	 * Sets the specified attribute for this node.
	 *
	 * @return this node
	 */
	IDomNode setAttribute(IDomAttribute attribute);

	/**
	 * Sets the specified attribute without quoting the integer value.
	 *
	 * @return this node
	 */
	IDomNode setAttribute(String name, int value);

	/**
	 * Sets the specified attribute without quoting the boolean value.
	 *
	 * @return this node
	 */
	IDomNode setAttribute(String name, boolean value);

	/**
	 * Sets the specified attribute with quoting the string value.
	 * <p>
	 * This does not quote the value if it is null.
	 *
	 * @return this node
	 */
	IDomNode setAttribute(String name, String value);

	/**
	 * Sets the attribute to the specified string value.
	 * <p>
	 * If the parameter quote is true, this adds single quotes to the value.
	 * This is needed if the specified value is for example a string.
	 *
	 * @return this node
	 */
	IDomNode setAttribute(String name, String value, boolean quote);

	/**
	 * Removes the given attribute and its value.
	 *
	 * @param name
	 *            the attribute name (never null)
	 * @return this node
	 */
	IDomNode unsetAttribute(String name);

	// -------------------------------- marker -------------------------------- //

	/**
	 * Defines the {@link IStaticObject} marker for this node.
	 *
	 * @param marker
	 *            the marker to set (never null)
	 * @throws UnsupportedOperationException
	 *             if the {@link IDomDocument} does not support marking of nodes
	 */
	default IDomNode setMarker(IStaticObject marker) {

		getDomDocument().setMarker(this, marker);
		return this;
	}

	/**
	 * Checks whether this note has the given marker.
	 *
	 * @param marker
	 *            the marker to check for
	 * @return <i>true</i> if the node has the given marker, <i>false</i>
	 *         otherwise
	 */
	default boolean hasMarker(IStaticObject marker) {

		return getDomDocument().hasMarker(this, marker);
	}

	// -------------------------------- alert, confirm and prompt -------------------------------- //

	/**
	 * Displays a custom modal alert dialog, for the given message.
	 *
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	void executeAlert(IDisplayString message);

	/**
	 * Displays a custom modal confirm dialog, for the given handler and
	 * message.
	 *
	 * @param confirmHandler
	 *            the handler to be processed in case the user clicks "OK"
	 *            (never <i>null</i>)
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	void executeConfirm(INullaryVoidFunction confirmHandler, IDisplayString message);

	/**
	 * Displays a custom modal prompt dialog, for the given handler and message.
	 *
	 * @param promptHandler
	 *            the handler to be processed in case the user clicks "OK"
	 *            (never <i>null</i>)
	 * @param message
	 *            the message to display (never <i>null</i>)
	 * @param defaultValue
	 *            the initial value of the input element (may be <i>null</i>)
	 */
	void executePrompt(Consumer<String> promptHandler, IDisplayString message, String defaultValue);

	// -------------------------------- HTML -------------------------------- //

	/**
	 * Builds an HTML representation of this node.
	 *
	 * @param output
	 *            the {@link Appendable} to which the HTML shall be directed
	 *            (never <i>null</i>)
	 */
	void buildHtml(Appendable output) throws IOException;
}
