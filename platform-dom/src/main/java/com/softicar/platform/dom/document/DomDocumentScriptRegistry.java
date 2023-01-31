package com.softicar.platform.dom.document;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.dom.elements.DomScript;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Keeps track of {@link DomScript} elements added to an {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
class DomDocumentScriptRegistry {

	private final IDomDocument document;
	private final Set<String> registeredUrls;

	public DomDocumentScriptRegistry(IDomDocument document) {

		this.document = document;
		this.registeredUrls = new TreeSet<>();
	}

	public static DomDocumentScriptRegistry getInstance(IDomDocument document) {

		return document.getDataMap().getOrPutInstance(DomDocumentScriptRegistry.class, () -> new DomDocumentScriptRegistry(document));
	}

	public void registerScript(String scriptUrl, MimeType mimeType) {

		Objects.requireNonNull(scriptUrl);
		Objects.requireNonNull(mimeType);

		if (!registeredUrls.contains(scriptUrl)) {
			var script = new DomScript()//
				.setType(mimeType)
				.setSrc(scriptUrl);
			document.getHead().appendChild(script);
			registeredUrls.add(scriptUrl);
		}
	}
}
