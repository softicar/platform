package com.softicar.platform.ajax.auto.complete;

import com.softicar.platform.ajax.document.AjaxDocumentScope;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.service.AbstractAjaxService;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.dom.input.auto.DomAutoCompleteList;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.dom.node.IDomNode;
import java.io.IOException;
import java.io.PrintWriter;

public class AjaxAutoCompleteService extends AbstractAjaxService {

	private final IAjaxDocument document;

	public AjaxAutoCompleteService(IAjaxRequest request, IAjaxDocument document) {

		super(request);

		this.document = document;
	}

	@Override
	public void service() {

		// get request parameters
		String nodeId = request.getParameter("n");
		String pattern = request.getParameter("p");

		// try to get document instance
		synchronized (document) {
			DomAutoCompleteList autoCompleteList = getAutoCompleteList(document, nodeId, pattern);
			if (autoCompleteList != null) {
				sendReply(autoCompleteList);
			}
		}
	}

	private void sendReply(DomAutoCompleteList autoCompleteList) {

		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter writer = response.getWriter()) {
			writer.print("[");
			writer.print(Imploder.implode(autoCompleteList, ","));
			writer.print("]");
			writer.flush();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private DomAutoCompleteList getAutoCompleteList(IAjaxDocument document, String nodeId, String pattern) {

		IDomNode node = document.getNode(nodeId);
		if (node instanceof IDomAutoCompleteInput) {
			try (AjaxDocumentScope scope = new AjaxDocumentScope(document, request)) {
				return ((IDomAutoCompleteInput<?>) node).getItemList(pattern);
			}
		} else {
			return null;
		}
	}
}
