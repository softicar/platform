package com.softicar.platform.ajax.resource;

import com.softicar.platform.common.io.resource.ResourceUrl;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class AjaxResourceUrlTest extends AbstractTest {

	private static final ResourceHash HASH = new ResourceHash(Hash.SHA1.getHashStringLC("foo"));

	@Test
	public void testWithId() {

		ResourceUrl url = new AjaxResourceUrl(88).concat("&bar=42");

		assertEquals("?resourceId=88&bar=42", url.toString());
	}

	@Test
	public void testWithHash() {

		ResourceUrl url = new AjaxResourceUrl(HASH).concat("&bar=42");

		assertEquals("?resourceHash=" + HASH + "&bar=42", url.toString());
	}
}
