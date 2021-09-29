package com.softicar.platform.common.code;

import org.junit.Assert;

public abstract class AbstractQualifiedNameTest extends Assert {

	public static void assertSegments(QualifiedName qualifiedName, String...expectedSegments) {

		assertArrayEquals(expectedSegments, qualifiedName.getSegments().toArray());
	}
}
