package com.softicar.platform.ajax.engine;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface AjaxDomEngineJavascriptContainer {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxDomEngineJavascriptContainer.class, Charsets.UTF8);

	IResourceSupplier ACTION_QUEUE = FACTORY.create("ActionQueue.js");
	IResourceSupplier DOM_CONTENT = FACTORY.create("DOMContext.js");
	IResourceSupplier DOM_ELEMENT_BUILDER = FACTORY.create("DomElementBuilder.js");
	IResourceSupplier DOM_EVENT_ENGINE = FACTORY.create("DomEventEngine.js");
	IResourceSupplier DOM_POPUP_ENGINE = FACTORY.create("DomPopupEngine.js");
	IResourceSupplier DRAG_AND_DROP = FACTORY.create("DragAndDrop.js");
	IResourceSupplier ENCODER = FACTORY.create("Encoder.js");
	IResourceSupplier EVENT_DELEGATION = FACTORY.create("EventDelegation.js");
	IResourceSupplier FADING = FACTORY.create("Fading.js");
	IResourceSupplier FOCUS_TRAP = FACTORY.create("FocusTrap.js");
	IResourceSupplier GLOBAL = FACTORY.create("Global.js");
	IResourceSupplier KEEP_ALIVE = FACTORY.create("KeepAlive.js");
	IResourceSupplier MUTEX = FACTORY.create("Mutex.js");
	IResourceSupplier PUSH_BROWSER_HISTORY_STATE = FACTORY.create("PushBrowserHistoryState.js");
	IResourceSupplier SERVER_REQUEST = FACTORY.create("ServerRequest.js");
	IResourceSupplier UTILS = FACTORY.create("Utils.js");
	IResourceSupplier VALUE_NODE_MAP = FACTORY.create("ValueNodeMap.js");
}
