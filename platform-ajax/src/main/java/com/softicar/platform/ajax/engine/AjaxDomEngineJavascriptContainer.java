package com.softicar.platform.ajax.engine;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface AjaxDomEngineJavascriptContainer {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxDomEngineJavascriptContainer.class, Charsets.UTF8);

	IResourceSupplier DOM_POPUP_ENGINE = FACTORY.create("DomPopupEngine.js");
	IResourceSupplier FOCUS_TRAP = FACTORY.create("FocusTrap.js");
	IResourceSupplier TYPE_SCRIPT_GENERATED = FACTORY.create("TypeScriptGenerated.js");
}
