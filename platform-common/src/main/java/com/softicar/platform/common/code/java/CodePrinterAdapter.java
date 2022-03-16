package com.softicar.platform.common.code.java;

public class CodePrinterAdapter {

	private final JavaCodePrinter printer;

	public CodePrinterAdapter(JavaCodePrinter printer) {

		this.printer = printer;
	}

	public void println() {

		printer.println();
	}

	public void println(String text, Object...args) {

		printer.println(text, args);
	}

	public void beginBlock(String text, Object...args) {

		printer.beginBlock(text, args);
	}

	public void beginBlock() {

		printer.beginBlock();
	}

	public void endBlock() {

		printer.endBlock();
	}
}
