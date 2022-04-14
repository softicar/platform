package com.softicar.platform.common.container.cache;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CacheTest extends AbstractTest {

	@Test
	public void test() {

		int size = 2;

		Cache<String, String> cache = new Cache<>(size, "AnimalCache");
		cache.put("cow", "moo");
		cache.put("cat", "meow");

		assertTrue(cache.containsKey("cow"));

		cache.put("goat", "baah");
		cache.put("dog", "woof");

		assertNull(cache.get("cat"));
		assertEquals("woof", cache.get("dog"));
		assertNull(cache.get("cow"));
		assertEquals("baah", cache.get("goat"));
	}
}
