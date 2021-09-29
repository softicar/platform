package com.softicar.platform.ajax;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface AjaxCssFiles {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxCssFiles.class, Charsets.UTF8);

	IResourceSupplier AJAX_STYLE = FACTORY.create("ajax-style.css");
}
