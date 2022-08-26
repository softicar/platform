package com.softicar.platform.dom;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

/**
 * Provides images for the DOM framework.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@ResourceSupplierContainer
public interface DomImages {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(DomImages.class);

	IResourceSupplier EMBLEM_AUTO_COMPLETE_VALUE_AMBIGUOUS = FACTORY.create("emblem-auto-complete-value-ambiguous.svg");
	IResourceSupplier EMBLEM_AUTO_COMPLETE_VALUE_ILLEGAL = FACTORY.create("emblem-auto-complete-value-illegal.svg");
}
