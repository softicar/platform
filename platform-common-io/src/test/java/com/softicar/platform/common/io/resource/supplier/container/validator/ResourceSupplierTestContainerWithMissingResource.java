package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface ResourceSupplierTestContainerWithMissingResource {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ResourceSupplierTestContainerWithMissingResource.class);

	IResourceSupplier MISSING = FACTORY.create("missing.file");
}
