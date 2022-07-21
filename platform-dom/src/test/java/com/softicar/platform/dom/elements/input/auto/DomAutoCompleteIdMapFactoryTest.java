package com.softicar.platform.dom.elements.input.auto;

import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

public class DomAutoCompleteIdMapFactoryTest extends AbstractDomAutoCompleteDefaultInputEngineTest {

	@Test
	public void testWithProperIds() {

		addTestValue("A [11]", 1);
		addTestValue("B [12]", 2);
		addTestValue("C [13]", 3);
		addTestValue("D [14]", 4);

		var idMap = new DomAutoCompleteIdMapFactory<>(createMap()).create();

		assertEquals("{11=1, 12=2, 13=3, 14=4}", assertOne(idMap).toString());
	}

	@Test
	public void testWithRedundantIds() {

		addTestValue("A [11]", 1);
		addTestValue("B [12]", 2);
		addTestValue("C [13]", 3);
		addTestValue("D [11]", 4);

		var idMap = new DomAutoCompleteIdMapFactory<>(createMap()).create();

		assertEmpty(idMap);
	}

	@Test
	public void testWithMissingId() {

		addTestValue("A [11]", 1);
		addTestValue("B [  ]", 2);
		addTestValue("C [13]", 3);
		addTestValue("D [14]", 4);

		var idMap = new DomAutoCompleteIdMapFactory<>(createMap()).create();

		assertEmpty(idMap);
	}

	private Map<String, TestValue> createMap() {

		return values//
			.stream()
			.collect(Collectors.toMap(it -> it.toDisplay().toString(), it -> it));
	}
}
