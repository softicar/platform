package com.softicar.platform.ajax.simple;

import org.junit.Assert;
import org.junit.Test;

public class SimpleServletRequestTest extends Assert {

	@Test
	public void testQueryString() {

		SimpleServletRequest request = new SimpleServletRequest();
		request.setParameter("aaa", "1");
		request.setParameter("bbb", "x+2;:");
		assertEquals("aaa=1&bbb=x%2B2%3B%3A", request.getQueryString());
	}

	@Test
	public void testRequestUrlWithProtocol() {

		SimpleServletRequest request = new SimpleServletRequest();
		request.setRequestUrl("http://foo/bar.html");

		assertEquals("/bar.html", request.getRequestURI());
		assertEquals("http://foo/bar.html", request.getRequestURL().toString());
	}

	@Test
	public void testRequestUrlWithoutProtocol() {

		SimpleServletRequest request = new SimpleServletRequest();
		request.setRequestUrl("/foo/bar.html");

		assertEquals("/foo/bar.html", request.getRequestURI());
		assertEquals("/foo/bar.html", request.getRequestURL().toString());
	}
}
