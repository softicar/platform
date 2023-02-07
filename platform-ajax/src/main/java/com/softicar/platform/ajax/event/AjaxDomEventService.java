package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.network.http.error.HttpInternalServerError;
import com.softicar.platform.dom.event.DomEventHandlerNodeCaller;
import com.softicar.platform.dom.node.IDomNode;

public class AjaxDomEventService extends AbstractAjaxDocumentActionService {

	public AjaxDomEventService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public void service(IAjaxDocument document, IDomNode eventNode) {

		if (eventNode != null) {
			var event = new AjaxDomEvent(document, message);
			var executor = createPayloadCodeExecutor().setEventNode(eventNode);
			new DomEventHandlerNodeCaller(executor, eventNode, event).call();
		} else {
			throw new HttpInternalServerError("A non-existing DOM node received a DOM event.");
		}
	}
}
