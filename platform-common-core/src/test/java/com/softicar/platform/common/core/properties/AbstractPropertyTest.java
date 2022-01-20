package com.softicar.platform.common.core.properties;

import java.util.function.Function;
import org.junit.Assert;
import org.junit.Test;

public class AbstractPropertyTest extends Assert {

	private static final String NORMAL = "normal";
	private static final String WHITESPACED = "  whitespaced  ";
	private static final String NUMERIC = "123";
	private static final String SENTENCE = "Its a me";

	@Test
	public void testNormalString() {

		TestProperty property = new TestProperty(NORMAL);
		assertEquals(NORMAL, property.getValue());
	}

	@Test
	public void testWhitespacedString() {

		TestProperty property = new TestProperty(WHITESPACED);
		assertEquals(WHITESPACED.trim(), property.getValue());
	}

	@Test
	public void testNumericString() {

		TestProperty property = new TestProperty(NUMERIC);
		assertEquals(NUMERIC, property.getValue());
	}

	@Test
	public void testSentenceString() {

		TestProperty property = new TestProperty(SENTENCE);
		assertEquals(SENTENCE, property.getValue());
	}

	private class TestProperty extends AbstractProperty<String> {

		private final String value;

		protected TestProperty(String value) {

			super(new PropertyName(value), "", Function.identity());
			this.value = value;
		}

		@Override
		protected String getValueString(PropertyName propertyName) {

			return value;
		}
	}
}
