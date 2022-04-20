package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class DisplayStringJoiningCollectorTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";

	@Test
	public void testWithEmtpyInput() {

		IDisplayString result = createList()//
			.stream()
			.collect(new DisplayStringJoiningCollector(IDisplayString.create(",")));

		assertEquals("", result.toString());
	}

	@Test
	public void testWithEmtpySeparator() {

		IDisplayString result = createList(A, B, A)//
			.stream()
			.collect(new DisplayStringJoiningCollector(IDisplayString.EMPTY));

		assertEquals(A + B + A, result.toString());
	}

	@Test
	public void testWithSeparator() {

		IDisplayString result = createList(A, B, A)//
			.stream()
			.collect(new DisplayStringJoiningCollector(IDisplayString.create(",")));

		assertEquals(A + "," + B + "," + A, result.toString());
	}

	private List<IDisplayString> createList(String...elements) {

		return Arrays//
			.asList(elements)
			.stream()
			.map(IDisplayString::create)
			.collect(Collectors.toList());
	}
}
