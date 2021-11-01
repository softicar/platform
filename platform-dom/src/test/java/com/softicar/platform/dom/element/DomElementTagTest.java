package com.softicar.platform.dom.element;

import org.junit.Assert;
import org.junit.Test;

public class DomElementTagTest extends Assert {

	@Test
	public void testNames() {

		for (DomElementTag tag: DomElementTag.values()) {
			assertEquals(tag.name(), tag.getName().toUpperCase());
		}
	}
}
