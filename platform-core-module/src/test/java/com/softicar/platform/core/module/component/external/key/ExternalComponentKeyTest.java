package com.softicar.platform.core.module.component.external.key;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import org.junit.Test;

public class ExternalComponentKeyTest extends AbstractTest {

	@Test
	public void testConstructor() {

		var key = new ExternalComponentKey(ExternalComponentType.LIBRARY, "some-name");

		assertEquals(ExternalComponentType.LIBRARY, key.getType());
		assertEquals("some-name", key.getName());
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullType() {

		DevNull.swallow(new ExternalComponentKey(null, "some-name"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithBlankName() {

		DevNull.swallow(new ExternalComponentKey(ExternalComponentType.LIBRARY, " "));
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullName() {

		DevNull.swallow(new ExternalComponentKey(ExternalComponentType.LIBRARY, null));
	}
}
