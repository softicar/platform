package com.softicar.platform.ajax.upload;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.event.AjaxDomEvent;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.document.DomDocumentEventScope;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.dom.node.IDomNode;

/**
 * AJAX service to handle file upload requests.
 *
 * @author Oliver Richers
 */
public class AjaxUploadService extends AbstractAjaxDocumentActionService {

	public AjaxUploadService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public void service(IAjaxDocument document, IDomNode eventNode) {

		setHiddenFrame(true);

		var event = new AjaxDomEvent(document, message);
		try (var scope = new DomDocumentEventScope(document, event)) {
			executePayloadCodeOnNode(IDomFileUploadHandler.class, this::handleFileUploads);
		}
	}

	private void handleFileUploads(IDomFileUploadHandler handler) {

		handler.handleFileUploads(request.getFileUploads());
	}
}
