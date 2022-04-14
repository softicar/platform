package com.softicar.platform.dom.attribute;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DomAttributeTest extends AbstractTest {

	@Test
	public void ctorConvertsNameToLowerCase() {

		DomAttribute attribute = new DomAttribute("AliGN", "RIghT", true);
		assertEquals("align", attribute.getName());
	}

	@Test
	public void ctorLeavesValueUntouched() {

		DomAttribute attribute = new DomAttribute("AliGN", "RIghT", true);
		assertEquals("RIghT", attribute.getValue());
	}

	@Test
	public void hashCodeReturnsSameValueWhenEqual() {

		DomAttribute attribute1 = new DomAttribute("AliGN", "right", true);
		DomAttribute attribute2 = new DomAttribute("align", "right", true);

		assertEquals(attribute1.hashCode(), attribute2.hashCode());
	}

	@Test
	public void equalsIfSameValueAndQuoting() {

		DomAttribute attribute1 = new DomAttribute("AliGN", "right", true);
		DomAttribute attribute2 = new DomAttribute("align", "right", true);

		assertEquals(attribute1, attribute2);
	}

	@Test
	public void notEqualIfDifferentlyQuoted() {

		DomAttribute attribute1 = new DomAttribute("align", "right", true);
		DomAttribute attribute2 = new DomAttribute("align", "right", false);

		assertFalse(attribute1.equals(attribute2));
	}

	@Test
	public void returnsQuotedJavascriptValueIfRequested() {

		DomAttribute attribute = new DomAttribute("align", "right", true);

		assertEquals("'right'", attribute.getValue_JS());
	}

	@Test
	public void returnsUnquotedJavascriptValueIfRequested() {

		DomAttribute attribute = new DomAttribute("align", "right", false);

		assertEquals("right", attribute.getValue_JS());
	}
}
