package com.softicar.platform.dom.resource.set;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.dom.resource.preprocessed.DomPreprocessedCssResource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of {@link IDomResourceSet} that extends
 * {@link DomBasicResourceSet} with a fallback to bundled, static default
 * resources, in case it contains no resource for a given {@link IResourceKey}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomResourceSet extends DomBasicResourceSet {

	private final Map<IResourceKey, IResource> preprocessedResources;

	public DomResourceSet() {

		this.preprocessedResources = new HashMap<>();
	}

	@Override
	public Optional<IResource> getResource(IResourceKey resourceKey) {

		return super.getResource(resourceKey)//
			.or(() -> DefaultDomResourceSet.getInstance().getResource(resourceKey))
			.map(resource -> preprocess(resource, resourceKey));
	}

	private IResource preprocess(IResource resource, IResourceKey resourceKey) {

		if (resource.getMimeType().equals(MimeType.TEXT_CSS)) {
			return preprocessedResources.computeIfAbsent(resourceKey, dummy -> new DomPreprocessedCssResource(resource, this));
		} else {
			return resource;
		}
	}
}
