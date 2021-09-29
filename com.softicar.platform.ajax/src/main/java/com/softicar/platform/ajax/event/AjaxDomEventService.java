package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.exceptions.AjaxHttpInternalServerError;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.exception.IDomExceptionDisplayElement;
import com.softicar.platform.dom.node.IDomNode;

public class AjaxDomEventService extends AbstractAjaxDocumentActionService {

	public AjaxDomEventService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public void service(IAjaxDocument document, IDomNode eventNode) {

		// prepare event
		IDomEvent event = new AjaxDomEvent(document, request);
		document.setCurrentEvent(event);

		// call event handler
		if (eventNode != null) {
			// TODO wrapping of the payload code should be done deeper down
			executePayloadCode(() -> handleEvent(event, eventNode));
		} else {
			throw new AjaxHttpInternalServerError("A non-existing DOM node received a DOM event.");
		}
	}

	private void handleEvent(IDomEvent event, IDomNode eventNode) {

		try {
			if (!event.getType().handleEvent(eventNode, event)) {
				((IDomEventHandler) eventNode).handleDOMEvent(event);
			}
		} catch (RuntimeException exception) {
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
