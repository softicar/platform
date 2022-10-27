package com.softicar.platform.dom.node;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomHead;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import com.softicar.platform.dom.elements.dialog.DomModalConfirmDialog;
import com.softicar.platform.dom.elements.dialog.DomModalPromptDialog;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Collection;
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
	 * @return the parent node or <i>null</i>
	 */
	IDomParentElement getParent();

	/**
	 * Removes this node from its parent.
	 * <p>
	 * If this node has no parent, this method does nothing.
	 */
	void disappend();

	/**
	 * Determines whether this node is appended (either directly or
	 * transitively) to the {@link DomBody} or {@link DomHead} of its
	 * {@link IDomDocument}.
	 *
	 * @return <i>true</i> if this node is appended; <i>false</i> otherwise
	 */
	boolean isAppended();

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
	 * Adds an {@link ITestMarker} to this {@link IDomNode}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to add (never <i>null</i>)
	 * @throws UnsupportedOperationException
	 *             if the {@link IDomDocument} does not support marking of nodes
	 */
	default IDomNode addMarker(ITestMarker marker) {

		getDomDocument().addMarker(this, marker);
		return this;
	}

	/**
	 * Calls {@link #addMarker(ITestMarker)} for all given {@link ITestMarker}s.
	 */
	default IDomNode addMarkers(Collection<ITestMarker> markers) {

		markers.forEach(this::addMarker);
		return this;
	}

	/**
	 * Checks whether this {@link IDomNode} has all given {@link ITestMarker}s.
	 *
	 * @param markers
	 *            the {@link ITestMarker}s to check for
	 * @return <i>true</i> if the node has the given markers, <i>false</i>
	 *         otherwise
	 */
	default boolean hasMarker(ITestMarker...markers) {

		return getDomDocument().hasMarker(this, markers);
	}

	// -------------------------------- initializer -------------------------------- //

	/**
	 * Adds the given deferred initializer (i.e. an initialization callback) for
	 * this {@link IDomNode}.
	 * <p>
	 * The initializer is executed when the {@link IDomNode} obtains a
	 * transitive parent connection to the {@link DomBody}.
	 *
	 * @param initializer
	 *            the initialization callback to execute as soon as this
	 *            {@link IDomNode} is transitively appended to the
	 *            {@link DomBody} (never <i>null</i>)
	 */
	default void addDeferredInitializer(INullaryVoidFunction initializer) {

		getDomDocument().getDeferredInitializationController().addDeferredInitializer(this, initializer);
	}

	// -------------------------------- alert, confirm and prompt -------------------------------- //

	/**
	 * Displays a custom modal alert dialog, for the given message.
	 *
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	default void executeAlert(IDisplayString message) {

		new DomModalAlertDialog(message).open();
	}

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
	default void executeConfirm(INullaryVoidFunction confirmHandler, IDisplayString message) {

		executeConfirm(confirmHandler, null, message);
	}

	/**
	 * Displays a custom modal confirm dialog, for the given handlers and
	 * message.
	 *
	 * @param confirmHandler
	 *            the handler to be processed in case the user clicks "OK"
	 *            (never <i>null</i>)
	 * @param cancelHandler
	 *            the handler to be processed in case the user clicks "Cancel"
	 *            (may be <i>null</i>)
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	default void executeConfirm(INullaryVoidFunction confirmHandler, INullaryVoidFunction cancelHandler, IDisplayString message) {

		new DomModalConfirmDialog(confirmHandler, cancelHandler, message).open();
	}

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
	default void executePrompt(Consumer<String> promptHandler, IDisplayString message, String defaultValue) {

		new DomModalPromptDialog(promptHandler, message, defaultValue).open();
	}
}
