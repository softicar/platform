package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import java.util.Collection;

/**
 * Executes the actual table export, based upon an instantiated
 * {@link ITableExportEngine}.
 *
 * @author Alexander Schmidt
 */
class TableExportPopupExecuteExportButton extends DomButton {

	private final DomPopup popup;
	private final ITableExportPopupOptionsProvider exportOptionsProvider;
	private final Collection<TableExportTableModel> tableModels;

	public TableExportPopupExecuteExportButton(DomPopup popup, ITableExportPopupOptionsProvider exportOptionsProvider,
			Collection<TableExportTableModel> tableModels) {

		this.popup = popup;
		this.exportOptionsProvider = exportOptionsProvider;
		this.tableModels = tableModels;

		setIcon(DomImages.TABLE_EXPORT.getResource());
		setLabel(DomI18n.EXPORT);
		setClickCallback(this::handleClick);
	}

	private void handleClick() {

		ITableExportEngine<?> engine = this.exportOptionsProvider.getCurrentEngineOrNull();

		if (engine != null) {
			String fileNamePrefix = this.exportOptionsProvider.getFileNamePrefixOrNull();

			if (fileNamePrefix != null) {
				boolean appendTimestamp = this.exportOptionsProvider.isAppendTimestamp();
				boolean enableDeflateCompression = this.exportOptionsProvider.isEnableDeflateCompression();

				engine.setFileNamePrefix(fileNamePrefix);
				engine.setAppendTimestamp(appendTimestamp);
				engine.setEnableDeflateCompression(enableDeflateCompression);
				engine.export(tableModels);

				this.popup.close();
			}
		} else {
			throw new SofticarUserException(DomI18n.PLEASE_SELECT_AN_EXPORT_FORMAT);
		}
	}
}
