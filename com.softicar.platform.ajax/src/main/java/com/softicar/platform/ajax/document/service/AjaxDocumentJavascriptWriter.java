package com.softicar.platform.ajax.document.service;

import com.softicar.platform.ajax.engine.JavascriptFormatter;
import com.softicar.platform.ajax.engine.JavascriptStatementList;
import com.softicar.platform.common.io.writer.IManagedPrintWriter;
import com.softicar.platform.common.io.writer.ManagedPrintWriter;
import com.softicar.platform.dom.utils.JavascriptEscaping;

class AjaxDocumentJavascriptWriter {

	private final JavascriptStatementList statementList;
	private boolean hiddenFrame;
	private boolean debug;

	public AjaxDocumentJavascriptWriter(JavascriptStatementList statementList) {

		this.statementList = statementList;
		this.hiddenFrame = false;
		this.debug = false;
	}

	public AjaxDocumentJavascriptWriter setHiddenFrame(boolean hiddenFrame) {

		this.hiddenFrame = hiddenFrame;
		return this;
	}

	public AjaxDocumentJavascriptWriter setDebug(boolean debug) {

		this.debug = debug;
		return this;
	}

	public void writeTo(IManagedPrintWriter writer) {

		if (hiddenFrame) {
			writeToHiddenFrame(writer);
		} else {
			appendTo(writer);
		}
	}

	private void writeToHiddenFrame(IManagedPrintWriter writer) {

		StringBuilder buffer = new StringBuilder();
		appendTo(new ManagedPrintWriter(buffer, "\r\n"));

		if (!buffer.isEmpty()) {
			writer.println("<script type=text/javascript>");
			writer.write("parent.SR_handleAjaxResponse('");
			writer.write(JavascriptEscaping.getEscaped(buffer.toString()));
			writer.println("');");
			writer.println("</script>");
		}
	}

	private void appendTo(IManagedPrintWriter writer) {

		statementList.appendTo(new JavascriptFormatter(writer, "    ", "\n", debug));
	}
}
