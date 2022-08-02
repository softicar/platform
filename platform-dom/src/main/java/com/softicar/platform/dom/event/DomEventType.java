package com.softicar.platform.dom.event;

import com.softicar.platform.dom.node.IDomNode;
import java.util.function.BiConsumer;

/**
 * Enumerates all allowed event types.
 *
 * @author Oliver Richers
 */
public enum DomEventType {

	CHANGE(IDomChangeEventHandler.class, IDomChangeEventHandler::handleChange),
	CLICK(IDomClickEventHandler.class, IDomClickEventHandler::handleClick),
	CONTEXTMENU(IDomContextMenuEventHandler.class, IDomContextMenuEventHandler::handleContextMenu),
	DBLCLICK(IDomDoubleClickEventHandler.class, IDomDoubleClickEventHandler::handleDoubleClick),
	ESCAPE(IDomEscapeKeyEventHandler.class, IDomEscapeKeyEventHandler::handleEscapeKey),
	ENTER(IDomEnterKeyEventHandler.class, IDomEnterKeyEventHandler::handleEnterKey),
	INPUT(IDomInputEventHandler.class, IDomInputEventHandler::handleInput),
	KEYDOWN(IDomKeyDownEventHandler.class, IDomKeyDownEventHandler::handleKeyDown),
	KEYUP(IDomKeyUpEventHandler.class, IDomKeyUpEventHandler::handleKeyUp),
	SPACE(IDomSpaceKeyEventHandler.class, IDomSpaceKeyEventHandler::handleSpaceKey),
	TAB(IDomTabKeyEventHandler.class, IDomTabKeyEventHandler::handleTabKey);

	private final DomEventDelegator<?> eventDelegator;

	private <H extends IDomAutoEventHandler> DomEventType(Class<H> eventHandlerClass, BiConsumer<H, IDomEvent> eventConsumer) {

		this.eventDelegator = new DomEventDelegator<>(this, eventHandlerClass, eventConsumer);
	}

	/**
	 * Enables the event listening for this event type on the given node.
	 * <p>
	 * The event listening is only enabled if the given node implements the
	 * event handling interface, associated with this event type.
	 *
	 * @param node
	 * @return <i>true</i> if event listening was enabled, <i>false</i>
	 *         otherwise
	 */
	public boolean enableEventListening(IDomNode node) {

		return eventDelegator.enableEventListening(node);
	}

	/**
	 * Makes the given node handle the specified event.
	 * <p>
	 * The event handling is only done if the given node implements the event
	 * handling interface, associated with this event type.
	 *
	 * @param node
	 *            an instance of {@link IDomNode}
	 * @param event
	 *            the event to handle
	 * @return <i>true</i> if event was handled, <i>false</i> otherwise
	 */
	public boolean handleEvent(IDomNode node, IDomEvent event) {

		return eventDelegator.handleEvent(node, event);
	}
}
