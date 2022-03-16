package com.softicar.platform.common.container.map.weak.map.adapter;

import java.util.HashMap;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class WeakValueMapAdapterTest extends Assert {

	private static final String A = "A";
	private static final String B = "B";
	private final WeakValueMapAdapter<Object, Object> map;

	public WeakValueMapAdapterTest() {

		map = new WeakValueMapAdapter<>(HashMap::new);
	}

	@Test
	public void testGetWithEmptyMap() {

		assertFalse(map.get(A).isPresent());
	}

	@Test
	public void testPutAndGet() {

		map.put(A, B);

		Optional<Object> value = map.get(A);
		assertTrue(value.isPresent());
		assertSame(B, value.get());
	}

	@Test
	public void testRemoveAndGet() {

		map.put(A, B);
		map.remove(A);

		assertFalse(map.get(A).isPresent());
	}

	@Test(timeout = 1000)
	public void testCollectionOfEntries() {

		map.put(A, new Object());

		while (map.get(A).isPresent()) {
			System.gc();
		}

		assertFalse(map.get(A).isPresent());
	}
}
