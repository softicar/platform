package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

/**
 * Provides icons for standard DOM elements.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@ResourceSupplierContainer
public interface DomElementsImages {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(DomElementsImages.class);

	IResourceSupplier CALENDAR_DAY = FACTORY.create("calendar-day.svg");
	IResourceSupplier CALENDAR_TODAY = FACTORY.create("calendar-today.svg");
	IResourceSupplier DIAGNOSTIC_ERROR = FACTORY.create("diagnostic-error.svg");
	IResourceSupplier DIAGNOSTIC_INFO = FACTORY.create("diagnostic-info.svg");
	IResourceSupplier DIAGNOSTIC_SUCCESS = FACTORY.create("diagnostic-success.svg");
	IResourceSupplier DIAGNOSTIC_WARNING = FACTORY.create("diagnostic-warning.svg");
	IResourceSupplier DIALOG_CANCEL = FACTORY.create("dialog-cancel.svg");
	IResourceSupplier DIALOG_CLOSE = FACTORY.create("dialog-close.svg");
	IResourceSupplier DIALOG_OKAY = FACTORY.create("dialog-okay.svg");
	IResourceSupplier DIALOG_OKAY_ALL = FACTORY.create("dialog-okay-all.svg");
	IResourceSupplier ENTRY_ADD = FACTORY.create("entry-add.svg");
	IResourceSupplier ENTRY_REMOVE = FACTORY.create("entry-remove.svg");
	IResourceSupplier FILTER = FACTORY.create("filter.svg");
	IResourceSupplier FILTER_ADD = FACTORY.create("filter-add.svg");
	IResourceSupplier FILTER_REMOVE = FACTORY.create("filter-remove.svg");
	IResourceSupplier HELP = FACTORY.create("help.svg");
	IResourceSupplier INFO = FACTORY.create("info.svg");
	IResourceSupplier MOVE_DOWN = FACTORY.create("move-down.svg");
	IResourceSupplier MOVE_UP = FACTORY.create("move-up.svg");
	IResourceSupplier MOVE_VERTICAL = FACTORY.create("move-vertical.svg");
	IResourceSupplier PAGE_NEXT = FACTORY.create("page-next.svg");
	IResourceSupplier PAGE_NEXT_DISABLED = FACTORY.create("page-next-disabled.svg");
	IResourceSupplier PAGE_PREVIOUS = FACTORY.create("page-previous.svg");
	IResourceSupplier PAGE_PREVIOUS_DISABLED = FACTORY.create("page-previous-disabled.svg");
	IResourceSupplier POPUP_TITLE_BAR_CLOSE = FACTORY.create("popup-title-bar-close.svg");
	IResourceSupplier TABLE_COLUMN_SELECTION = FACTORY.create("table-column-selection.svg");
	IResourceSupplier TABLE_EXPORT = FACTORY.create("table-export.svg");
	IResourceSupplier TABLE_ROW_SELECTION_INVERT_CURRENT_PAGE = FACTORY.create("table-row-selection-invert-current-page.svg");
	IResourceSupplier TABLE_ROW_SELECTION_SELECT_CURRENT_PAGE = FACTORY.create("table-row-selection-select-current-page.svg");
	IResourceSupplier TABLE_ROW_SELECTION_UNSELECT_ALL_PAGES = FACTORY.create("table-row-selection-unselect-all-pages.svg");
	IResourceSupplier TABLE_ROW_SELECTION_UNSELECT_CURRENT_PAGE = FACTORY.create("table-row-selection-unselect-current-page.svg");
	IResourceSupplier TABLE_SHOW_ALL_ROWS = FACTORY.create("table-show-all-rows.svg");
	IResourceSupplier UNDO = FACTORY.create("undo.svg");
}
