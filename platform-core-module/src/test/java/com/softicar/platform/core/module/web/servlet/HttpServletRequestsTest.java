package com.softicar.platform.core.module.web.servlet;

import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class HttpServletRequestsTest extends Asserts {

	private static final String TRUSTED_PROXIES = "127.0.0.1";

	@Test
	public void testGetClientAddress() {

		var request = new TestRequest("127.0.0.1");
		request.setHeader("X-Forwarded-For", "198.51.100.66, 203.0.113.7");

		assertEquals("203.0.113.7", HttpServletRequests.getClientAddress(request, TRUSTED_PROXIES));
	}

	@Test
	public void testGetClientAddressWithMultipleForwardedForHeaders() {

		var request = new TestRequest("127.0.0.1");
		request.setHeader("X-Forwarded-For", "198.51.100.66");
		request.setHeader("X-Forwarded-For", "203.0.113.7");

		assertEquals("203.0.113.7", HttpServletRequests.getClientAddress(request, TRUSTED_PROXIES));
	}

	@Test
	public void testGetClientAddressWithoutForwardedFor() {

		assertEquals("127.0.0.1", HttpServletRequests.getClientAddress(new TestRequest("127.0.0.1"), TRUSTED_PROXIES));
	}

	@Test
	public void testGetClientAddressFromUntrustedPeer() {

		var request = new TestRequest("203.0.113.7");
		request.setHeader("X-Forwarded-For", "198.51.100.66");

		assertEquals("203.0.113.7", HttpServletRequests.getClientAddress(request, TRUSTED_PROXIES));
	}

	private static class TestRequest extends SimpleServletRequest {

		private final String remoteAddress;

		public TestRequest(String remoteAddress) {

			this.remoteAddress = remoteAddress;
		}

		@Override
		public String getRemoteAddr() {

			return remoteAddress;
		}
	}
}
