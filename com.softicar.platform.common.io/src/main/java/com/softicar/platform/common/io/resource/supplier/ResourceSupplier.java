package com.softicar.platform.common.io.resource.supplier;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.static_.AbstractStaticResourceSupplier;
import com.softicar.platform.common.io.resource.static_.IStaticResource;

/**
 * Supplies a static {@link IResource}.
 *
 * @author Alexander Schmidt
 */
public class ResourceSupplier extends AbstractStaticResourceSupplier {

	public ResourceSupplier(IStaticResource staticResource) {

		super(staticResource);
	}

	@Override
	public IResource getResource() {

		return staticResource;
	}
}
