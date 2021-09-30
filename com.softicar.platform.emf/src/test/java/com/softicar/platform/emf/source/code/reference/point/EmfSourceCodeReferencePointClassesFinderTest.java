package com.softicar.platform.emf.source.code.reference.point;


import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import org.junit.Test;

public class EmfSourceCodeReferencePointClassesFinderTest {

	@Test
	public void test() {

		boolean found = new EmfSourceCodeReferencePointClassesFinder()//
			.findClasses()
			.contains(new JavaClassName(EmfSourceCodeReferencePointForTesting.class));

		assertTrue(found);
	}
}
