package com.softicar.platform.common.core.properties;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class SystemPropertyFactoryTest extends Assert {

	// ---------------- Property Name ---------------- //

	@Test
	public void testGetPropertyNameWithoutPrefix() {

		IProperty<?> property = createFactory().createStringProperty("foo.bar", null);
		assertEquals("foo.bar", property.getPropertyName().toString());
	}

	@Test
	public void testGetPropertyNameWithPrefix() {

		IProperty<?> property = createFactory("prefix").createStringProperty("foo.bar", null);
		assertEquals("prefix.foo.bar", property.getPropertyName().toString());
	}

	// ---------------- Property Value (String) ---------------- //

	@Test
	public void testCreateStringPropertyAndGetValueWithDefault() {

		IProperty<String> property = createFactory().createStringProperty("foo.bar", "someValue");
		assertEquals("someValue", property.getValue());
	}

	@Test
	public void testCreateStringPropertyAndGetValueWithDefaultNull() {

		IProperty<String> property = createFactory().createStringProperty("foo.bar", null);
		assertNull(property.getValue());
	}

	@Test
	public void testCreateStringPropertyAndGetValueOfCommonJvmProperty() {

		IProperty<String> property = createFactory().createStringProperty("os.arch", null);
		assertEquals(System.getProperty("os.arch"), property.getValue());
	}

	// ---------------- Property Value (Integer) ---------------- //

	@Test
	public void testCreateIntegerPropertyAndGetValueWithDefault() {

		IProperty<Integer> property = createFactory().createIntegerProperty("foo.bar", 10);
		assertEquals(Integer.valueOf(10), property.getValue());
	}

	@Test
	public void testCreateIntegerPropertyAndGetValueWithDefaultNull() {

		IProperty<Integer> property = createFactory().createIntegerProperty("foo.bar", null);
		assertNull(property.getValue());
	}

	// ---------------- Property Value (Boolean) ---------------- //

	@Test
	public void testCreateBooleanPropertyAndGetValueWithDefault() {

		IProperty<Boolean> property = createFactory().createBooleanProperty("foo.bar", true);
		assertEquals(true, property.getValue());
	}

	@Test
	public void testCreateBooleanPropertyAndGetValueWithDefaultNull() {

		IProperty<Boolean> property = createFactory().createBooleanProperty("foo.bar", null);
		assertNull(property.getValue());
	}

	// ---------------- Property Value (BigDecimal) ---------------- //

	@Test
	public void testCreateBigDecimalPropertyAndGetValueWithDefault() {

		IProperty<BigDecimal> property = createFactory().createBigDecimalProperty("foo.bar", new BigDecimal("123.45"));
		assertEquals(new BigDecimal("123.45"), property.getValue());
	}

	@Test
	public void testCreateBigDecimalPropertyAndGetValueWithDefaultNull() {

		IProperty<BigDecimal> property = createFactory().createBigDecimalProperty("foo.bar", null);
		assertNull(property.getValue());
	}

	// ---------------- Internals ---------------- //

	private SystemPropertyFactory createFactory() {

		return new SystemPropertyFactory();
	}

	private SystemPropertyFactory createFactory(String namePrefix) {

		return new SystemPropertyFactory(namePrefix);
	}
}
