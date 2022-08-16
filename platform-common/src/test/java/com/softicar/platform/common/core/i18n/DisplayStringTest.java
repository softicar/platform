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
}
