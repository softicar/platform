package com.softicar.platform.dom.resource.preprocessed;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.resource.set.IDomResourceSet;
import java.util.Objects;

class DomCssPreprocessorResourceUrlFactory {

	private final IDomResourceSet resourceSet;

	public DomCssPreprocessorResourceUrlFactory(IDomResourceSet resourceSet) {

		this.resourceSet = Objects.requireNonNull(resourceSet);
	}

	public String create(String absolutePath) {

		IResourceKey resourceKey = new DomCssPreprocessorClasspathResourceKeyFactory().create(absolutePath);
		IResource resource = resourceSet//
			.getResource(resourceKey)
			.orElseThrow(() -> new DomCssPreprocessorException("Could not retrieve resource for '%s'.".formatted(absolutePath)));
		return createResourceUrl(resource);
	}

	private String createResourceUrl(IResource resource) {

		IDomDocument document = CurrentDomDocument.get();
		if (document != null) {
			try {
				return document.getEngine().getResourceUrl(resource).toString();
			} catch (Exception exception) {
				throw new DomCssPreprocessorException("Failed to create a resource URL.", exception);
			}
		} else {
			// HERE BE DRAGONS:
			// In absence of a document, we cannot create a resource URL.
			// This is the case, and totally expected, during code validation.
			// If we'd throw an exception in this case, code validation would erroneously terminate.
			return "";
		}
	}
}
