package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class WebServicePathTest extends Asserts {

	@Test
	public void testParseWithValidPath() {

		assertEquals("[0815,/]", assertOne(WebServicePath.parse("/service/0815/")).toString());
		assertEquals("[0815,/foo/bar]", assertOne(WebServicePath.parse("/service/0815/foo/bar")).toString());
	}

	@Test
	public void testParseWithIllegalPath() {

		assertEmpty(WebServicePath.parse(null));
		assertEmpty(WebServicePath.parse(""));
		assertEmpty(WebServicePath.parse("/"));
		assertEmpty(WebServicePath.parse("/ser/"));
	}
}
