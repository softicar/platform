package com.softicar.platform.core.module.component.external.release;

import com.softicar.platform.core.module.component.external.key.ExternalComponentKey;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import org.junit.Assert;
import org.junit.Test;

public class ExternalComponentReleaseTest extends Assert {

	@Test
	public void test() {

		var release = create(ExternalComponentType.LIBRARY, "foo-bar", "1.2.3");

		assertEquals(ExternalComponentType.LIBRARY, release.getKey().getType());
		assertEquals("foo-bar", release.getKey().getName());
		assertEquals("1.2.3", release.getVersion());
	}

	@Test
	public void testWithNullVersion() {

		var release = create(ExternalComponentType.LIBRARY, "foo-bar", null);

		assertEquals(ExternalComponentType.LIBRARY, release.getKey().getType());
		assertEquals("foo-bar", release.getKey().getName());
		assertNull(release.getVersion());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithBlankName() {

		create(ExternalComponentType.LIBRARY, " ", "1.2.3");
	}

	@Test(expected = NullPointerException.class)
	public void testWithNullName() {

		create(ExternalComponentType.LIBRARY, null, "1.2.3");
	}

	private ExternalComponentRelease create(ExternalComponentType type, String name, String version) {

		var key = new ExternalComponentKey(type, name);
		return new ExternalComponentRelease(key, version);
	}
}
