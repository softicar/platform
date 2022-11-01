package com.softicar.platform.ajax;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

/**
 * Provides {@link IResource} objects for testing.
 *
 * @author Oliver Richers
 */
@ResourceSupplierContainer
public interface AjaxTestResources {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxTestResources.class);

	IResourceSupplier TEST_PNG = FACTORY.create("test.png");
	IResourceSupplier TEST_TIFF = FACTORY.create("test.tiff");
}
