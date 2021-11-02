package com.softicar.platform.dom.event;

import com.softicar.platform.dom.node.IDomNode;
import java.util.function.BiConsumer;

public class DomEventDelegator<H extends IDomAutoEventHandler> {

	private final DomEventType type;
	private final Class<H> handlerClass;
	private final BiConsumer<H, IDomEvent> consumer;

	public DomEventDelegator(DomEventType type, Class<H> handlerClass, BiConsumer<H, IDomEvent> consumer) {

		this.type = type;
		this.handlerClass = handlerClass;
		this.consumer = consumer;
	}

	/**
	 * Enables the event listening on the given node.
	 * <p>
	 * The event listening is only enabled if the given node implements the
	 * event handling interface, associated with this event delegator.
	 *
	 * @param node
	 *            an instance of {@link IDomNode}
	 * @return <i>true</i> if event listening was enabled, <i>false</i>
	 *         otherwise
	 */
	public boolean enableEventListening(IDomNode node) {

		if (handlerClass.isInstance(node)) {
			node.getDomEngine().listenToEvent(node, type);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Makes the given node handle the specified event.
	 * <p>
	 * The event handling is only done if the given node implements the event
	 * handling interface, associated with this event delegator.
	 *
	 * @param node
	 *            an instance of {@link IDomNode}
	 * @param event
	 *            the event to handle
	 * @return <i>true</i> if event was handled, <i>false</i> otherwise
	 */
	public boolean handleEvent(IDomNode node, IDomEvent event) {

		if (handlerClass.isInstance(node)) {
			consumer.accept(handlerClass.cast(node), event);
			return true;
		} else {
			return false;
		}
	}
}
