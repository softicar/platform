package com.softicar.platform.ajax.document.creation;

import com.softicar.platform.ajax.AjaxI18n;
import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.engine.AjaxJavascriptPrinter;
import com.softicar.platform.ajax.image.AjaxImages;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
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

		// texts
		println("var LOCK_MESSAGE = '%s';", AjaxI18n.PLEASE_WAIT.concat(" ").concat(AjaxI18n.ANOTHER_ACTION_IS_STILL_EXECUTING));
		println("var AUTO_COMPLETE_TEXT_GENERIC = '%s';", AjaxI18n.AUTO_COMPLETE_INPUT_ELEMENT);
		println("var AUTO_COMPLETE_TEXT_NO_RECORDS_FOUND = '%s';", AjaxI18n.NO_RECORDS_FOUND);
		println("var AUTO_COMPLETE_TEXT_NOT_OKAY = '%s';", AjaxI18n.INVALID_INPUT);
		println("var AUTO_COMPLETE_TEXT_VALUE_AMBIGUOUS = '%s';", AjaxI18n.AMBIGUOUS_INPUT);
		println("var AUTO_COMPLETE_TEXT_VALUE_ILLEGAL = '%s';", AjaxI18n.ILLEGAL_INPUT);
		println("var AUTO_COMPLETE_TEXT_VALUE_MISSING = '%s';", AjaxI18n.INPUT_REQUIRED);
		println("var AUTO_COMPLETE_TEXT_VALUE_VALID = '%s';", AjaxI18n.VALID_INPUT);

		// images
		println("var AUTO_COMPLETE_IMAGE_GENERIC = '%s';", getResourceUrl(AjaxImages.EMBLEM_AUTO_COMPLETE_GENERIC));
		println("var AUTO_COMPLETE_IMAGE_NOT_OKAY = '%s';", getResourceUrl(AjaxImages.EMBLEM_AUTO_COMPLETE_NOT_OKAY));
		println("var AUTO_COMPLETE_IMAGE_VALUE_AMBIGUOUS = '%s';", getResourceUrl(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_AMBIGUOUS));
		println("var AUTO_COMPLETE_IMAGE_VALUE_ILLEGAL = '%s';", getResourceUrl(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_ILLEGAL));
		println("var AUTO_COMPLETE_IMAGE_VALUE_MISSING = '%s';", getResourceUrl(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_MISSING));
		println("var AUTO_COMPLETE_IMAGE_VALUE_VALID = '%s';", getResourceUrl(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID));

		// variables
		println("var DOCUMENT_INSTANCE_UUID = '%s'", document.getInstanceUuid().toString());
		println("var VERBOSE = %s;", ajaxRequest.isVerbose());
		println("var DEBUG = %s;", ajaxRequest.isDebug());
		println("var TEST = %s;", ajaxRequest.isTest());
		new AjaxJavascriptPrinter().printAll(writer, !ajaxRequest.isDebug());
		println("</script>");
	}

	private void printJavascriptUpdateCode() {

		println("<script>");
		println("GLOBAL_init();");
		updateCodeWriter.apply();
		println("lock(LOCK_REASON_DOM_EVENT);");
		println("AQ_executeNextAction();");
		println("</script>");
	}

	private void println(String format, Object...args) {

		writer.println(format, args);
	}

	private IResourceUrl getResourceUrl(IResourceSupplier resourceSupplier) {

		return getResourceUrl(resourceSupplier.getResource());
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
