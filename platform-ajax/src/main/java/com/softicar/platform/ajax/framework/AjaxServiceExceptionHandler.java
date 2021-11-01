package com.softicar.platform.ajax.framework;

import com.softicar.platform.ajax.exceptions.AjaxHttpError;
import com.softicar.platform.ajax.exceptions.AjaxHttpInternalServerError;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.utils.CastUtils;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxServiceExceptionHandler {

	private final IAjaxFramework framework;
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	public AjaxServiceExceptionHandler(IAjaxFramework framework, HttpServletRequest request, HttpServletResponse response) {

		this.framework = framework;
		this.request = request;
		this.response = response;
	}

	public void handleException(Throwable throwable) {

		AjaxHttpError error = getHttpError(throwable);

		sendHttpError(error.getHttpStatusCode());

		if (error.isServerError()) {
			framework.getAjaxStrategy().logException(throwable, request);
		}
	}

	private AjaxHttpError getHttpError(Throwable throwable) {

		return CastUtils//
			.tryCast(throwable, AjaxHttpError.class)
			.orElseGet(() -> new AjaxHttpInternalServerError(throwable));
	}

	private void sendHttpError(int errorCode) {

		try {
			response.sendError(errorCode);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
