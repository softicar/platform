package com.softicar.platform.common.io.hash;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import org.junit.Assert;
import org.junit.Test;

public class Sha1HashTest extends Assert {

	private static final String HASH = "abcdef0123456789abcdef0123456789abcdef01";
	private static final String OTHER_HASH = "abcdef0123456789abcdef0123456789abcdef02";

	@Test
	public void testEquals() {

		assertTrue(new Sha1Hash(HASH).equals(new Sha1Hash(HASH)));
		assertTrue(new Sha1Hash(HASH).equals(new Sha1Hash(HASH.toUpperCase())));
		assertFalse(new Sha1Hash(HASH).equals(new Sha1Hash(OTHER_HASH)));
	}

	@Test
	public void testHashCode() {

		assertEquals(new Sha1Hash(HASH).hashCode(), HASH.hashCode());
	}

	@Test
	public void testCompareTo() {

		assertEquals(0, new Sha1Hash(HASH).compareTo(new Sha1Hash(HASH)));
		assertEquals(-1, new Sha1Hash(HASH).compareTo(new Sha1Hash(OTHER_HASH)));
		assertEquals(+1, new Sha1Hash(OTHER_HASH).compareTo(new Sha1Hash(HASH)));
	}

	@Test
	public void testToString() {

		assertEquals(HASH, new Sha1Hash(HASH).toString());
		assertEquals(HASH, new Sha1Hash(HASH.toUpperCase()).toString());
	}

	@Test
	public void testWithByteArray() {

		byte[] bytes = Hexadecimal.getBytesFromHexString(HASH);
		assertEquals(HASH, new Sha1Hash(bytes).toString());
	}

	@Test(expected = NullPointerException.class)
	public void testNullHash() {

		DevNull.swallow(new Sha1Hash((String) null));
	}

	@Test(expected = RuntimeException.class)
	public void testIllegalHashLength() {

		DevNull.swallow(new Sha1Hash("foo"));
	}

	@Test(expected = RuntimeException.class)
	public void testIllegalHashCharacters() {

		DevNull.swallow(new Sha1Hash(HASH.substring(0, 39) + "z"));
	}
}
