package com.softicar.platform.core.module.page;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface PageResources {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(PageResources.class);

	IResourceSupplier PAGE_STYLE = FACTORY.create("page-style.css");
}
