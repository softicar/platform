package com.softicar.platform.dom.text;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Assert;
import org.junit.Test;

public class TextNodeFactoryTest {

	@Test
	public void test() {

		CurrentDomDocument.set(new DomDocument());
		test("", DomTextNode0.class);
		test("1", DomTextNode4.class);
		test("12", DomTextNode4.class);
		test("123", DomTextNode4.class);
		test("1234", DomTextNode4.class);
		test("12345", DomTextNode8.class);
		test("123456", DomTextNode8.class);
		test("1234567", DomTextNode8.class);
		test("12345678", DomTextNode8.class);
		test("123456789", DomTextNode12.class);
		test("1234567890", DomTextNode12.class);
		test("12345678901", DomTextNode12.class);
		test("123456789012", DomTextNode12.class);
		test("1234567890123", DomTextNodeCommon.class);
		test("12345678901234", DomTextNodeCommon.class);

		test("ä", DomTextNode4.class);
		test("äÜ", DomTextNode4.class);
		test("äÜa", DomTextNode8.class);
	}

	private static void test(String text, Class<?> expectedClass) {

		DomTextNode textNode = TextNodeFactory.create(text);

		Assert.assertEquals(text, textNode.getText());
		Assert.assertSame(expectedClass, textNode.getClass());
	}
}
