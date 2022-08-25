package com.softicar.platform.ajax.document.action;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.drag.AjaxDragAndDropService;
import com.softicar.platform.ajax.event.AjaxDomEventService;
import com.softicar.platform.ajax.keepalive.AjaxKeepAliveService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.service.IAjaxService;
import com.softicar.platform.ajax.timeout.AjaxTimeoutService;
import com.softicar.platform.ajax.upload.AjaxUploadService;
import com.softicar.platform.common.container.enums.IdToEnumMap;
import java.util.function.BiFunction;

/**
 * Enumerates all valid AJAX request types.
 *
 * @author Oliver Richers
 */
public enum AjaxDocumentActionType {

	KEEP_ALIVE(2, AjaxKeepAliveService::new),
	TIMEOUT(5, AjaxTimeoutService::new),
	DOM_EVENT(6, AjaxDomEventService::new),
	DRAG_AND_DROP(7, AjaxDragAndDropService::new),
	UPLOAD(8, AjaxUploadService::new),
	//
	;

	private static final IdToEnumMap<AjaxDocumentActionType> MAP = new IdToEnumMap<>(AjaxDocumentActionType.class, AjaxDocumentActionType::getId);
	private final int id;
	private final BiFunction<IAjaxRequest, IAjaxDocument, IAjaxService> serviceFactory;

	private AjaxDocumentActionType(int id, BiFunction<IAjaxRequest, IAjaxDocument, IAjaxService> serviceFactory) {

		this.id = id;
		this.serviceFactory = serviceFactory;
	}

	public static AjaxDocumentActionType getById(int id) {

		return MAP.get(id);
	}

	public int getId() {

		return id;
	}

	public IAjaxService createService(IAjaxRequest request, IAjaxDocument document) {

		return serviceFactory.apply(request, document);
	}
}
