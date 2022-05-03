package com.softicar.platform.ajax.engine;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface AjaxDomEngineJavascriptContainer {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxDomEngineJavascriptContainer.class, Charsets.UTF8);

	IResourceSupplier DOM_CONTENT = FACTORY.create("DOMContext.js");
	IResourceSupplier DOM_ELEMENT_BUILDER = FACTORY.create("DomElementBuilder.js");
	IResourceSupplier DOM_EVENT_ENGINE = FACTORY.create("DomEventEngine.js");
	IResourceSupplier DOM_POPUP_ENGINE = FACTORY.create("DomPopupEngine.js");
	IResourceSupplier DRAG_AND_DROP = FACTORY.create("DragAndDrop.js");
	IResourceSupplier EVENT_DELEGATION = FACTORY.create("EventDelegation.js");
	IResourceSupplier FOCUS_TRAP = FACTORY.create("FocusTrap.js");
	IResourceSupplier GLOBAL = FACTORY.create("Global.js");
	IResourceSupplier PUSH_BROWSER_HISTORY_STATE = FACTORY.create("PushBrowserHistoryState.js");
	IResourceSupplier TYPE_SCRIPT_GENERATED = FACTORY.create("TypeScriptGenerated.js");
	IResourceSupplier UTILS = FACTORY.create("Utils.js");
	IResourceSupplier VALUE_NODE_MAP = FACTORY.create("ValueNodeMap.js");
}
