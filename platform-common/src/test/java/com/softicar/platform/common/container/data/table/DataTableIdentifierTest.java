package com.softicar.platform.common.container.data.table;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DataTableIdentifierTest extends AbstractTest {

	@Test
	public void testIsPresent() {

		assertFalse(new DataTableIdentifier("").isPresent());
		assertFalse(new DataTableIdentifier("  ").isPresent());
		assertTrue(new DataTableIdentifier("foo").isPresent());
	}

	@Test
	public void testGetHash() {

		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", new DataTableIdentifier("").getHash());
		assertEquals("0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33", new DataTableIdentifier("foo").getHash());
		assertEquals("336d1b3d72e061b98b59d6c793f6a8da217a727a", new DataTableIdentifier("foo.bar").getHash());
	}

	@Test
	public void testToString() {

		assertEquals("", new DataTableIdentifier("").toString());
		assertEquals("", new DataTableIdentifier("  ").toString());
		assertEquals("x", new DataTableIdentifier(" x ").toString());
		assertEquals("foo", new DataTableIdentifier("foo").toString());
		assertEquals("foo.bar", new DataTableIdentifier("foo.bar").toString());
	}
}
