package com.softicar.platform.common.io.xml.parser;

import com.softicar.platform.common.core.utils.DevNull;
import org.junit.Assert;
import org.junit.Test;

public class XmlTagTest extends Assert {

	@Test
	public void testCreateWithoutIndex() {

		var tag = XmlTag.create("foo");
		assertEquals("foo", tag.getName());
		assertEquals(0, tag.getIndex());
	}

	@Test
	public void testCreateWithIndex() {

		var tag = XmlTag.create("foo", 5);
		assertEquals("foo", tag.getName());
		assertEquals(5, tag.getIndex());
	}

	@Test
	public void testCreateWithIndexedString() {

		var tag = XmlTag.create("foo:22");
		assertEquals("foo", tag.getName());
		assertEquals(22, tag.getIndex());
	}

	@Test(expected = NullPointerException.class)
	public void testCreateWithNullTag() {

		DevNull.swallow(XmlTag.create(null, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithEmptyTag() {

		DevNull.swallow(XmlTag.create("", 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNegativeIndex() {

		DevNull.swallow(XmlTag.create("foo", -1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithIndexedStringAndTooManyTokens() {

		DevNull.swallow(XmlTag.create("foo:11:22"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithIndexedStringAndIllegalIndex() {

		DevNull.swallow(XmlTag.create("foo:xxx"));
	}

	@Test
	public void testGetName() {

		var tag = XmlTag.create("foo", 5);
		assertEquals("foo", tag.getName());
	}

	@Test
	public void testGetIndex() {

		var tag = XmlTag.create("foo", 5);
		assertEquals(5, tag.getIndex());
	}
}
