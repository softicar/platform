package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class JavaPackageTreeSetTest extends AbstractTest {

	@Test
	public void test() {

		JavaPackageTreeSet packageTreeSet = new JavaPackageTreeSet();
		packageTreeSet.add(new JavaPackageTree(new JavaPackageName("com.test.foo")));
		packageTreeSet.add(new JavaPackageTree(new JavaPackageName("com.test.bar")));

		assertTrue(packageTreeSet.contains(new JavaPackageName("com.test.foo")));
		assertTrue(packageTreeSet.contains(new JavaPackageName("com.test.foo.bar")));
		assertTrue(packageTreeSet.contains(new JavaPackageName("com.test.bar")));
		assertTrue(packageTreeSet.contains(new JavaPackageName("com.test.bar.foo")));
		assertTrue(packageTreeSet.contains(new JavaClassName("com.test.foo.Bar")));
		assertTrue(packageTreeSet.contains(new JavaClassName("com.test.bar.Foo")));

		assertFalse(packageTreeSet.contains(new JavaPackageName("com.test")));
		assertFalse(packageTreeSet.contains(new JavaPackageName("com.test.baz")));
		assertFalse(packageTreeSet.contains(new JavaPackageName("com.test.fo.bar")));
		assertFalse(packageTreeSet.contains(new JavaPackageName("com.test.Foo")));
		assertFalse(packageTreeSet.contains(new JavaClassName("com.test.Foo")));
	}
}
