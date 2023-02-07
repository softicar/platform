package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.testing.Asserts;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mockito.Mockito;

public class WebServiceUuidFetcherTest extends Asserts {

	private static final UUID UUID_A = UUID.fromString("784e5e46-201a-4a02-b657-04e24e329621");
	private static final UUID UUID_B = UUID.fromString("cefb09ad-0900-4741-b725-d466f4a50454");

	@Test
	public void testWithUuidInPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/context/service/" + UUID_A);
		Mockito.when(request.getContextPath()).thenReturn("/context");
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(UUID_A, serviceUuid);
	}

	@Test
	public void testWithUuidInPathAndEmptyContextPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/service/" + UUID_A);
		Mockito.when(request.getContextPath()).thenReturn("");
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(UUID_A, serviceUuid);
	}

	@Test
	public void testWithUuidInPathAndNullContextPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/context/service/" + UUID_A);
		Mockito.when(request.getContextPath()).thenReturn(null);

		assertExceptionMessage("Web service UUID is missing.", () -> new WebServiceUuidFetcher(request).getServiceUuidOrThrow());
	}

	@Test
	public void testWithUuidInParameter() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getParameter("id")).thenReturn(UUID_A.toString());
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(UUID_A, serviceUuid);
	}

	@Test
	public void testWithUuidInPathAndParameter() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/context/service/" + UUID_A);
		Mockito.when(request.getContextPath()).thenReturn("/context");
		Mockito.when(request.getParameter("id")).thenReturn(UUID_B.toString());
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(UUID_A, serviceUuid);
	}
}
