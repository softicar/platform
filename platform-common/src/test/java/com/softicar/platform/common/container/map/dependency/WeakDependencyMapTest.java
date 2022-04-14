package com.softicar.platform.common.container.map.dependency;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import org.junit.Test;

public class WeakDependencyMapTest extends AbstractTest {

	private final String A = "A";
	private final String B = "B";
	private final String C = "C";
	private final String D = "D";
	private final String E = "E";

	private final WeakDependencyMap<String> dependencyMap;

	public WeakDependencyMapTest() {

		this.dependencyMap = new WeakDependencyMap<>();

		dependencyMap.addDependency(A, B);
		dependencyMap.addDependency(B, C);
		dependencyMap.addDependency(B, E);
		dependencyMap.addDependency(D, E);
	}

	@Test
	public void test() {

		assertClosure(Arrays.asList(), Arrays.asList());
		assertClosure(Arrays.asList(A, B, C, E), Arrays.asList(A));
		assertClosure(Arrays.asList(A, B, C, D, E), Arrays.asList(A, D));
		assertClosure(Arrays.asList(B, C, E), Arrays.asList(B, C));
		assertClosure(Arrays.asList(C), Arrays.asList(C));
		assertClosure(Arrays.asList(D, E), Arrays.asList(D));
		assertClosure(Arrays.asList(E), Arrays.asList(E));
	}

	private void assertClosure(Collection<String> expectedElements, Collection<String> seeds) {

		Collection<String> closure = dependencyMap.getTransitiveClosure(seeds);
		assertEquals(new TreeSet<>(expectedElements), new TreeSet<>(closure));
	}
}
