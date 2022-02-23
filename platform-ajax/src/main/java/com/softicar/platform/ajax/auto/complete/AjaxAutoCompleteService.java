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
			String autoCompleteString = getAutoCompleteList(document, nodeId, pattern);
			if (autoCompleteString != null) {
				sendReply(autoCompleteString);
			}
		}
	}

	private void sendReply(String autoCompleteList) {

		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter writer = response.getWriter()) {
			writer.print(autoCompleteList);
			writer.flush();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private String getAutoCompleteList(IAjaxDocument document, String nodeId, String pattern) {

		IDomNode node = document.getNode(nodeId);
		if (node instanceof IDomAutoCompleteInput) {
			try (AjaxDocumentScope scope = new AjaxDocumentScope(document, request)) {
				StringBuilder builder = new StringBuilder();
				builder.append("{");
				builder.append("\"items\":[" + Imploder.implode(((IDomAutoCompleteInput<?>) node).getItemList(pattern), ",") + "], ");
				builder.append("\"maxRows\":" + DomAutoCompleteList.MAXIMUM_ELEMENT_COUNT);
				builder.append("}");
				return builder.toString();
			}
		} else {
			return null;
		}
	}
}
