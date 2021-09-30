package com.softicar.platform.ajax.document.action;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.registry.AjaxDocumentRegistry;
import com.softicar.platform.ajax.exceptions.AjaxHttpGoneError;
import com.softicar.platform.ajax.request.AjaxParameterUtils;
import com.softicar.platform.ajax.request.IAjaxRequest;
import java.util.UUID;

class AjaxDocumentFromRequestFetcher {

	private static final String DOCUMENT_INSTANCE_UUID_PARAMETER = "i";

	private final IAjaxRequest request;

	public AjaxDocumentFromRequestFetcher(IAjaxRequest request) {

		this.request = request;
	}

	public IAjaxDocument fetchOrThrow() {

		UUID instanceUuid = UUID.fromString(AjaxParameterUtils.getStringOrThrow(request, DOCUMENT_INSTANCE_UUID_PARAMETER));
		return AjaxDocumentRegistry//
			.getInstance(request.getHttpSession())
			.getDocument(instanceUuid)
			.orElseThrow(AjaxHttpGoneError::new);
	}
}
