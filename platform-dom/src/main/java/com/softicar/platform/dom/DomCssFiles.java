package com.softicar.platform.dom;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface DomCssFiles {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(DomCssFiles.class, Charsets.UTF8);

	IResourceSupplier DOM_ACTION_BAR_STYLE = FACTORY.create("dom-action-bar-style.css");
	IResourceSupplier DOM_AUTO_COMPLETE_STYLE = FACTORY.create("dom-auto-complete-style.css");
	IResourceSupplier DOM_BAR_STYLE = FACTORY.create("dom-bar-style.css");
	IResourceSupplier DOM_BUTTON_STYLE = FACTORY.create("dom-button-style.css");
	IResourceSupplier DOM_CHECKBOX_STYLE = FACTORY.create("dom-checkbox-style.css");
	IResourceSupplier DOM_DATA_TABLE_STYLE = FACTORY.create("dom-data-table-style.css");
	IResourceSupplier DOM_DAY_CHOOSER_DIV_STYLE = FACTORY.create("dom-day-chooser-div-style.css");
	IResourceSupplier DOM_DAY_INPUT_STYLE = FACTORY.create("dom-day-input-style.css");
	IResourceSupplier DOM_DAY_TIME_INPUT_STYLE = FACTORY.create("dom-day-time-input-style.css");
	IResourceSupplier DOM_ICON_STYLE = FACTORY.create("dom-icon-style.css");
	IResourceSupplier DOM_IMAGE_VIEWER_STYLE = FACTORY.create("dom-image-viewer-style.css");
	IResourceSupplier DOM_INPUT_DIAGNOSTICS_STYLE = FACTORY.create("dom-input-diagnostics-style.css");
	IResourceSupplier DOM_JSON_DISPLAY_STYLE = FACTORY.create("dom-json-display-style.css");
	IResourceSupplier DOM_LABEL_GRID_STYLE = FACTORY.create("dom-label-grid-style.css");
	IResourceSupplier DOM_MESSAGE_DIV_STYLE = FACTORY.create("dom-message-div-style.css");
	IResourceSupplier DOM_MODAL_POPUP_STYLE = FACTORY.create("dom-modal-popup-style.css");
	IResourceSupplier DOM_MULTI_LINE_STRING_DISPLAY_STYLE = FACTORY.create("dom-multi-line-string-display-style.css");
	IResourceSupplier DOM_PAGEABLE_TABLE_STYLE = FACTORY.create("dom-pageable-table-style.css");
	IResourceSupplier DOM_PERCENTAGE_BAR_STYLE = FACTORY.create("dom-percentage-bar-style.css");
	IResourceSupplier DOM_POPUP_STYLE = FACTORY.create("dom-popup-style.css");
	IResourceSupplier DOM_PREFORMATTED_LABEL_STYLE = FACTORY.create("dom-preformatted-label-style.css");
	IResourceSupplier DOM_SEPARATOR_CELL_STYLE = FACTORY.create("dom-separator-cell-style.css");
	IResourceSupplier DOM_STYLE = FACTORY.create("dom-style.css");
	IResourceSupplier DOM_TAB_BAR_STYLE = FACTORY.create("dom-tab-bar-style.css");
	IResourceSupplier DOM_VERTICAL_TEXT_BOX_STYLE = FACTORY.create("dom-vertical-text-box-style.css");
	IResourceSupplier DOM_WIKI_ELEMENTS_STYLE = FACTORY.create("dom-wiki-elements-style.css");
}
