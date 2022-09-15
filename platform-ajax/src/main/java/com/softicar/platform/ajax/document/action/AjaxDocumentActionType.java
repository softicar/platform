package com.softicar.platform.ajax.document.action;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.drag.AjaxDragAndDropService;
import com.softicar.platform.ajax.event.AjaxDomEventService;
import com.softicar.platform.ajax.keepalive.AjaxKeepAliveService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.service.IAjaxService;
import com.softicar.platform.ajax.timeout.AjaxTimeoutService;
import com.softicar.platform.ajax.upload.AjaxUploadService;
import java.util.function.BiFunction;

/**
 * Enumerates all valid AJAX request types.
 *
 * @author Oliver Richers
 */
public enum AjaxDocumentActionType {

	KEEP_ALIVE(AjaxKeepAliveService::new),
	TIMEOUT(AjaxTimeoutService::new),
	DOM_EVENT(AjaxDomEventService::new),
	DRAG_AND_DROP(AjaxDragAndDropService::new),
	UPLOAD(AjaxUploadService::new),
	//
	;

	private final BiFunction<IAjaxRequest, IAjaxDocument, IAjaxService> serviceFactory;

	private AjaxDocumentActionType(BiFunction<IAjaxRequest, IAjaxDocument, IAjaxService> serviceFactory) {

		this.serviceFactory = serviceFactory;
	}

	public IAjaxService createService(IAjaxRequest request, IAjaxDocument document) {

		return serviceFactory.apply(request, document);
	}
}
