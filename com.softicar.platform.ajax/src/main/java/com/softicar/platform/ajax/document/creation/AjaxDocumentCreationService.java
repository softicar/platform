package com.softicar.platform.ajax.document.creation;

import com.softicar.platform.ajax.AjaxCssFiles;
import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.AjaxDocumentScope;
import com.softicar.platform.ajax.document.registry.AjaxDocumentRegistry;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.DomCssFiles;
import com.softicar.platform.dom.document.CurrentDomDocument;

public class AjaxDocumentCreationService extends AbstractAjaxDocumentService {

	public AjaxDocumentCreationService(IAjaxRequest request) {

		super(request, new AjaxDocument(request));
	}

	@Override
	public void service() {

		// TODO move this maintenance call into AjaxDocumentRegistry
		AjaxDocumentRegistry//
			.getInstance(request.getHttpSession())
			.dropIdleAndOldDocuments(framework.getSettings());

		try (AjaxDocumentScope scope = new AjaxDocumentScope(document, request)) {
			setDomLinkCreation(false);
			CurrentDomDocument.set(document);
			buildDocument();
			printDocumentPage();
			setDomLinkCreation(true);
		}

		flushOutput();
	}

	private void buildDocument() {

		document.getEngine().setupStatusElements();

		executePayloadCode(() -> framework.getAjaxStrategy().buildDocument(document));

		document.getEngine().registerCssResourceLink(AjaxCssFiles.AJAX_STYLE.getResource());
		document.getEngine().registerCssResourceLink(DomCssFiles.DOM_STYLE.getResource());

		document.finishRequestHandling(statementList);
	}

	private void printDocumentPage() {

		new AjaxDocumentPagePrinter(document, request, this::flushJavaScript, getOutputWriter()).printPage();
	}

	private void setDomLinkCreation(boolean enabled) {

		document.getEngine().getResourceLinkRegistry().setDomLinkCreationEnabled(enabled);
	}
}
