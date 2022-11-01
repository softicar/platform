package com.softicar.platform.ajax.resource;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class AjaxResourceUrlParserTest extends Asserts {

	@Test
	public void testGetResourceHash() {

		assertHash("ab12df", "http://localhost:80/foo?%s=ab12DF&bar=x".formatted(AjaxResourceUrl.getHashParameterName()));
		assertHash("caffee23", "http://localhost:80/foo?bar=y&%s=Caffee23".formatted(AjaxResourceUrl.getHashParameterName()));
	}

	@Test
	public void testGetResourceId() {

		assertId(123, "http://localhost:80/foo?%s=123&bar=x".formatted(AjaxResourceUrl.getIdParameterName()));
		assertId(123, "http://localhost:80/foo?bar=y&%s=123".formatted(AjaxResourceUrl.getIdParameterName()));
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
