package com.softicar.platform.emf.data.table.filter.string;

import org.junit.Assert;
import org.junit.Test;

public class FilterTextNormalizerTest extends Assert {

	@Test
	public void test() {

		assertNull(filter(null));

		assertEquals("", filter(""));
		assertEquals("", filter(" \t\n"));

		assertEquals("foo", filter("foo"));
		assertEquals("foo", filter(" foo\n"));
		assertEquals("foo", filter("\u00A0foo"));
		assertEquals("ÜAä", filter(" ÜAä\n\u00A0"));

		assertEquals("foo  bar", filter(" foo \nbar\n\u00A0"));
	}

	private Object filter(String string) {

		return new FilterTextNormalizer(string).getNormalized();
	}
}
