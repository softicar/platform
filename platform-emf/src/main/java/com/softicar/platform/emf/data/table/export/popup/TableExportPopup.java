package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.data.table.export.engine.factory.TableExportEngineFactoryEnum;
import com.softicar.platform.emf.data.table.export.file.name.ITableExportFileNameSuffixCreator;
import com.softicar.platform.emf.data.table.export.file.name.TableExportDefaultFileNameSuffixCreator;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModelList;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModelListFactory;
import java.util.function.Supplier;

/**
 * Offers various formats (as defined in {@link TableExportEngineFactoryEnum})
 * for selection, to export a specific {@link DomTable}.
 *
 * @author Alexander Schmidt
 */
public class TableExportPopup extends DomPopup {

	public TableExportPopup(TableExportTableModelListFactory tableSupplierContainer, Supplier<String> fileNamePrefixFunction, boolean appendTimestamp,
			boolean enableDeflateCompression) {

		setCaption(DomI18n.EXPORT_TABLE);

		TableExportPopupLowerMessageDiv lowerMessageDiv = new TableExportPopupLowerMessageDiv();
		ITableExportFileNameSuffixCreator fileNameSuffixCreator = new TableExportDefaultFileNameSuffixCreator();

		String initialFileNamePrefix = createInitialFileNamePrefix(fileNamePrefixFunction);
		TableExportTableModelList tableModelList = tableSupplierContainer.create(initialFileNamePrefix);
		TableExportTableModel mainTableModel = tableModelList.getMainTableModel();

		TableExportPopupConfigurationInputForm inputForm = new TableExportPopupConfigurationInputForm(//
			mainTableModel,
			fileNameSuffixCreator,
			lowerMessageDiv,
			appendTimestamp,
			enableDeflateCompression);

		getCloseManager().setCloseCallback(() -> handleClose(inputForm));

		appendChild(inputForm);
		appendChild(lowerMessageDiv);
		appendActionNode(new TableExportPopupExecuteExportButton(this, inputForm, tableModelList));
		appendCancelButton();
	}

	private String createInitialFileNamePrefix(Supplier<String> fileNamePrefixFunction) {

		String fileNamePrefix = fileNamePrefixFunction != null? fileNamePrefixFunction.get() : null;

		if (fileNamePrefix == null || fileNamePrefix.trim().isEmpty()) {
			return DomI18n.EXPORT.toString();
		} else {
			return fileNamePrefix;
		}
	}

	private void handleClose(ITableExportPopupOptionsProvider exportOptionsProvider) {

		DomPopup columnSelectionPopup = exportOptionsProvider.getColumnSelectionPopup();
		if (columnSelectionPopup != null) {
			columnSelectionPopup.hide();
		}
	}
}
