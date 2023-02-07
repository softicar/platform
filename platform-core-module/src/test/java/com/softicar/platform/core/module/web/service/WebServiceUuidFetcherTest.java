package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.testing.Asserts;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mockito.Mockito;

public class WebServiceUuidFetcherTest extends Asserts {

	private static final UUID SERVICE_UUID = UUID.fromString("784e5e46-201a-4a02-b657-04e24e329621");

	@Test
	public void testWithProperUuid() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/context/service/" + SERVICE_UUID);
		Mockito.when(request.getContextPath()).thenReturn("/context");
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(SERVICE_UUID, serviceUuid);
	}

	@Test
	public void testWithEmptyContextPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/service/" + SERVICE_UUID);
		Mockito.when(request.getContextPath()).thenReturn("");
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(SERVICE_UUID, serviceUuid);
	}

	@Test
	public void testWithMissingServiceUuid() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/service");
		Mockito.when(request.getContextPath()).thenReturn("");

		assertExceptionMessage("Failed to determine web service path from request URL.", () -> new WebServiceUuidFetcher(request).getServiceUuidOrThrow());
	}

	@Test
	public void testWithMalformedServiceUuid() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/service/foo");
		Mockito.when(request.getContextPath()).thenReturn("");

		assertExceptionMessage("Request URL contains malformed web service UUID.", () -> new WebServiceUuidFetcher(request).getServiceUuidOrThrow());
	}

	@Test
	public void testWithNullContextPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/context/service/" + SERVICE_UUID);
		Mockito.when(request.getContextPath()).thenReturn(null);

		assertExceptionMessage("Failed to retrieve servlet context path.", () -> new WebServiceUuidFetcher(request).getServiceUuidOrThrow());
	}
}
