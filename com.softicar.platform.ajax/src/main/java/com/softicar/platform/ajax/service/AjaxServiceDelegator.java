package com.softicar.platform.ajax.service;

import com.softicar.platform.ajax.document.action.AjaxDocumentActionServiceDelegator;
import com.softicar.platform.ajax.document.creation.AjaxDocumentCreationService;
import com.softicar.platform.ajax.exceptions.AjaxHttpBadRequestError;
import com.softicar.platform.ajax.exceptions.AjaxHttpNotFoundError;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.resource.AjaxResourceService;
import com.softicar.platform.common.core.logging.CurrentLogLevel;
import com.softicar.platform.common.core.logging.LogLevel;

/**
 * Delegates the handling of service requests to the respective services.
 *
 * @author Oliver Richers
 */
public class AjaxServiceDelegator extends AbstractAjaxService {

	public AjaxServiceDelegator(IAjaxRequest request) {

		super(request);
	}

	@Override
	public void service() {

		setLogLevel();

		if (request.isFavoriteIconRequest()) {
			throw new AjaxHttpNotFoundError();
		} else if (request.isResourceRequest()) {
			new AjaxResourceService(request).service();
		} else if (request.isDocumentActionRequest()) {
			new AjaxDocumentActionServiceDelegator(request).service();
		} else if (request.isDocumentCreationRequest()) {
			new AjaxDocumentCreationService(request).service();
		} else {
			throw new AjaxHttpBadRequestError();
		}
	}

	private void setLogLevel() {

		CurrentLogLevel.set(request.isVerbose()? LogLevel.VERBOSE : LogLevel.INFO);
	}
}
