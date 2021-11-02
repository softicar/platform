package com.softicar.platform.ajax.document.service;

import com.softicar.platform.ajax.document.AjaxDocumentScope;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.exceptions.AjaxHttpBadRequestError;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractAjaxDocumentActionService extends AbstractAjaxDocumentService {

	private static final String EVENT_NODE_PARAMETER = "n";
	private static final String INPUT_VALUE_PARAMETER_PREFIX = "V";

	public AbstractAjaxDocumentActionService(IAjaxRequest request, IAjaxDocument document) {

		super(request, document);
	}

	@Override
	public final void service() {

		synchronized (document) {
			try (AjaxDocumentScope scope = new AjaxDocumentScope(document, request)) {
				updateInputFieldValues(document);
				service(document);
			}
		}

		flushJavaScript();
		flushOutput();
	}

	protected abstract void service(IAjaxDocument document, IDomNode eventNode);

	protected <T> void executePayloadCodeOnNode(Class<T> nodeClass, Consumer<T> payloadCode) {

		IDomNode node = Optional//
			.ofNullable(getEventNode(document))
			.orElseThrow(() -> new AjaxHttpBadRequestError("Failed to determine event node for document action."));
		T castedNode = CastUtils//
			.tryCast(node, nodeClass)
			.orElseThrow(() -> new AjaxHttpBadRequestError("Failed to cast event node for document action."));
		executePayloadCode(() -> payloadCode.accept(castedNode));
	}

	private void service(IAjaxDocument document) {

		service(document, getEventNode(document));
		executePayloadCode(() -> document.getRefreshBus().submitEvent());
		document.finishRequestHandling(statementList);
	}

	private IDomNode getEventNode(IAjaxDocument document) {

		String nodeId = request.getParameter(EVENT_NODE_PARAMETER);
		return nodeId != null? document.getNode(nodeId) : null;
	}

	private void updateInputFieldValues(IAjaxDocument document) {

		for (Map.Entry<String, String[]> parameter: request.getParameterMap().entrySet()) {
			String name = parameter.getKey();
			String[] value = parameter.getValue();
			if (name.startsWith(INPUT_VALUE_PARAMETER_PREFIX)) {
				IDomNode node = document.getNode(Integer.parseInt(name.substring(1)));

				if (node == null) {
					// node was collected
					continue;
				}

				if (node instanceof DomSelect<?>) {
					((DomSelect<?>) node).setSelectedOptions_noJS(value[0]);
				} else {
					node.getAccessor().setAttributeInMap("value", value[0]);
				}
			}
		}

		document.getEngine().approveNodeValues();
	}
}
