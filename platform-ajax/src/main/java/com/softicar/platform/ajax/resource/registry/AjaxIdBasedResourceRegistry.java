package com.softicar.platform.ajax.resource.registry;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.registry.ResourceRegistry;
import java.util.Optional;

class AjaxIdBasedResourceRegistry {

	private final ResourceRegistry registry;

	public AjaxIdBasedResourceRegistry() {

		this.registry = new ResourceRegistry();
	}

	public synchronized AjaxResourceUrl register(IResource resource) {

		return new AjaxResourceUrl(registry.register(resource));
	}

	public synchronized Optional<IResource> getResource(int resourceId) {

		return registry.getResource(resourceId);
	}
}
