package com.softicar.platform.ajax.engine.auto.complete;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface AjaxDomEngineAutoCompleteJavascriptContainer {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxDomEngineAutoCompleteJavascriptContainer.class, Charsets.UTF8);

	IResourceSupplier ENGINE = FACTORY.create("AutoCompleteEngine.js");
	IResourceSupplier IMAGE_INDICATOR = FACTORY.create("AutoCompleteImageIndicator.js");
	IResourceSupplier INPUT_CONFIGURATION = FACTORY.create("AutoCompleteInputConfiguration.js");
	IResourceSupplier INPUT_CONTEXT = FACTORY.create("AutoCompleteInputContext.js");
	IResourceSupplier INPUT_INDICATOR_MODE = FACTORY.create("AutoCompleteInputIndicatorMode.js");
	IResourceSupplier INPUT_VALIDATOR_MODE = FACTORY.create("AutoCompleteInputValidationMode.js");
	IResourceSupplier LOADING_INDICATOR = FACTORY.create("AutoCompleteLoadingIndicator.js");
	IResourceSupplier POPUP = FACTORY.create("AutoCompletePopup.js");
	IResourceSupplier REQUEST = FACTORY.create("AutoCompleteRequest.js");
	IResourceSupplier REQUEST_MANAGER = FACTORY.create("AutoCompleteRequestManager.js");
	IResourceSupplier STATE = FACTORY.create("AutoCompleteState.js");
	IResourceSupplier STATUS_HANDLER = FACTORY.create("AutoCompleteStatusHandler.js");
	IResourceSupplier STATUS_INDICATOR = FACTORY.create("AutoCompleteStatusIndicator.js");
	IResourceSupplier VALUE_STATE = FACTORY.create("AutoCompleteValueState.js");
}
