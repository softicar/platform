package com.softicar.platform.dom.elements.select.value.simple.display;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import org.junit.Assert;
import org.junit.Test;

public class DomSimpleValueSelectDefaultDisplayStringFunctionTest extends Assert {

	private final DomSimpleValueSelectDefaultDisplayStringFunction<Object> function;

	public DomSimpleValueSelectDefaultDisplayStringFunctionTest() {

		this.function = new DomSimpleValueSelectDefaultDisplayStringFunction<>();
	}

	@Test
	public void testWithDisplayableValue() {

		IDisplayString output = function.apply(new TestDisplayable());

		assertEquals("I am displayable", output.toString());
	}

	@Test
	public void testWithNonDisplayableValue() {

		IDisplayString output = function.apply(123);

		assertEquals("123", output.toString());
	}

	@Test
	public void testWithNull() {

		IDisplayString output = function.apply(null);

		assertEquals("null", output.toString());
	}

	private static class TestDisplayable implements IDisplayable {

		@Override
		public IDisplayString toDisplay() {

			return IDisplayString.create("I am displayable");
		}
	}
}
