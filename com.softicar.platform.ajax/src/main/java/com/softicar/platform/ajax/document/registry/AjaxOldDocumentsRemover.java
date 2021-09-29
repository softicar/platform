package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.document.IAjaxDocument;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class limits the number of {@link IAjaxDocument} instances to a given
 * maximum by removing the least recently used instances.
 *
 * @author Oliver Richers
 */
class AjaxOldDocumentsRemover {

	private final Map<UUID, IAjaxDocument> documents;
	private final int maximumDocumentCount;

	public AjaxOldDocumentsRemover(Map<UUID, IAjaxDocument> documents, int maximumDocumentCount) {

		this.documents = documents;
		this.maximumDocumentCount = maximumDocumentCount;
	}

	public void remove() {

		documents.keySet().removeAll(getDocumentIdsToRemove());
	}

	private Collection<UUID> getDocumentIdsToRemove() {

		return documents//
			.values()
			.stream()
			.sorted(AjaxDocumentLastAccessFirstComparator.get())
			.limit(Math.max(0, documents.size() - maximumDocumentCount))
			.map(IAjaxDocument::getInstanceUuid)
			.collect(Collectors.toList());
	}
}
