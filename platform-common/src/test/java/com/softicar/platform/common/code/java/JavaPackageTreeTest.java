package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class JavaPackageTreeTest extends AbstractTest {

	@Test
	public void test() {

		JavaPackageTree packageTree = new JavaPackageTree("com.test.foo");

		assertTrue(packageTree.contains(new JavaPackageName("com.test.foo")));
		assertTrue(packageTree.contains(new JavaClassName("com.test.foo.Bar")));
		assertTrue(packageTree.contains(new JavaClassName("com.test.foo.bar.Baz")));

		assertFalse(packageTree.contains(new JavaPackageName("com")));
		assertFalse(packageTree.contains(new JavaPackageName("com.test")));
		assertFalse(packageTree.contains(new JavaPackageName("com.test.fo")));
		assertFalse(packageTree.contains(new JavaPackageName("com.test.Foo")));
		assertFalse(packageTree.contains(new JavaClassName("com.test.Foo")));
	}
}
