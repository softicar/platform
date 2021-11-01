package com.softicar.platform.common.container.map.weak;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.LogLevel;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link WeakIntHashMap}.
 *
 * @author Oliver Richers
 */
public class WeakIntHashMapSpeedTest {

	private static TestList testList = new TestList();

	private static void put(ArrayList<WeakReference<TestValue>> list, TestValue value) {

		for (int i = 0; i < list.size(); ++i) {
			WeakReference<TestValue> reference = list.get(i);
			TestValue tmp = reference.get();
			if (tmp != null && tmp.getID() == value.getID()) {
				list.set(i, new WeakReference<>(value));
				return;
			}
		}
		list.add(new WeakReference<>(value));
	}

	private static void testArray(int n) {

		long a = System.nanoTime();
		ArrayList<WeakReference<TestValue>> map = new ArrayList<>();
		for (int i = 0; i < n; ++i) {
			TestValue value = testList.get(i);
			put(map, value);
		}
		long b = System.nanoTime();
		Log.finfo("testArray(%s/%s): %5.2f", map.size(), n, (b - a) / 1000000.0);
	}

	private static void testWeakIntHashMap(int n) {

		long a = System.nanoTime();
		WeakIntHashMap<TestValue> map = new WeakIntHashMap<>();
		for (int i = 0; i < n; ++i) {
			TestValue value = testList.get(i);
			map.put(value.getID(), value);
		}
		long b = System.nanoTime();

		int size = map.size();
		Log.fverbose("StepAverage: %s", map.getStepAverage());
		Log.fverbose("ClusterAverage: %s", map.getClusterAverage());

		long c = System.nanoTime();
		for (int i = 0; i < n; ++i) {
			map.remove(testList.get(i).getID());
		}
		long d = System.nanoTime();
		Assert.assertEquals(map.size(), 0);
		Log.finfo("WeakIntHashMap(%s/%s): %5.2f %5.2f", size, n, (b - a) / 1000000.0, (d - c) / 1000000.0);
	}

	private static void testHashMap(int n) {

		long a = System.nanoTime();
		HashMap<Integer, WeakReference<TestValue>> map = new HashMap<>();
		for (int i = 0; i < n; ++i) {
			TestValue value = testList.get(i);
			map.put(value.getID(), new WeakReference<>(value));
		}
		long b = System.nanoTime();

		int size = map.size();

		long c = System.nanoTime();
		for (int i = 0; i < n; ++i) {
			map.remove(testList.get(i).getID());
		}
		long d = System.nanoTime();
		Assert.assertEquals(map.size(), 0);
		Log.finfo("HashMap(%s/%s): %5.2f %5.2f", size, n, (b - a) / 1000000.0, (d - c) / 1000000.0);
	}

	private static void testTreeMap(int n) {

		long a = System.nanoTime();
		TreeMap<Integer, WeakReference<TestValue>> map = new TreeMap<>();
		for (int i = 0; i < n; ++i) {
			TestValue value = testList.get(i);
			map.put(value.getID(), new WeakReference<>(value));
		}
		long b = System.nanoTime();

		int size = map.size();

		long c = System.nanoTime();
		for (int i = 0; i < n; ++i) {
			map.remove(testList.get(i).getID());
		}
		long d = System.nanoTime();
		Assert.assertEquals(map.size(), 0);
		Log.finfo("TreeMap(%s/%s): %5.2f %5.2f", size, n, (b - a) / 1000000.0, (d - c) / 1000000.0);
	}

	@Test
	public void testSpeed() {

		LogLevel.INFO.set();

		WeakIntHashMapTest.collectOnce();
		for (int i = 1; i <= 10000; i *= 10) {
			testArray(i);
		}
		WeakIntHashMapTest.collectOnce();
		for (int i = 1; i <= 1000000; i *= 10) {
			testHashMap(i);
		}
		WeakIntHashMapTest.collectOnce();
		for (int i = 1; i <= 1000000; i *= 10) {
			testTreeMap(i);
		}
		WeakIntHashMapTest.collectOnce();
		for (int i = 1; i <= 1000000; i *= 10) {
			testWeakIntHashMap(i);
		}
		WeakIntHashMapTest.collectOnce();
	}
}
