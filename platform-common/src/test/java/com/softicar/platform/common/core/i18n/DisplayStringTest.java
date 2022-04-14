package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;

public class DisplayStringTest extends AbstractTest {

	@Test
	public void testAppendWithOptional() {

		DisplayString displayString = new DisplayString().setSeparator("X");

		displayString.append(Optional.of(IDisplayString.create("Foo")));
		displayString.append(Optional.empty());
		displayString.append(Optional.of(IDisplayString.create("Bar")));
		displayString.append(Optional.empty());

		assertEquals("FooXBar", displayString.toString());
	}

	@Test
	public void testEnforceLowerCase() {

		DisplayString displayString = new DisplayString().setEnforceLowerCase();

		displayString.append(IDisplayString.create("Foo"));
		displayString.append(" ");
		displayString.append(IDisplayString.create("Bar"));

		assertEquals("foo bar", displayString.toString());
	}

	@Test
	public void testEnforceLowerCaseWithSeparator() {

		DisplayString displayString = new DisplayString().setEnforceLowerCase().setSeparator("X");

		displayString.append(IDisplayString.create("Foo"));
		displayString.append(IDisplayString.create("Bar"));

		assertEquals("fooXbar", displayString.toString());
	}

	@Test
	public void testEnforceUpperCase() {

		DisplayString displayString = new DisplayString().setEnforceUpperCase();

		displayString.append(IDisplayString.create("Foo"));
		displayString.append(" ");
		displayString.append(IDisplayString.create("Bar"));

		assertEquals("FOO BAR", displayString.toString());
	}

	@Test
	public void testEnforceUpperCaseWithSeparator() {

		DisplayString displayString = new DisplayString().setEnforceUpperCase().setSeparator("x");

		displayString.append(IDisplayString.create("Foo"));
		displayString.append(IDisplayString.create("Bar"));

		assertEquals("FOOxBAR", displayString.toString());
	}
}
