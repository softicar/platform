package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.testing.Asserts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mockito.Mockito;

public class WebServicePathExtractorTest extends Asserts {

	@Test
	public void test() {

		assertPath("|", "/portal", "/portal/service/");
		assertPath("abc|", "/portal", "/portal/service/abc");
		assertPath("abc|/", "/portal", "/portal/service/abc/");
		assertPath("abc|/my/resource.js", "/portal", "/portal/service/abc/my/resource.js");

		assertError("Failed to retrieve servlet context path.", null, "/portal/service/abc");
		assertError("Failed to retrieve request URI.", "/portal", null);
		assertError("Failed to determine web service path from request URI.", "/portal", "/portal/service");
		assertError("Failed to determine web service path from request URI.", "/portal", "/xxxxxx/service/abc");
	}

	private void assertPath(String expectedPath, String contextPath, String requestUri) {

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn(requestUri);
		Mockito.when(request.getContextPath()).thenReturn(contextPath);

		var path = new WebServicePathExtractor(request).extractPathOrThrow();

		assertEquals(expectedPath, path.getServiceIdentifier() + '|' + path.getResourcePath());
	}

	private void assertError(String expectedError, String contextPath, String requestUri) {

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn(requestUri);
		Mockito.when(request.getContextPath()).thenReturn(contextPath);

		assertExceptionMessage(expectedError, () -> new WebServicePathExtractor(request).extractPathOrThrow());
	}
}
