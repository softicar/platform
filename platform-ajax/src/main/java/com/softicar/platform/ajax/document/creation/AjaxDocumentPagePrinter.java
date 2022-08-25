package com.softicar.platform.ajax.document.creation;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.engine.AjaxJavascriptPrinter;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.common.io.writer.IManagedPrintWriter;

/**
 * Implementation of the initial page printing for {@link AjaxDocument}.
 * <p>
 * This class also prints the complete JavaScript code which is used to
 * implement the AJAX framework on the client, i.e. the web browser.
 *
 * @author Oliver Richers
 */
class AjaxDocumentPagePrinter {

	private final IAjaxDocument document;
	private final IAjaxRequest ajaxRequest;
	private final INullaryVoidFunction updateCodeWriter;
	private final IManagedPrintWriter writer;

	public AjaxDocumentPagePrinter(IAjaxDocument document, IAjaxRequest ajaxRequest, INullaryVoidFunction updateCodeWriter, IManagedPrintWriter writer) {

		this.document = document;
		this.ajaxRequest = ajaxRequest;
		this.updateCodeWriter = updateCodeWriter;
		this.writer = writer;
	}

	public void printPage() {

		printDocType();
		println("<html>");
		printHead();
		printBody();
		println("</html>");
	}

	private void printDocType() {

		println("<!DOCTYPE html>");
	}

	private void printHead() {

		println("<head>");
		printIconLink();
		printResourceLinks();
		println("</head>");
	}

	private void printIconLink() {

		println("<link rel='icon' href='%s'>", createFaviconHrefValue());
	}

	private void printResourceLinks() {

		document//
			.getEngine()
			.getResourceLinkRegistry()
			.getResourceLinks()
			.forEach(link -> println(link.toHtml()));
	}

	private void printBody() {

		println("<body>");
		printJavascriptFramework();
		printJavascriptUpdateCode();
		println("</body>");
	}

	private void printJavascriptFramework() {

		println("<script>");
		println("var DOCUMENT_INSTANCE_UUID = '%s'", document.getInstanceUuid().toString());
		println("var VERBOSE = %s;", ajaxRequest.isVerbose());
		println("var DEBUG = %s;", ajaxRequest.isDebug());
		new AjaxJavascriptPrinter().printAll(writer, !ajaxRequest.isDebug());
		println("</script>");
	}

	private void printJavascriptUpdateCode() {

		println("<script>");
		updateCodeWriter.apply();
		println("KEEP_ALIVE.schedule();");
		println("</script>");
	}

	private void println(String format, Object...args) {

		writer.println(format, args);
	}

	private IResourceUrl getResourceUrl(IResource resource) {

		return document.getEngine().getResourceUrl(resource);
	}

	private String createFaviconHrefValue() {

		return document//
			.getAjaxFramework()
			.getAjaxStrategy()
			.getFaviconResource()
			.map(this::getResourceUrl)
			.map(IResourceUrl::toString)
			.orElse("data:,");
	}
}
