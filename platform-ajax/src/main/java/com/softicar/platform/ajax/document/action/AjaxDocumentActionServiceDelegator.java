package com.softicar.platform.ajax.document.action;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.AjaxParameterUtils;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.service.AbstractAjaxService;

public class AjaxDocumentActionServiceDelegator extends AbstractAjaxService {

	public AjaxDocumentActionServiceDelegator(IAjaxRequest request) {

		super(request);
	}

	@Override
	public void service() {

		getActionType().createService(request, getDocumentOrThrow()).service();
	}

	private IAjaxDocument getDocumentOrThrow() {

		return new AjaxDocumentFromRequestFetcher(request).fetchOrThrow();
	}

	private AjaxDocumentActionType getActionType() {

		int actionId = AjaxParameterUtils.getIntOrThrow(request, "a");
		return AjaxDocumentActionType.getById(actionId);
	}
}
