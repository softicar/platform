package com.softicar.platform.common.io.resource.key;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class ResourceKeyTest extends Assert {

	private static final Class<?> ANCHOR_CLASS = Object.class;

	@Test
	public void testToString() {

		assertEquals(ANCHOR_CLASS.getPackageName() + ".foo.image", new ResourceKey(ANCHOR_CLASS, "foo.png").toString());
		assertEquals(ANCHOR_CLASS.getPackageName() + ".bar.text", new ResourceKey(ANCHOR_CLASS, "bar.txt").toString());
	}

	@Test
	public void testEquals() {

		assertEquals(createKey("foo.png"), createKey("foo.gif"));
		assertEquals(createKey("foo.png"), createKey("foo.jpg"));
		assertEquals(createKey("foo.png"), new ResourceKey(Class.class, "foo.png"));

		assertNotEquals(createKey("foo.png"), createKey("foo.xml"));
		assertNotEquals(createKey("foo.png"), createKey("bar.png"));
		assertNotEquals(createKey("foo.png"), new ResourceKey(Collection.class, "foo.png"));
	}

	private static ResourceKey createKey(String filename) {

		return new ResourceKey(ANCHOR_CLASS, filename);
	}
}
