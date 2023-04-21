package com.softicar.platform.dom.event;

import com.softicar.platform.dom.node.IDomNode;

/**
 * Represents an event triggered on an {@link IDomNode}.
 * <p>
 * This type of event can be triggered by a mouse click, a key that was pressed
 * or the selecting of an option in a drop-down menu. The {@link #getType()}
 * method returns the concrete type of the event.
 *
 * @author Oliver Richers
 */
public interface IDomEvent {

	/**
	 * Returns the type of this event object.
	 *
	 * @return type of this event
	 */
	DomEventType getType();

	/**
	 * Returns the target DOM node that this event was triggered on.
	 *
	 * @return the associated DOM node (may be <i>null</i>)
	 */
	IDomNode getCurrentTarget();

	/**
	 * Returns the x position of the mouse cursor when the event was triggered.
	 *
	 * @return the mouse cursor x position or zero
	 */
	double getClientX();

	/**
	 * Returns the y position of the mouse cursor when the event was triggered.
	 *
	 * @return the mouse cursor y position or zero
	 */
	double getClientY();

	/**
	 * Returns the x position of the mouse cursor relative to the target node
	 * bounding rectangle.
	 * <p>
	 * The bounding rectangle includes the border and padding, but not the
	 * margin of the target node. Because the bounding rectangle is computed
	 * with sub-pixel accuracy, this method returns a floating-point value.
	 *
	 * @return the relative mouse cursor x position or null
	 */
	double getRelativeX();

	/**
	 * Returns the y position of the mouse cursor relative to the target node
	 * bounding rectangle.
	 * <p>
	 * The bounding rectangle includes the border and padding, but not the
	 * margin of the target node. Because the bounding rectangle is computed
	 * with sub-pixel accuracy, this method returns a floating-point value.
	 *
	 * @return the relative mouse cursor y position or null
	 */
	double getRelativeY();

	/**
	 * Returns the x offset by which the document body was scrolled when the
	 * event was triggered.
	 *
	 * @return the x scroll offset of the document body
	 */
	double getScrollX();

	/**
	 * Returns the y offset by which the document body was scrolled when the
	 * event was triggered.
	 *
	 * @return the y scroll offset of the document body
	 */
	double getScrollY();

	/**
	 * Returns the name of the key that triggered this event.
	 * <p>
	 * If no key triggered the event, the empty string is returned.
	 * @return the key name (never <i>null</i>)
	 */
	String getKey();

	/**
	 * Returns the width of the browser window when the event was triggered.
	 *
	 * @return the width of the window in pixels
	 */
	double getWindowWidth();

	/**
	 * Returns the height of the browser window when the event was triggered.
	 *
	 * @return the height of the window in pixels
	 */
	double getWindowHeight();

	/**
	 * Returns the selected text in the browser window.
	 * <p>
	 * If no text is selected, this returns a empty {@link String}.
	 *
	 * @return the selected text (never <i>null</i>)
	 */
	String getWindowSelection();

	/**
	 * Returns the bounding client {@link DomRect} for the event node.
	 *
	 * @return the bounding client {@link DomRect} (never <i>null</i>)
	 */
	DomRect getBoundingClientRect();

	/**
	 * Returns the delta-x value of a {@link DomEventType#WHEEL} event.
	 * <p>
	 * Returns 0 for any other {@link DomEventType}.
	 *
	 * @return the delta-x value
	 */
	double getDeltaX();

	/**
	 * Returns the delta-y value of a {@link DomEventType#WHEEL} event.
	 * <p>
	 * Returns 0 for any other {@link DomEventType}.
	 *
	 * @return the delta-y value
	 */
	double getDeltaY();

	/**
	 * Returns the delta-z value of a {@link DomEventType#WHEEL} event.
	 * <p>
	 * Returns 0 for any other {@link DomEventType}.
	 *
	 * @return the delta-z value
	 */
	double getDeltaZ();

	/**
	 * Returns whether the ALT key is pressed.
	 *
	 * @return true if ALT key is pressed
	 */
	boolean isAltKey();

	/**
	 * Returns whether the CTRL key is pressed.
	 *
	 * @return true if CTRL key is pressed
	 */
	boolean isCtrlKey();

	/**
	 * Returns whether the META key is pressed.
	 *
	 * @return true if META key is pressed
	 */
	boolean isMetaKey();

	/**
	 * Returns whether the SHIFT key is pressed.
	 *
	 * @return true if SHIFT key is pressed
	 */
	boolean isShiftKey();
}
