package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.network.http.HttpStatusCode;
import com.softicar.platform.common.network.http.error.HttpError;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public class WebServiceBrokerServiceExceptionHandler {

	private final Exception exception;
	private final HttpServletResponse response;

	public WebServiceBrokerServiceExceptionHandler(Exception exception, HttpServletResponse response) {

		this.exception = exception;
		this.response = response;
	}

	public void handleException() {

		CastUtils//
			.tryCast(exception, HttpError.class)
			.ifPresentOrElse(this::handleHttpError, this::handleIntenalError);
	}

	private void handleHttpError(HttpError httpError) {

		sendError(httpError.getStatusCode(), httpError.getMessage());
	}

	private void handleIntenalError() {

		exception.printStackTrace();
		sendError(HttpStatusCode.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}

	private void sendError(HttpStatusCode statusCode, String message) {

		try {
			response.sendError(statusCode.getCode(), message);
		} catch (IOException exception) {
			// ignoring I/O exceptions; probably the client closed the connection
			DevNull.swallow(exception);
		}
	}
}
