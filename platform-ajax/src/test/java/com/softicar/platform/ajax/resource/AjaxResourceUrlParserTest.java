package com.softicar.platform.ajax.resource;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class AjaxResourceUrlParserTest extends Asserts {

	@Test
	public void testGetResourceHash() {

		assertHash("ab12df", "http://localhost:80/foo?resourceHash=ab12DF&bar=x");
		assertHash("caffee23", "http://localhost:80/foo?bar=y&resourceHash=Caffee23");
	}

	@Test
	public void testGetResourceId() {

		assertId(123, "http://localhost:80/foo?resourceId=123&bar=x");
		assertId(123, "http://localhost:80/foo?bar=y&resourceId=123");
	}

	private void assertHash(String expectedHash, String url) {

		var hash = new AjaxResourceUrlParser(url).getResourceHash();

		assertEquals(expectedHash, assertOne(hash).toString());
	}

	private void assertId(int expectedId, String url) {

		var id = new AjaxResourceUrlParser(url).getResourceId();

		assertEquals(expectedId, assertOne(id));
	}
}
