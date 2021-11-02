package com.softicar.platform.common.code.java;

import static org.junit.Assert.assertEquals;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class JavaCodePrinterTest {

	private JavaCodePrinter printer;
	private JavaCodePrinter fileHeader;
	private int headerLineIndex;

	@Before
	public void before() {

		this.printer = new JavaCodePrinter();
	}

	@Test
	public void addsPackageDeclaration() {

		generateFileHeader("com.example");
		assertHeaderLine("package com.example;");
		assertHeaderLine("");
		assertEndOfHeader();
	}

	@Test
	public void skipsJavaLangImports() {

		printer.addImport(Integer.class);
		printer.addImport(String.class);
		printer.addImport(List.class);

		generateFileHeader("com.example");
		assertHeaderLine("package com.example;");
		assertHeaderLine("");
		assertHeaderLine("import java.util.List;");
		assertHeaderLine("");
		assertEndOfHeader();
	}

	@Test
	public void skipsPrimitiveImports() {

		printer.addImport(int.class);
		printer.addImport(byte.class);

		generateFileHeader("com.example");
		assertHeaderLine("package com.example;");
		assertHeaderLine("");
		assertEndOfHeader();
	}

	@Test
	public void skipsArrayImports() {

		printer.addImport(int[].class);
		printer.addImport(byte[].class);

		generateFileHeader("com.example");
		assertHeaderLine("package com.example;");
		assertHeaderLine("");
		assertEndOfHeader();
	}

	@Test
	public void skipsLocalImports() {

		printer.addImport(new JavaClassName("com.Foo"));
		printer.addImport(new JavaClassName("com.example.Foo"));
		printer.addImport(new JavaClassName("com.example.Bar"));
		printer.addImport(new JavaClassName("com.example.Foo.Bar"));
		printer.addImport(new JavaClassName("com.example.core.Foo"));

		generateFileHeader("com.example");
		assertHeaderLine("package com.example;");
		assertHeaderLine("");
		assertHeaderLine("import com.Foo;");
		assertHeaderLine("import com.example.Foo.Bar;");
		assertHeaderLine("import com.example.core.Foo;");
		assertHeaderLine("");
		assertEndOfHeader();

		generateFileHeader("com.example.core");
		assertHeaderLine("package com.example.core;");
		assertHeaderLine("");
		assertHeaderLine("import com.Foo;");
		assertHeaderLine("import com.example.Bar;");
		assertHeaderLine("import com.example.Foo;");
		assertHeaderLine("import com.example.Foo.Bar;");
		assertHeaderLine("");
		assertEndOfHeader();
	}

	@Test
	public void testToString() {

		printer.addImport(List.class);
		printer.beginClass("public class Test");
		printer.endClass();

		// @formatter:off
		String expectedCode =
				"package com.example;\n" +
				"\n" +
				"import java.util.List;\n" +
				"\n" +
				"public class Test {\n" +
				"}\n" +
				"\n";
		// @formatter:on

		JavaPackageName packageName = new JavaPackageName("com.example");
		assertEquals(expectedCode, printer.toString(packageName));
	}

	private void generateFileHeader(String packageName) {

		this.fileHeader = printer.getFileHeader(new JavaPackageName(packageName));
		this.headerLineIndex = 0;
	}

	private void assertHeaderLine(String expectedLine) {

		assertEquals(expectedLine, fileHeader.getCodeLines().get(headerLineIndex));
		++headerLineIndex;
	}

	private void assertEndOfHeader() {

		assertEquals(headerLineIndex, fileHeader.getCodeLines().size());
	}
}
