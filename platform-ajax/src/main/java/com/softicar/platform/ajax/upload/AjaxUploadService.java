package com.softicar.platform.ajax.upload;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.document.service.AbstractAjaxDocumentActionService;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Collections;
import java.util.Optional;

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

		executePayloadCodeOnNode(IDomFileUploadHandler.class, this::handleFileUploads);
	}

	private void handleFileUploads(IDomFileUploadHandler handler) {

		handler.handleFileUploads(getFileUploads());
	}

	private Iterable<IDomFileUpload> getFileUploads() {

		return Optional//
			.of(request)
			.map(it -> it.getParameters())
			.map(it -> it.getFileUploads())
			.orElse(Collections.emptyList());
	}
}
