package com.softicar.platform.dom.event;

import com.softicar.platform.dom.document.DomDocumentEventScope;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.utils.DomPayloadCodeExecutor;
import java.util.Objects;

public class DomEventHandlerNodeCaller {

	private final DomPayloadCodeExecutor executor;
	private final IDomNode eventNode;
	private final IDomEvent event;

	public DomEventHandlerNodeCaller(DomPayloadCodeExecutor executor, IDomNode eventNode, IDomEvent event) {

		this.executor = Objects.requireNonNull(executor);
		this.eventNode = Objects.requireNonNull(eventNode);
		this.event = Objects.requireNonNull(event);
	}

	public void call() {

		var document = eventNode.getDomDocument();

		try (var scope = new DomDocumentEventScope(document, event)) {
			executor.execute(() -> {
				if (eventNode instanceof IDomEventHandler) {
					((IDomEventHandler) eventNode).handleDOMEvent(event);
				} else {
					event.getType().handleEvent(eventNode, event);
				}
				document.getDeferredInitializationController().handleAllAppended();
			});
		}
	}
}
