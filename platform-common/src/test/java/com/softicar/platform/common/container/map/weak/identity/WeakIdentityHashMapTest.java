package com.softicar.platform.common.container.map.weak.identity;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Before;
import org.junit.Test;

public class WeakIdentityHashMapTest extends AbstractTest {

	private WeakIdentityHashMap<Object, Integer> map;
	private TestKey key1;
	private TestKey key2;

	@Before
	public void before() {

		this.map = new WeakIdentityHashMap<>();
		this.key1 = new TestKey(1);
		this.key2 = new TestKey(2);
	}

	@Test
	public void testPutAndGet() {

		// check size() and isEmpty()
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());

		// put elements
		map.put(key1, 1);
		map.put(key2, 2);

		// get value of key1 and key2
		assertEquals(1, map.get(key1).intValue());
		assertEquals(2, map.get(key2).intValue());

		// check size() and isEmpty()
		assertEquals(2, map.size());
		assertFalse(map.isEmpty());
	}

	@Test
	public void testGetIfNotContained() {

		assertNull(map.get(key1));
		map.put(key1, 1);
		assertNotNull(map.get(key1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwsOnNullKey() {

		map.put(null, 42);
	}

	@Test
	public void holdsKeysByWeakReferences() {

		// insert key1 and key2
		map.put(key1, 1);
		map.put(key2, 2);
		assertNotNull(map.get(key2));
		assertEquals(2, map.size());

		// remove the only "hard" reference to key2
		key2 = null;

		// loop until key has been collected
		for (int i = 0; i < 10; ++i) {
			if (map.size() == 1) {
				break;
			}

			System.gc();
			System.runFinalization();
			Sleep.sleep(10);
		}

		assertEquals(1, map.size());
		assertEquals(1, map.get(key1).intValue());
		assertNull(map.get(key2));
	}

	@Test
	public void hashesByIdentity() {

		TestKey keyA = new TestKey(13);
		TestKey keyB = new TestKey(13);

		// verify that the keys are equal
		assertEquals(keyA.hashCode(), keyB.hashCode());
		assertTrue(keyA.equals(keyB));
		assertTrue(keyB.equals(keyA));

		map.put(keyA, 1);
		map.put(keyB, 2);

		assertEquals(2, map.size());
		assertEquals(1, map.get(keyA).intValue());
		assertEquals(2, map.get(keyB).intValue());
	}
}
