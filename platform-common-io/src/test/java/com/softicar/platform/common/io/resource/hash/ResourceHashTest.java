package com.softicar.platform.common.io.resource.hash;

import org.junit.Assert;
import org.junit.Test;

public class ResourceHashTest extends Assert {

	@Test
	public void testEquals() {

		assertTrue(new ResourceHash("FOO").equals(new ResourceHash("foo")));
		assertFalse(new ResourceHash("FOO").equals(new ResourceHash("BAR")));
	}

	@Test
	public void testHashCode() {

		int code1 = new ResourceHash("FOO").hashCode();
		int code2 = new ResourceHash("foo").hashCode();

		assertEquals(code1, code2);
	}

	@Test
	public void testToString() {

		assertEquals("lower", new ResourceHash("lower").toString());
		assertEquals("upper", new ResourceHash("UPPER").toString());
	}
}
