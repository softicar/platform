package com.softicar.platform.ajax.keepalive;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.node.IDomNode;

/**
 * AJAX service to handle keep-alive requests.
 *
 * @author Oliver Richers
 */
public final class AjaxKeepAliveService extends AbstractAjaxDocumentActionService {

	public AjaxKeepAliveService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public void service(IAjaxDocument document, IDomNode eventNode) {

		CastUtils.tryCast(document, IAjaxKeepAliveHandler.class).ifPresent(this::handleKeepAlive);
	}

	private void handleKeepAlive(IAjaxKeepAliveHandler handler) {

		executePayloadCode(handler::handleKeepAlive);
	}
}
