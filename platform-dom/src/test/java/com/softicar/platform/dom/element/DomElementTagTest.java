package com.softicar.platform.dom.element;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DomElementTagTest extends AbstractTest {

	@Test
	public void testNames() {

		for (DomElementTag tag: DomElementTag.values()) {
			assertEquals(tag.name(), tag.getName().toUpperCase());
		}
	}
}
