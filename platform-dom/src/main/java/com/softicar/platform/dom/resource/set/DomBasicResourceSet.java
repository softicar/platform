package com.softicar.platform.dom.resource.set;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The most basic implementation of {@link IDomResourceSet}.
 *
 * @author Alexander Schmidt
 */
public class DomBasicResourceSet implements IDomResourceSet {

	private final Map<IResourceKey, IResource> resources;

	public DomBasicResourceSet() {

		this.resources = new HashMap<>();
	}

	public void put(IResourceKey key, IResource resource) {

		resources.put(key, resource);
	}

	public void putAll(DomBasicResourceSet other) {

		resources.putAll(other.resources);
	}

	@Override
	public Optional<IResource> getResource(IResourceKey resourceKey) {

		return Optional.ofNullable(resources.get(resourceKey));
	}
}
