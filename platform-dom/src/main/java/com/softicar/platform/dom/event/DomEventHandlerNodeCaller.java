package com.softicar.platform.dom.event;

import com.softicar.platform.dom.exception.IDomExceptionDisplayElement;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Objects;

public class DomEventHandlerNodeCaller {

	private final IDomNode eventNode;
	private final IDomEvent event;

	public DomEventHandlerNodeCaller(IDomNode eventNode, IDomEvent event) {

		this.eventNode = Objects.requireNonNull(eventNode);
		this.event = Objects.requireNonNull(event);
	}

	public void call() {

		var document = eventNode.getDomDocument();
		document.setCurrentEvent(event);

		try {
			if (eventNode instanceof IDomEventHandler) {
				((IDomEventHandler) eventNode).handleDOMEvent(event);
			} else {
				event.getType().handleEvent(eventNode, event);
			}
			document.getDeferredInitializationController().handleAllAppended();
		} catch (Exception exception) {
			IDomExceptionDisplayElement displayElement = findExceptionDisplayElement(eventNode, exception);
			if (displayElement != null) {
				displayElement.display(exception);
			} else {
				throw exception;
			}
		}
	}

	private IDomExceptionDisplayElement findExceptionDisplayElement(IDomNode node, Exception exception) {

		while (node != null) {
			if (node instanceof IDomExceptionDisplayElement) {
				IDomExceptionDisplayElement displayElement = (IDomExceptionDisplayElement) node;
				if (displayElement.accepts(exception)) {
					return displayElement;
				}
			}
			node = node.getParent();
		}
		return null;
	}
}
