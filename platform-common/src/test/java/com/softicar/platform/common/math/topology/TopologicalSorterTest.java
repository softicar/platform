package com.softicar.platform.common.math.topology;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import org.junit.Test;

public class TopologicalSorterTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private static final String D = "D";
	private static final String E = "E";
	private static final String F = "F";

	@Test
	public void test() {

		Collection<String> sorted = new TopologicalSorter<>(Arrays.asList(A, B, C, D, E, F))//
			.addEdge(A, B)
			.addEdge(B, C)
			.addEdge(D, C)
			.addEdge(E, A)
			.addEdge(F, B)
			.addEdge(F, D)
			.getSorted();

		IndexHashMap<String> indexMap = new IndexHashMap<>(sorted);

		assertBefore(indexMap, A, B);
		assertBefore(indexMap, B, C);
		assertBefore(indexMap, D, C);
		assertBefore(indexMap, E, A);
		assertBefore(indexMap, F, B);
		assertBefore(indexMap, F, D);
	}

	private void assertBefore(IndexHashMap<String> indexMap, String x, String y) {

		assertTrue(indexMap.getIndex(x) < indexMap.getIndex(y));
	}

	private static class IndexHashMap<T> implements Comparator<T> {

		private final HashMap<T, Integer> map;

		public IndexHashMap(Collection<? extends T> values) {

			this.map = new HashMap<>();

			int index = 0;
			for (T column: values) {
				map.put(column, index);
				index++;
			}
		}

		public Integer getIndex(T value) {

			return map.get(value);
		}

		@Override
		public int compare(T left, T right) {

			return getIndex(left).compareTo(getIndex(right));
		}
	}
}
