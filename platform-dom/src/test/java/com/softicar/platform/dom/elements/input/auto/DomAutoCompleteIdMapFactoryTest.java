package com.softicar.platform.dom.elements.input.auto;

import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

public class DomAutoCompleteIdMapFactoryTest extends AbstractDomAutoCompleteDefaultInputEngineTest {

	@Test
	public void testWithProperIds() {

		addTestElement("A [11]", 1);
		addTestElement("B [12]", 2);
		addTestElement("C [13]", 3);
		addTestElement("D [14]", 4);

		var idMap = new DomAutoCompleteIdMapFactory<>(createMap()).create();

		assertEquals("{11=1, 12=2, 13=3, 14=4}", assertOne(idMap).toString());
	}

	@Test
	public void testWithRedundantIds() {

		addTestElement("A [11]", 1);
		addTestElement("B [12]", 2);
		addTestElement("C [13]", 3);
		addTestElement("D [11]", 4);

		var idMap = new DomAutoCompleteIdMapFactory<>(createMap()).create();

		assertEmpty(idMap);
	}

	@Test
	public void testWithMissingId() {

		addTestElement("A [11]", 1);
		addTestElement("B [  ]", 2);
		addTestElement("C [13]", 3);
		addTestElement("D [14]", 4);

		var idMap = new DomAutoCompleteIdMapFactory<>(createMap()).create();

		assertEmpty(idMap);
	}

	private Map<String, TestElement> createMap() {

		return elements//
			.stream()
			.collect(Collectors.toMap(it -> it.toDisplay().toString(), it -> it));
	}
}
