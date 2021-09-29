package com.softicar.platform.common.code;

import com.softicar.platform.common.core.utils.DevNull;
import org.junit.Assert;
import org.junit.Test;

public class QualifiedNameTest extends Assert {

	@Test
	public void testEquals() {

		QualifiedName foo1 = QualifiedName.parse("com.example.Foo");
		QualifiedName foo2 = QualifiedName.parse("com.example.Foo");
		QualifiedName bar = QualifiedName.parse("com.example.Bar");

		assertNotEquals(foo1, null);
		assertNotEquals(foo2, null);
		assertNotEquals(bar, null);

		assertEquals(foo1, foo1);
		assertEquals(foo2, foo2);
		assertEquals(foo1, foo2);
		assertEquals(foo2, foo1);

		assertNotEquals(foo1, bar);
		assertNotEquals(foo2, bar);
		assertNotEquals(bar, foo1);
		assertNotEquals(bar, foo2);
	}

	@Test
	public void testCompareTo() {

		QualifiedName foo = QualifiedName.parse("com.example.Foo");
		QualifiedName bar = QualifiedName.parse("com.example.Bar");
		assertTrue(foo.compareTo(bar) > 0);
		assertTrue(bar.compareTo(foo) < 0);
		assertEquals(0, foo.compareTo(foo));
		assertEquals(0, bar.compareTo(bar));
	}

	@Test
	public void getBeginning() {

		final QualifiedName NAME = QualifiedName.parse("com.example.Foo");

		assertEquals(QualifiedName.EMPTY_NAME, NAME.getBeginning(0));
		assertEquals(QualifiedName.parse("com"), NAME.getBeginning(1));
		assertEquals(QualifiedName.parse("com.example"), NAME.getBeginning(2));
		assertEquals(NAME, NAME.getBeginning(3));

		try {
			NAME.getBeginning(4);
			fail();
		} catch (IndexOutOfBoundsException exception) {
			// expected exception
			DevNull.swallow(exception);
		}
	}
}
