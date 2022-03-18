package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface ResourceSupplierTestContainerWithAmbiguousResourceKey {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ResourceSupplierTestContainerWithAmbiguousResourceKey.class);

	IResourceSupplier STYLE_CSS = FACTORY.create("style.css");
	IResourceSupplier STYLE_JS = FACTORY.create("style.js");
	IResourceSupplier STYLE_PNG = FACTORY.create("style.png");
}
