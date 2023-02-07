package com.softicar.platform.core.module.web.service;

import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.network.http.HttpStatusCode;
import com.softicar.platform.common.network.http.error.HttpError;
import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class WebServiceBrokerServiceExceptionHandlerTest extends Asserts {

	private final SimpleServletResponse response;

	public WebServiceBrokerServiceExceptionHandlerTest() {

		this.response = new SimpleServletResponse();
	}

	@Test
	public void testHandleExceptionWithHttpError() {

		var exception = new HttpError(HttpStatusCode.SERVICE_UNAVAILABLE, "Some message");

		handleException(exception, response);

		assertEquals(503, response.getStatus());
		assertEquals("Some message", response.getStatusMessage());
	}

	@Test
	public void testHandleExceptionWithUnknownRuntimeException() {

		var exception = new UnknownRuntimeException();

		handleException(exception, response);

		assertEquals(500, response.getStatus());
		assertEquals("Internal Server Error", response.getStatusMessage());
	}

	@Test
	public void testHandleExceptionWithNullException() {

		assertException(NullPointerException.class, () -> handleException(null, response));
	}

	@Test
	public void testHandleExceptionWithNullResponse() {

		var exception = new HttpError(HttpStatusCode.SERVICE_UNAVAILABLE, "Some message");

		assertException(NullPointerException.class, () -> handleException(exception, null));
	}

	private void handleException(Exception exception, SimpleServletResponse response) {

		new WebServiceBrokerServiceExceptionHandler(exception, response).handleException();
	}

	private static class UnknownRuntimeException extends RuntimeException {

		// nothing
	}
}
