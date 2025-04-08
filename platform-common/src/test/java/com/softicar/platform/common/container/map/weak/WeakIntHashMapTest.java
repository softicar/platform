package com.softicar.platform.common.container.map.weak;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.testing.AbstractTest;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link WeakIntHashMap}.
 *
 * @author Oliver Richers
 */
public class WeakIntHashMapTest extends AbstractTest {

	private static final int MAX_COLLECT_LOOP = 100;
	private static final int COLLECT_SLEEP_MILLIS = 10;
	private static TestList testList = new TestList();

	@Test
	public void testCollect() {

		// fill maps
		int n = 200000;
		WeakIntHashMap<TestValue> map = new WeakIntHashMap<>();
		HashMap<Integer, TestValue> verify = new HashMap<>();
		for (int i = 0; i < n; ++i) {
			int key = i;//(int) (Math.random()*range);
			TestValue value = new TestValue(key);
			verify.put(value.getID(), value);
			map.put(key, value);
		}

		// verify that nothing gets collected
		collectOnce();
		map.collect();
		Assert.assertEquals(map.size(), verify.size());
		Log.finfo("StepAverage: %s", map.getStepAverage());

		// remove elements
		int size = map.size();
		for (int i = 0; i < n / 2; ++i) {
			int key = i;//(int) (Math.random()*range);
			TestValue value2 = map.get(key);
			TestValue value1 = verify.remove(key);
			Assert.assertTrue(value1 == null || value1 == value2);
		}

		// wait for elements to be collected
		for (int i = 0; i < MAX_COLLECT_LOOP; ++i) {
			if (map.size() == verify.size()) {
				break;
			}

			collectOnce();
			map.collect();
		}
		Log.finfo("size: %s -> %s", size, map.size());
		Log.finfo("StepAverage: %s", map.getStepAverage());
		Assert.assertEquals(map.size(), verify.size());

		// unlink map
		verify = null;
		for (int i = 0; i < MAX_COLLECT_LOOP; ++i) {
			if (map.size() == 0) {
				break;
			}

			collectOnce();
			map.collect();
		}
		Assert.assertEquals(map.size(), 0);
	}

	@Test
	public void testPutAndGet() {

		LogLevel.VERBOSE.set();

		// test put and get
		int n = 1000;
		int range = 3000;
		int replaces = 0;
		WeakIntHashMap<TestValue> map = new WeakIntHashMap<>();
		HashMap<Integer, TestValue> verify = new HashMap<>();
		for (int i = 0; i < n; ++i) {
			int key = (int) (Math.random() * range);
			TestValue value = new TestValue(key);
			TestValue prev1 = verify.put(value.getID(), value);
			TestValue prev2 = map.put(key, value);

			if (prev1 != null) {
				++replaces;
				Assert.assertSame(prev1, prev2);
			}

			Assert.assertEquals(verify.size(), map.size());
			Assert.assertSame(value, map.get(value.getID()));
		}
		Log.finfo("replaces: %s/%s", replaces, n);
		Log.finfo("step average: %s", map.getStepAverage());

		// test iterator
		HashSet<Integer> set = new HashSet<>();
		for (TestValue value: map) {
			set.add(value.getID());
		}
		Assert.assertEquals(map.size(), set.size());

		// test remove
		for (Integer key: verify.keySet()) {
			int size = map.size();
			map.remove(key);
			Assert.assertEquals(map.size(), size - 1);
		}
		Assert.assertEquals(map.size(), 0);
		Assert.assertTrue(map.isEmpty());
	}

	// ******************************************************************************** //
	// * PUT AND SIZE                                                                 * //
	// ******************************************************************************** //

	@Test
	public void testPutAndSize() {

		HashMap<Integer, WeakReference<TestValue>> map1 = new HashMap<>();
		WeakIntHashMap<TestValue> map2 = new WeakIntHashMap<>();
		for (int i = 0; i < 100000; ++i) {
			TestValue value = testList.get(i);
			map1.put(value.getID(), new WeakReference<>(value));
			map2.put(value.getID(), value);
			Assert.assertEquals(map1.size(), map2.size());
		}

		for (int i = 0; i < MAX_COLLECT_LOOP; ++i) {
			if (map1.size() == map2.size()) {
				break;
			}
			collectOnce();
			map2.collect();
		}

		Assert.assertEquals(map1.size(), map2.size());
	}

	public static void collectOnce() {

		System.gc();
		Sleep.sleep(COLLECT_SLEEP_MILLIS);
	}
}
