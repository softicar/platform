package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
interface ResourceSupplierTestContainerWithIllegalFieldOrder {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ResourceSupplierTestContainerWithIllegalFieldOrder.class);

	// A must come before B
	IResourceSupplier B = FACTORY.create("b.file");
	IResourceSupplier A = FACTORY.create("a.file");

	// X_X must come before XXX (underscore is treated as space)
	IResourceSupplier XXX = FACTORY.create("xxx.file");
	IResourceSupplier X_Y = FACTORY.create("x-y.file");
}
