package com.softicar.platform.ajax.framework;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.network.http.HttpStatusCode;
import com.softicar.platform.common.network.http.error.HttpError;
import com.softicar.platform.common.network.http.error.HttpInternalServerError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AjaxServiceExceptionHandler {

	private final AjaxFramework framework;
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	public AjaxServiceExceptionHandler(AjaxFramework framework, HttpServletRequest request, HttpServletResponse response) {

		this.framework = framework;
		this.request = request;
		this.response = response;
	}

	public void handleException(Throwable throwable) {

		HttpError error = getHttpError(throwable);

		sendHttpError(error.getStatusCode());

		if (error.getStatusCode().isServerError()) {
			framework.getAjaxStrategy().logException(throwable, request);
		}
	}

	private HttpError getHttpError(Throwable throwable) {

		return CastUtils//
			.tryCast(throwable, HttpError.class)
			.orElseGet(() -> new HttpInternalServerError(throwable));
	}

	private void sendHttpError(HttpStatusCode statusCode) {

		try {
			response.sendError(statusCode.getCode());
		} catch (Exception exception) {
			DevNull.swallow(exception);
			Log.ferror("Failed to send HTTP error to client. The HTTP client probably closed the connection prematurely.");
		}
	}
}
