package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.formatting.Formatting;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Extended version of {@link CodePrinter} for Java.
 *
 * @author Oliver Richers
 */
public class JavaCodePrinter extends CodePrinter {

	private final JavaImports imports;

	public JavaCodePrinter() {

		this(new JavaImports());
	}

	public JavaCodePrinter(JavaImports imports) {

		this.imports = imports;
	}

	public String toString(JavaPackageName packageName) {

		StringBuilder builder = new StringBuilder();
		getFileHeader(packageName).writeOut(builder);
		writeOut(builder);
		return builder.toString();
	}

	// -------------------- WRITING -------------------- //

	public void writeOutToSourceFolder(JavaClassName className) {

		writeOut(new InternalSourceFolderFinder(getClass()).findSourceFolder(), className);
	}

	public void writeOut(File outputFolder, JavaClassName className) {

		File file = new File(outputFolder, className.getCanonicalName("/") + ".java");
		Log.finfo("Writing: %s", file.getPath());
		try (FileWriter writer = new FileWriter(file)) {
			prependFileHeader(className.getPackageName());
			writeOut(writer);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	// -------------------- HEADER -------------------- //

	/**
	 * Generates and returns the file header for this Java class.
	 * <p>
	 * The file header contains with the package declaration followed by the
	 * import statements.
	 *
	 * @param packageName
	 *            the package name to use
	 * @return a {@link JavaCodePrinter} containing the generated header
	 */
	public JavaCodePrinter getFileHeader(JavaPackageName packageName) {

		// create file header
		JavaCodePrinter fileHeader = new JavaCodePrinter();

		// package name
		fileHeader.println("package %s;", packageName.getName());
		fileHeader.println();

		// imports
		if (!imports.isEmpty()) {
			for (JavaClassName className: imports) {
				if (className.getPackageName().equals(packageName) && !className.isInnerClass()) {
					// skip redundant import
				} else {
					fileHeader.println("import %s;", className.getCanonicalName());
				}
			}
			fileHeader.println();
		}

		return fileHeader;
	}

	public void prependFileHeader(JavaPackageName packageName) {

		JavaCodePrinter fileHeader = getFileHeader(packageName);
		prependLines(fileHeader.getCodeLines());
	}

	// -------------------- IMPORTS -------------------- //

	public Set<JavaClassName> getImports() {

		return imports.getImports();
	}

	public void addImport(JavaClass javaClass) {

		imports.addImport(javaClass);
	}

	public void addImport(Class<?> classImport) {

		imports.addImport(classImport);
	}

	public void addImport(JavaClassName className) {

		imports.addImport(className);
	}

	public void addImports(Iterable<JavaClassName> classNames) {

		imports.addImports(classNames);
	}

	public void addImportsFor(Set<JavaClassName> classNames, Set<String> simpleNames) {

		imports.addImportsFor(classNames, simpleNames);
	}

	// -------------------- CLASS -------------------- //

	public void beginClass(String format, Object...args) {

		beginBlock(format, args);
		println();
	}

	public void endClass() {

		endBlock(true);
	}

	// -------------------- METHOD -------------------- //

	public void beginMethod(String format, Object...args) {

		beginBlock(format, args);
		println();
	}

	public void endMethod() {

		endBlock(true);
	}

	// -------------------- IF -------------------- //

	public void beginIf(String condition, Object...args) {

		beginBlock("if(%s)", Formatting.format(condition, args));
	}

	public void beginElseIf(String condition, Object...args) {

		decrementIndentation();
		println("} else if(%s) {", Formatting.format(condition, args));
		incrementIndentation();
	}

	public void beginElse() {

		decrementIndentation();
		println("} else {");
		incrementIndentation();
	}

	// -------------------- SWITCH -------------------- //

	public void beginSwitch(String text) {

		println("%s {", text);
	}

	public void endSwitch() {

		println("}");
	}

	public void beginCase(String text, Object...args) {

		println(text, args);
		incrementIndentation();
	}

	public void endCase() {

		decrementIndentation();
	}

	// -------------------- MISC -------------------- //

	public void printSeparator(String text) {

		println("// -------------------------------- %s -------------------------------- //", text);
		println();
	}
}
