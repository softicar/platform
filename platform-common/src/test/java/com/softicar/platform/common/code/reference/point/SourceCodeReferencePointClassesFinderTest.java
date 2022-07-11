package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class SourceCodeReferencePointClassesFinderTest extends AbstractTest {

	@Test
	public void test() {

		boolean found = new SourceCodeReferencePointClassesFinder()//
			.findClasses()
			.contains(new JavaClassName(SourceCodeReferencePointForTesting.class));

		assertTrue(found);
	}
}
