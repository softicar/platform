package com.softicar.platform.common.io.classpath;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.junit.Test;

public class ClasspathFileUtilsTest extends AbstractTest {

	@Test
	public void testGetExtension() {

		String fileName = "foo.bar";
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals(".bar", extension);
	}

	@Test
	public void testGetExtensionWithPath() {

		String fileName = "qwe/foo.bar";
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals(".bar", extension);
	}

	@Test
	public void testGetExtensionWithMultipleDots() {

		String fileName = "qwe.foo.bar";
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals(".bar", extension);
	}

	@Test
	public void testGetExtensionWithShortExtension() {

		String fileName = "foo.b";
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals(".b", extension);
	}

	@Test
	public void testGetExtensionWithLongExtension() {

		String fileName = "foo.baaaar";
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals(".baaaar", extension);
	}

	@Test
	public void testGetExtensionWithTailingDot() {

		String fileName = "qwe.asd/foo.bar.";
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals("", extension);
	}

	@Test
	public void testGetExtensionWithEmptyFileName() {

		String fileName = "";
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals("", extension);
	}

	@Test
	public void testGetExtensionWithNullFileName() {

		String fileName = null;
		String extension = ClasspathFileUtils.getExtension(fileName);
		assertEquals("", extension);
	}

	@Test
	public void testGetNormalizedExtensionFilter() {

		Collection<String> input = new ArrayList<>();
		input.add(null);
		input.add("");
		input.add(".");
		input.add("qwe");
		input.add(".asd");
		input.add(".ZXC");

		Collection<String> output = ClasspathFileUtils.getNormalizedExtensionFilter(input);

		assertEquals(4, output.size());
		assertTrue(output.contains(""));
		assertTrue(output.contains(".qwe"));
		assertTrue(output.contains(".asd"));
		assertTrue(output.contains(".zxc"));
	}

	@Test
	public void testGetNormalizedExtensionFilterWithNullInput() {

		Collection<String> input = null;

		Collection<String> output = ClasspathFileUtils.getNormalizedExtensionFilter(input);

		assertNotNull(output);
		assertTrue(output.isEmpty());
	}

	@Test
	public void testGetRelativePaths() {

		Collection<Package> packages = new ArrayList<>();
		packages.add(Object.class.getPackage());
		packages.add(ArrayList.class.getPackage());

		Set<String> relativePaths = ClasspathFileUtils.getRelativePaths(packages);

		assertEquals(2, relativePaths.size());
		assertTrue(relativePaths.contains("java/lang/"));
		assertTrue(relativePaths.contains("java/util/"));
	}
}
