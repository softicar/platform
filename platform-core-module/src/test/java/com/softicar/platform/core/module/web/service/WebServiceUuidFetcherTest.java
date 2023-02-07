package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.testing.Asserts;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mockito.Mockito;

public class WebServiceUuidFetcherTest extends Asserts {

	private static final UUID SERVICE_UUID = UUID.fromString("784e5e46-201a-4a02-b657-04e24e329621");

	@Test
	public void testWithUuidInPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/context/service/" + SERVICE_UUID);
		Mockito.when(request.getContextPath()).thenReturn("/context");
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(SERVICE_UUID, serviceUuid);
	}

	@Test
	public void testWithUuidInPathAndEmptyContextPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/service/" + SERVICE_UUID);
		Mockito.when(request.getContextPath()).thenReturn("");
		var serviceUuid = new WebServiceUuidFetcher(request).getServiceUuidOrThrow();

		assertEquals(SERVICE_UUID, serviceUuid);
	}

	@Test
	public void testWithUuidInPathAndNullContextPath() {

		var request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/context/service/" + SERVICE_UUID);
		Mockito.when(request.getContextPath()).thenReturn(null);

		assertExceptionMessage("Web service UUID is missing.", () -> new WebServiceUuidFetcher(request).getServiceUuidOrThrow());
	}
}
