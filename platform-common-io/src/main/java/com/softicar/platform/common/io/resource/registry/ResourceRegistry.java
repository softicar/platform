package com.softicar.platform.common.io.resource.registry;

import com.softicar.platform.common.container.map.weak.WeakIntHashMap;
import com.softicar.platform.common.container.map.weak.identity.WeakIdentityHashMap;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceRegistry;
import java.util.Optional;

/**
 * Default implementation of {@link IResourceRegistry}.
 *
 * @author Oliver Richers
 */
public class ResourceRegistry implements IResourceRegistry {

	private final WeakIdentityHashMap<IResource, Integer> resourceToId;
	private final WeakIntHashMap<IResource> idToResource;
	private int counter;

	public ResourceRegistry() {

		this.counter = 1;
		this.resourceToId = new WeakIdentityHashMap<>();
		this.idToResource = new WeakIntHashMap<>();
	}

	@Override
	public Optional<IResource> getResource(int resourceId) {

		resourceToId.collect();
		idToResource.collect();

		return Optional.ofNullable(idToResource.get(resourceId));
	}

	@Override
	public int register(IResource resource) {

		resourceToId.collect();
		idToResource.collect();

		// check if resource was already registered
		Integer resourceId = resourceToId.get(resource);
		if (resourceId != null) {
			return resourceId;
		}

		return doRegister(resource);
	}

	private int doRegister(IResource resource) {

		int resourceId = allocateResourceId();
		resourceToId.put(resource, resourceId);
		idToResource.put(resourceId, resource);
		return resourceId;
	}

	private int allocateResourceId() {

		int resourceId = counter;
		counter += 1;
		return resourceId;
	}
}
