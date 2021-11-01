package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.document.IAjaxDocument;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class removes {@link IAjaxDocument} instances that have not been
 * accessed for given maximum period of time.
 *
 * @author Oliver Richers
 */
class AjaxIdleDocumentsRemover {

	private final Map<UUID, IAjaxDocument> documents;
	private final int maximumIdleSeconds;

	public AjaxIdleDocumentsRemover(Map<UUID, IAjaxDocument> documents, int maximumIdleSeconds) {

		this.documents = documents;
		this.maximumIdleSeconds = maximumIdleSeconds;
	}

	public void remove() {

		documents//
			.keySet()
			.removeAll(getDocumentUuidsToRemove());
	}

	private Collection<UUID> getDocumentUuidsToRemove() {

		return documents//
			.values()
			.stream()
			.filter(document -> document.getLogs().getIdleSeconds() > maximumIdleSeconds)
			.map(IAjaxDocument::getInstanceUuid)
			.collect(Collectors.toList());
	}
}
