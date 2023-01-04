package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;

/**
 * Implemented by elements that provide the options which are required to
 * actually perform a table export.
 *
 * @author Alexander Schmidt
 */
public interface ITableExportPopupOptionsProvider {

	ITableExportEngine getCurrentEngineOrNull();

	DomPopup getColumnSelectionPopup();

	String getFileNamePrefixOrNull();

	boolean isAppendTimestamp();

	boolean isEnableDeflateCompression();
}
