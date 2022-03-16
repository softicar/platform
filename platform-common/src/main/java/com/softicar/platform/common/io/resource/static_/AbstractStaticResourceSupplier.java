package com.softicar.platform.common.io.resource.static_;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.ResourceKey;

public abstract class AbstractStaticResourceSupplier implements IStaticResourceSupplier {

	protected final ResourceKey resourceKey;
	protected final IResource staticResource;

	public AbstractStaticResourceSupplier(IStaticResource staticResource) {

		this.resourceKey = new ResourceKey(staticResource.getAnchorClass(), staticResource.getFilename().get());
		this.staticResource = staticResource;
	}

	@Override
	public ResourceKey getResourceKey() {

		return resourceKey;
	}

	@Override
	public IResource getStaticResource() {

		return staticResource;
	}
}
