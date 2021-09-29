package com.softicar.platform.ajax.resource.registry;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.common.container.map.weak.map.adapter.WeakValueMapAdapter;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class AjaxHashBasedResourceRegistry {

	private final WeakValueMapAdapter<ResourceHash, IResource> resourceHashMap;

	public AjaxHashBasedResourceRegistry() {

		this.resourceHashMap = new WeakValueMapAdapter<>(ConcurrentHashMap::new);
	}

	public Optional<AjaxResourceUrl> register(IResource resource) {

		return resource//
			.getContentHash()
			.map(resourceHash -> {
				resourceHashMap.put(resourceHash, resource);
				return new AjaxResourceUrl(resourceHash);
			});
	}

	public Optional<IResource> getResource(ResourceHash resourceHash) {

		return resourceHashMap.get(resourceHash);
	}
}
