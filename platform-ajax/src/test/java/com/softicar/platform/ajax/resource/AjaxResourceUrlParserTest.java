package com.softicar.platform.ajax.resource;

import com.softicar.platform.common.io.resource.hash.ResourceHash;
import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class AjaxResourceUrlParserTest extends Asserts {

	@Test
	public void testGetResourceHash() {

		var hash = new AjaxResourceUrlParser("http://localhost:80/foo?resourceHash=ab12DF&bar").getResourceHash();

		assertEquals(new ResourceHash("ab12DF"), assertOne(hash));
	}

	@Test
	public void testGetResourceId() {

		var id = new AjaxResourceUrlParser("http://localhost:80/foo?resourceId=123&bar").getResourceId();

		assertEquals(123, assertOne(id));
	}
}
