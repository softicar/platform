package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.exceptions.AjaxHttpInternalServerError;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.event.DomEventHandlerNodeCaller;
import com.softicar.platform.dom.node.IDomNode;

public class AjaxDomEventService extends AbstractAjaxDocumentActionService {

	public AjaxDomEventService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public void service(IAjaxDocument document, IDomNode eventNode) {

		if (eventNode != null) {
			AjaxDomEvent event = new AjaxDomEvent(document, request);
			executePayloadCode(() -> new DomEventHandlerNodeCaller(eventNode, event).call());
		} else {
			throw new AjaxHttpInternalServerError("A non-existing DOM node received a DOM event.");
		}
	}
}
