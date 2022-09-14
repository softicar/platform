package com.softicar.platform.ajax.drag;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.event.IDomDropEventHandler;
import com.softicar.platform.dom.node.IDomNode;

public class AjaxDragAndDropService extends AbstractAjaxDocumentActionService {

	public AjaxDragAndDropService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public void service(IAjaxDocument document, IDomNode eventNode) {

		executePayloadCodeOnNode(IDomDropEventHandler.class, this::handleDrop);
	}

	private void handleDrop(IDomDropEventHandler handler) {

		handler.handleDrop(new AjaxDomDropEvent(document, message));
	}
}
