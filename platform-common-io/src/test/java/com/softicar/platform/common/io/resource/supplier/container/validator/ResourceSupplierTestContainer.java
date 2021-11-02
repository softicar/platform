package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
interface ResourceSupplierTestContainer {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ResourceSupplierTestContainer.class);

	IResourceSupplier FIRST = FACTORY.create("first.file");
	IResourceSupplier SECOND = FACTORY.create("second.file");
}
