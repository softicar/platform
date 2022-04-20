package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class EmfSourceCodeReferencePointClassesFinderTest extends AbstractTest {

	@Test
	public void test() {

		boolean found = new EmfSourceCodeReferencePointClassesFinder()//
			.findClasses()
			.contains(new JavaClassName(EmfSourceCodeReferencePointForTesting.class));

		assertTrue(found);
	}
}
