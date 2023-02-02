package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class WebServicePathTest extends Asserts {

	@Test
	public void testParseWithValidPath() {

		assertIdentifierAndPath("", "", WebServicePath.parseOrThrow("/service/"));
		assertIdentifierAndPath("", "/", WebServicePath.parseOrThrow("/service//"));
		assertIdentifierAndPath("0815", "", WebServicePath.parseOrThrow("/service/0815"));
		assertIdentifierAndPath("0815", "/", WebServicePath.parseOrThrow("/service/0815/"));
		assertIdentifierAndPath("0815", "/foo/bar", WebServicePath.parseOrThrow("/service/0815/foo/bar"));
	}

	@Test
	public void testParseWithIllegalPath() {

		assertEmpty(WebServicePath.parse(null));
		assertEmpty(WebServicePath.parse(""));
		assertEmpty(WebServicePath.parse(" "));
		assertEmpty(WebServicePath.parse("/"));
		assertEmpty(WebServicePath.parse("/foo"));
		assertEmpty(WebServicePath.parse("/foo/"));
		assertEmpty(WebServicePath.parse("/service"));
	}

	private void assertIdentifierAndPath(String expectedServiceIdentifier, String expectedResourcePath, WebServicePath servicePath) {

		assertEquals(expectedServiceIdentifier, servicePath.getServiceIdentifier());
		assertEquals(expectedResourcePath, servicePath.getResourcePath());
	}
}
