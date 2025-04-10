package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.customization.IAjaxSettings;
import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import jakarta.servlet.http.HttpSession;

/**
 * This registry manages all {@link AjaxDocument} instances of an
 * {@link HttpSession}.
 *
 * @author Oliver Richers
 */
public class AjaxDocumentRegistry {

	private final Map<UUID, IAjaxDocument> documents;

	public AjaxDocumentRegistry() {

		this.documents = new TreeMap<>();
	}

	public static AjaxDocumentRegistry getInstance(HttpSession session) {

		return new AjaxSessionAttributeManager(session)//
			.getOrAddInstance(AjaxDocumentRegistry.class, AjaxDocumentRegistry::new);
	}

	/**
	 * Registers the given {@link IAjaxDocument} and returns a random
	 * {@link UUID} for it.
	 * <p>
	 * The same {@link IAjaxDocument} shall not be registered twice. There is no
	 * check against this.
	 *
	 * @param document
	 *            the {@link IAjaxDocument} to register (never <i>null</i>)
	 * @return a random {@link UUID} for the given {@link IAjaxDocument} (never
	 *         <i>null</i>)
	 */
	public synchronized UUID register(IAjaxDocument document) {

		UUID instanceUuid = UUID.randomUUID();
		documents.put(instanceUuid, document);
		return instanceUuid;
	}

	/**
	 * Retrieves the {@link AjaxDocument} with the given {@link UUID}.
	 * <p>
	 * A previously registered {@link AjaxDocument} instance is not guaranteed
	 * to be returned. It might have been dropped in the meantime.
	 *
	 * @param instanceUuid
	 *            the {@link UUID} of the {@link AjaxDocument} instance
	 * @return the optionally returned {@link AjaxDocument} instance
	 */
	public synchronized Optional<IAjaxDocument> getDocument(UUID instanceUuid) {

		return Optional.ofNullable(documents.get(instanceUuid));
	}

	public synchronized void dropIdleAndOldDocuments(IAjaxSettings settings) {

		new AjaxIdleDocumentsRemover(documents, settings.getMaximumDocumentIdleSeconds()).remove();
		new AjaxOldDocumentsRemover(documents, settings.getMaximumDocumentsPerSession()).remove();
	}
}
