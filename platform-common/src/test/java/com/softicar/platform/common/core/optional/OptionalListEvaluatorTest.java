package com.softicar.platform.common.core.optional;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;

public class OptionalListEvaluatorTest extends AbstractTest {

	private static final String DEFAULT = "DEFAULT";
	private static final String A = "A";
	private static final String B = "B";

	@Test
	public void testGetFirst() {

		assertEquals(Optional.empty(), createEvaluator().add(Optional.empty()).getFirst());
		assertEquals(Optional.of(A), createEvaluator().add(Optional.of(A)).getFirst());
		assertEquals(Optional.of(A), createEvaluator().add(Optional.of(A)).add(Optional.of(B)).getFirst());
		assertEquals(Optional.of(B), createEvaluator().add(Optional.of(B)).add(Optional.of(A)).getFirst());
	}

	@Test
	public void testGetFirstOrDefault() {

		assertEquals(DEFAULT, createEvaluator().add(Optional.empty()).getFirstOrDefault());
		assertEquals(A, createEvaluator().add(Optional.of(A)).getFirstOrDefault());
		assertEquals(A, createEvaluator().add(Optional.of(A)).add(Optional.of(B)).getFirstOrDefault());
		assertEquals(B, createEvaluator().add(Optional.of(B)).add(Optional.of(A)).getFirstOrDefault());
	}

	private OptionalListEvaluator<String> createEvaluator() {

		return new OptionalListEvaluator<>(DEFAULT);
	}
}
