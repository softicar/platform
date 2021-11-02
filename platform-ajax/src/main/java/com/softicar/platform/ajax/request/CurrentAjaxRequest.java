package com.softicar.platform.ajax.request;

import com.softicar.platform.dom.document.IDomDocument;
import java.util.Optional;

/**
 * Attaches the current {@link IAjaxRequest} to the {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public class CurrentAjaxRequest {

	private final IAjaxRequest request;

	public CurrentAjaxRequest(IAjaxRequest request) {

		this.request = request;
	}

	public static void set(IDomDocument document, IAjaxRequest request) {

		document//
			.getDataMap()
			.putInstance(new CurrentAjaxRequest(request));
	}

	public static Optional<IAjaxRequest> get(IDomDocument document) {

		return document//
			.getDataMap()
			.getOptional(CurrentAjaxRequest.class)
			.map(CurrentAjaxRequest::getRequest);
	}

	public IAjaxRequest getRequest() {

		return request;
	}
}
