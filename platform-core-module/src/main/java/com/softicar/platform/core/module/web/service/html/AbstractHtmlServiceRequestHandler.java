package com.softicar.platform.core.module.web.service.html;

import com.softicar.platform.common.io.writer.IManagedPrintWriter;
import com.softicar.platform.common.io.writer.ManagedPrintWriter;
import com.softicar.platform.core.module.web.service.AbstractWebServiceRequestHandler;
import java.io.PrintWriter;

/**
 * Abstract base class for simple services speaking only HTML.
 * <p>
 * You just have to implement the method {@link #printPage()}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractHtmlServiceRequestHandler extends AbstractWebServiceRequestHandler {

	protected IManagedPrintWriter writer;
	private PrintWriter printWriter;

	@Override
	protected void service() {

		try (PrintWriter printWriter = startHtmlOutput()) {
			this.printWriter = printWriter;
			this.writer = new ManagedPrintWriter(printWriter, "\r\n");
			printPage();
		}
	}

	protected final void print(String text) {

		printWriter.print(text);
	}

	protected final void print(String text, Object...args) {

		printWriter.print(String.format(text, args));
	}

	protected final void println(String line) {

		printWriter.println(line);
	}

	protected final void println(String line, Object...args) {

		printWriter.println(String.format(line, args));
	}

	protected PrintWriter getPrintWriter() {

		return printWriter;
	}

	protected abstract void printPage();
}
