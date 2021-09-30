package com.softicar.platform.common.code.java;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class IdentifierExtractorTest {

	@Test
	public void extractsSimpleIdentifier() {

		check(Arrays.asList("foo"), "(foo)");
	}

	@Test
	public void ignoresNumbers() {

		check(Arrays.asList("foo"), "foo+1337");
	}

	@Test
	public void extractsSeveralIdentifiers() {

		check(Arrays.asList("foo", "foo_1337"), "foo+foo_1337");
	}

	private void check(List<String> expected, String line) {

		List<String> identifiers = new ArrayList<>();
		new IdentifierExtractor(line, identifiers).extract();
		assertEquals(expected, identifiers);
	}
}
