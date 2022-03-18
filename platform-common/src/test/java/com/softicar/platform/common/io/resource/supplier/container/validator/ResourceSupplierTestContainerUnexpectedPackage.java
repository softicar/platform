package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface ResourceSupplierTestContainerUnexpectedPackage {

	// Intentionally pass an anchor class that is not in the same package as this interface.
	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(Object.class);

	IResourceSupplier YYY = FACTORY.create("yyy.file");
}
