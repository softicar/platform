package com.softicar.platform.core.module.component.external;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.component.external.key.ExternalComponentKey;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import org.junit.Assert;
import org.junit.Test;

public class ExternalComponentTest extends Assert {

	@Test
	public void testConstructor() {

		var key = new ExternalComponentKey(ExternalComponentType.LIBRARY, "some-identifier");

		IExternalComponent library = new ExternalComponent(key);

		assertEquals(ExternalComponentType.LIBRARY, library.getKey().getType());
		assertEquals(ExternalComponentType.LIBRARY, library.getType());
		assertEquals("some-identifier", library.getKey().getName());
		assertEquals("some-identifier", library.getName());
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullKey() {

		DevNull.swallow(new ExternalComponent(null));
	}

	// -------------------------------- setDescription -------------------------------- //

	@Test
	public void testSetDescriptionWithSingleSupplier() {

		var library = createComponent();
		library.setDescription(() -> "foo");

		assertEquals("foo", library.getDescription());
	}

	@Test
	public void testSetDescriptionWithSingleSupplierOfNull() {

		var library = createComponent();
		library.setDescription(() -> null);

		assertNull(library.getDescription());
	}

	@Test
	public void testSetDescriptionWithMultipleSuppliers() {

		var library = createComponent();
		library.setDescription(() -> "foo", () -> "bar");

		assertEquals("foo", library.getDescription());
	}

	@Test
	public void testSetDescriptionWithMultipleSuppliersAndFirstNullSupplier() {

		var library = createComponent();
		library.setDescription(() -> null, () -> "bar");

		assertEquals("bar", library.getDescription());
	}

	// -------------------------------- setVersion -------------------------------- //

	@Test
	public void testSetVersionWithSingleSupplier() {

		var library = createComponent();
		library.setVersion(() -> "foo");

		assertEquals("foo", library.getVersion());
	}

	@Test
	public void testSetVersionWithSingleSupplierOfNull() {

		var library = createComponent();
		library.setVersion(() -> null);

		assertNull(library.getVersion());
	}

	@Test
	public void testSetVersionWithMultipleSuppliers() {

		var library = createComponent();
		library.setVersion(() -> "foo", () -> "bar");

		assertEquals("foo", library.getVersion());
	}

	@Test
	public void testSetVersionWithMultipleSuppliersAndFirstNullSupplier() {

		var library = createComponent();
		library.setVersion(() -> null, () -> "bar");

		assertEquals("bar", library.getVersion());
	}

	// -------------------------------- setLicense -------------------------------- //

	@Test
	public void testSetLicenseWithSingleSupplier() {

		var library = createComponent();
		library.setLicense(() -> "foo");

		assertEquals("foo", library.getLicense());
	}

	@Test
	public void testSetLicenseWithSingleSupplierOfNull() {

		var library = createComponent();
		library.setLicense(() -> null);

		assertNull(library.getLicense());
	}

	@Test
	public void testSetLicenseWithMultipleSuppliers() {

		var library = createComponent();
		library.setLicense(() -> "foo", () -> "bar");

		assertEquals("foo", library.getLicense());
	}

	@Test
	public void testSetLicenseWithMultipleSuppliersAndFirstNullSupplier() {

		var library = createComponent();
		library.setLicense(() -> null, () -> "bar");

		assertEquals("bar", library.getLicense());
	}

	// -------------------------------- private -------------------------------- //

	private ExternalComponent createComponent() {

		return new ExternalComponent(new ExternalComponentKey(ExternalComponentType.LIBRARY, "some-identifier"));
	}
}
