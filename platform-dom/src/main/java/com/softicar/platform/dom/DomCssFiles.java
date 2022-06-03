package com.softicar.platform.dom;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface DomCssFiles {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(DomCssFiles.class, Charsets.UTF8);

	IResourceSupplier DOM_JSON_DISPLAY_STYLE = FACTORY.create("dom-json-display-style.css");
	IResourceSupplier DOM_STYLE = FACTORY.create("dom-style.css");
}
