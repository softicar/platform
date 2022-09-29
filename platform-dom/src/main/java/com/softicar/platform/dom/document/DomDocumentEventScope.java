package com.softicar.platform.dom.document;

import com.softicar.platform.dom.event.IDomEvent;
import java.util.Objects;

public class DomDocumentEventScope implements AutoCloseable {

	private final IDomDocument document;
	private final IDomEvent previousEvent;

	public DomDocumentEventScope(IDomDocument document, IDomEvent event) {

		Objects.requireNonNull(document);
		Objects.requireNonNull(event);

		this.document = document;
		this.previousEvent = document.getCurrentEvent();
		document.setCurrentEvent(event);
	}

	@Override
	public void close() {

		document.setCurrentEvent(previousEvent);
	}
}
