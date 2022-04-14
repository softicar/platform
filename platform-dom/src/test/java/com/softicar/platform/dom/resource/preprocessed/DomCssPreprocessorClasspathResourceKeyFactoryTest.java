package com.softicar.platform.dom.resource.preprocessed;

import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DomCssPreprocessorClasspathResourceKeyFactoryTest extends AbstractTest {

	private final DomCssPreprocessorClasspathResourceKeyFactory factory;

	public DomCssPreprocessorClasspathResourceKeyFactoryTest() {

		this.factory = new DomCssPreprocessorClasspathResourceKeyFactory();
	}

	@Test
	public void testCreate() {

		IResourceKey resourceKey = factory.create("/com/example/font/Rubik-wght.ttf");

		assertEquals("com.example.font", resourceKey.getPackageName());
		assertEquals("rubik-wght", resourceKey.getBasename().get());
		// TODO We currently use "application/octet-stream" for TTFs - we should probably use "font/ttf" instead.
		assertEquals("application", resourceKey.getSuperMimeType().get());
	}

	@Test(expected = DomCssPreprocessorException.class)
	public void testPreprocessWithRelativeResourcePath() {

		factory.create("com/example/Rubik-wght.ttf");
	}

	@Test(expected = DomCssPreprocessorException.class)
	public void testPreprocessWithEmptyResourcePath() {

		factory.create("");
	}

	@Test(expected = NullPointerException.class)
	public void testPreprocessWithNullResourcePath() {

		factory.create(null);
	}
}
