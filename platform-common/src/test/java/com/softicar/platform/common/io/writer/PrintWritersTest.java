package com.softicar.platform.common.io.writer;

import com.softicar.platform.common.core.properties.SystemPropertyEnum;
import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class PrintWritersTest extends Asserts {

	@Test
	public void test() {

		var string = PrintWriters.printToString(writer -> {
			writer.println("Hello, World!");
		});

		assertEquals("Hello, World!" + SystemPropertyEnum.LINE_SEPARATOR.get(), string);
	}

	@Test
	public void testWithException() {

		var string = PrintWriters.printToString(new RuntimeException("Foo!")::printStackTrace);

		assertStartsWith("java.lang.RuntimeException: Foo!", string);
	}
}
