package com.softicar.platform.ajax.document.service;

import com.softicar.platform.ajax.document.AjaxDocumentScope;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.AjaxRequestMessage;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.network.http.error.HttpBadRequestError;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractAjaxDocumentActionService extends AbstractAjaxDocumentService {

	protected final AjaxRequestMessage message;

	public AbstractAjaxDocumentActionService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);

		this.message = request.getRequestMessageOrThrow();
	}

	@Override
	public final void service() {

		synchronized (document) {
			try (AjaxDocumentScope scope = new AjaxDocumentScope(document, request)) {
				updateInputFieldValues();
				service(document, getEventNode());
				executePayloadCode(() -> document.getRefreshBus().submitEvent());
				executePayloadCode(() -> document.getDeferredInitializationController().handleAllAppended());
				document.finishRequestHandling(statementList);
			}
		}

		flushJavaScript();
		flushOutput();
	}

	protected abstract void service(IAjaxDocument document, IDomNode eventNode);

	protected <T> void executePayloadCodeOnNode(Class<T> nodeClass, Consumer<T> payloadCode) {

		IDomNode node = Optional//
			.ofNullable(getEventNode())
			.orElseThrow(() -> new HttpBadRequestError("Failed to determine event node for document action."));
		T castedNode = CastUtils//
			.tryCast(node, nodeClass)
			.orElseThrow(() -> new HttpBadRequestError("Failed to cast event node for document action."));
		executePayloadCode(() -> payloadCode.accept(castedNode));
	}

	private IDomNode getEventNode() {

		return message.getNode(document);
	}

	private void updateInputFieldValues() {

		message.getNodeValues().entrySet().forEach(this::updateInputFieldValue);
		document.getEngine().approveNodeValues();
	}

	private void updateInputFieldValue(Entry<String, String> entry) {

		var node = document.getNode(entry.getKey());
		if (node != null) {
			if (node instanceof DomSelect<?>) {
				((DomSelect<?>) node).setSelectedOptions_noJS(entry.getValue());
			} else {
				node.getAccessor().setAttributeInMap("value", entry.getValue());
			}
		}
	}
}
