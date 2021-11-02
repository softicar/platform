package com.softicar.platform.ajax.engine.indicator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface AjaxDomEngineIndicatorJavascriptContainer {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxDomEngineIndicatorJavascriptContainer.class, Charsets.UTF8);

	IResourceSupplier IMAGE = FACTORY.create("IndicatorImage.js");
	IResourceSupplier LOADING = FACTORY.create("IndicatorLoading.js");
	IResourceSupplier TEXT = FACTORY.create("IndicatorText.js");
}
