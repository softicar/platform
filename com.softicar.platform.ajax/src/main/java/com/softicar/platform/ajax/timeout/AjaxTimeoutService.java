package com.softicar.platform.ajax.timeout;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.event.timeout.IDomTimeoutNode;
import com.softicar.platform.dom.node.IDomNode;

/**
 * AJAX service to handle timeout requests.
 *
 * @author Oliver Richers
 */
public class AjaxTimeoutService extends AbstractAjaxDocumentActionService {

	public AjaxTimeoutService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public void service(IAjaxDocument document, IDomNode eventNode) {

		executePayloadCodeOnNode(IDomTimeoutNode.class, IDomTimeoutNode::handleTimeout);
	}
}
