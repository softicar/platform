package com.softicar.platform.dom.resource.supplier;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.static_.AbstractStaticResourceSupplier;
import com.softicar.platform.common.io.resource.static_.IStaticResource;
import com.softicar.platform.dom.resource.set.CurrentDomResourceSet;

/**
 * A proxy for an {@link IResource}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomResourceSupplierProxy extends AbstractStaticResourceSupplier {

	public DomResourceSupplierProxy(IStaticResource staticResource) {

		super(staticResource);
	}

	@Override
	public IResource getResource() {

		return CurrentDomResourceSet//
			.get()
			.getResource(resourceKey)
			.orElseThrow();
	}
}
