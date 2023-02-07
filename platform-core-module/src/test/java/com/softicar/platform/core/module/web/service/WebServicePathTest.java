package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.testing.Asserts;
import java.util.Optional;
import org.junit.Test;

public class WebServicePathTest extends Asserts {

	@Test
	public void testParseWithValidPath() {

		assertIdentifierAndPath("", "", WebServicePath.parse("/service/"));
		assertIdentifierAndPath("", "/", WebServicePath.parse("/service//"));
		assertIdentifierAndPath("0815", "", WebServicePath.parse("/service/0815"));
		assertIdentifierAndPath("0815", "/", WebServicePath.parse("/service/0815/"));
		assertIdentifierAndPath("0815", "/foo/bar", WebServicePath.parse("/service/0815/foo/bar"));
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

	private void assertIdentifierAndPath(String expectedServiceIdentifier, String expectedResourcePath, Optional<WebServicePath> servicePath) {

		assertEquals(expectedServiceIdentifier, servicePath.get().getServiceIdentifier());
		assertEquals(expectedResourcePath, servicePath.get().getResourcePath());
	}
}
