package com.softicar.platform.ajax.document.action;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.registry.AjaxDocumentRegistry;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.service.AbstractAjaxService;
import com.softicar.platform.common.network.http.error.HttpGoneError;

public class AjaxDocumentActionServiceDelegator extends AbstractAjaxService {

	public AjaxDocumentActionServiceDelegator(IAjaxRequest request) {

		super(request);
	}

	@Override
	public void service() {

		request//
			.getRequestMessageOrThrow()
			.getActionType()
			.createService(request, getDocumentOrThrow())
			.service();
	}

	private IAjaxDocument getDocumentOrThrow() {

		var instanceUuid = request//
			.getRequestMessageOrThrow()
			.getInstanceUuid();
		return AjaxDocumentRegistry//
			.getInstance(request.getHttpSession())
			.getDocument(instanceUuid)
			.orElseThrow(HttpGoneError::new);
	}
}
