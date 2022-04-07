package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.popup.help.DomHelpPopupButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelect;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.emf.data.table.export.column.selection.TableExportColumnSelectionButton;
import com.softicar.platform.emf.data.table.export.column.selection.TableExportColumnSelectionPopup;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.engine.factory.TableExportEngineFactories;
import com.softicar.platform.emf.data.table.export.file.name.ITableExportFileNameSuffixCreator;
import com.softicar.platform.emf.data.table.export.file.name.TableExportFileNameWithOmittableSuffix;
import com.softicar.platform.emf.data.table.export.file.name.TableExportFileTimestampMode;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Contains the input elements of {@link TableExportPopup}.
 * <p>
 * TODO: Has gotten fairly complex. Simplify.
 *
 * @author Alexander Schmidt
 */
public class TableExportPopupConfigurationInputForm extends DomLabelGrid implements ITableExportPopupOptionsProvider {

	private final TableExportTableModel mainTableModel;
	private final TableExportPopupLowerMessageDiv lowerMessageDiv;
	private final ITableExportFileNameSuffixCreator fileNameSuffixCreator;

	private final DomTextInput inputFileName;
	private final DomCheckbox inputAppendTimestamp;
	private final DomCheckbox inputEnableDeflateCompression;
	private final FileNameSuffixDiv fileNameSuffixDiv;
	private final DomSimpleValueSelect<ITableExportEngineFactory<?>> inputEngineFactorySelect;
	private final Map<String, ITableExportEngine<?>> engineByFactoryClassName = new TreeMap<>();

	private TableExportColumnSelectionButton columnSelectionButton;

	private ITableExportEngine<?> currentEngine = null;

	public TableExportPopupConfigurationInputForm(TableExportTableModel mainTableModel, ITableExportFileNameSuffixCreator fileNameSuffixCreator,
			TableExportPopupLowerMessageDiv lowerMessageDiv, boolean appendTimestamp, boolean enableDeflateCompression) {

		this.mainTableModel = mainTableModel;
		this.fileNameSuffixCreator = fileNameSuffixCreator;
		this.lowerMessageDiv = lowerMessageDiv;
		this.inputFileName = new DomTextInput(mainTableModel.getTableName().orElse(""));
		this.fileNameSuffixDiv = new FileNameSuffixDiv();
		this.inputAppendTimestamp = new DomCheckbox(appendTimestamp, it -> fileNameSuffixDiv.refresh());
		this.inputEnableDeflateCompression = new DomCheckbox(enableDeflateCompression, it -> fileNameSuffixDiv.refresh());

		List<ITableExportEngineFactory<?>> factories = TableExportEngineFactories.getAllFactories();

		this.inputEngineFactorySelect = new DomSimpleValueSelect<ITableExportEngineFactory<?>>().setValues(factories);
		this.inputEngineFactorySelect.selectValue(TableExportEngineFactories.getDefaultFactory());
		this.inputEngineFactorySelect.setCallbackOnChange(this::refresh);
		refresh();
	}

	@Override
	public TableExportColumnSelectionPopup getColumnSelectionPopup() {

		return columnSelectionButton != null? columnSelectionButton.getTableExportColumnSelectionPopup() : null;
	}

	@Override
	public ITableExportEngine<?> getCurrentEngineOrNull() {

		return this.currentEngine;
	}

	@Override
	public String getFileNamePrefixOrNull() {

		String fileName = this.inputFileName.getInputTextTrimmed();

		if (!fileName.isBlank() && TableExportLib.validateFileName(fileName)) {
			return fileName;
		} else {
			throw new SofticarUserException(DomI18n.PLEASE_ENTER_A_VALID_FILE_NAME);
		}
	}

	@Override
	public boolean isAppendTimestamp() {

		return this.inputAppendTimestamp.isChecked();
	}

	@Override
	public boolean isEnableDeflateCompression() {

		return this.inputEnableDeflateCompression.isChecked();
	}

	private void refresh() {

		removeChildren();

		inputEngineFactorySelect.disappend();
		inputFileName.disappend();
		fileNameSuffixDiv.disappend();
		inputAppendTimestamp.disappend();
		inputEnableDeflateCompression.disappend();

		appendEngineSelect();
		refreshForSelectedEngine();
	}

	private void appendEngineSelect() {

		add(DomI18n.EXPORT_FORMAT, inputEngineFactorySelect);
	}

	private void refreshForSelectedEngine() {

		var selectedFactory = this.inputEngineFactorySelect.getSelectedValue();
		if (selectedFactory.isPresent()) {
			add(DomI18n.FILE_NAME, new InputBar(inputFileName, fileNameSuffixDiv));
			add(DomI18n.APPEND_TIMESTAMP, new InputBar(inputAppendTimestamp, new HelpTextButtonOptionTimestamp()));
			add(DomI18n.COMPRESS_FILE, new InputBar(inputEnableDeflateCompression, new HelpTextButtonOptionDeflateCompression()));

			TableExportPopupConfigurationDivBuilder configurationDivBuilder = new TableExportPopupConfigurationDivBuilder();

			ITableExportEngineFactory<?> factory = selectedFactory.get();

			this.currentEngine = fetchOrCreateEngine(factory);
			if (getColumnSelectionPopup() != null) {
				getColumnSelectionPopup().hide();
			}
			this.columnSelectionButton = new TableExportColumnSelectionButton(this.mainTableModel, currentEngine);

			configurationDivBuilder.addButton(columnSelectionButton);

			this.inputEnableDeflateCompression.setValue(factory.getEngineConfiguration().isCompressed());

			if (!configurationDivBuilder.getButtons().isEmpty()) {
				add(DomI18n.CONFIGURATION, configurationDivBuilder.build());
			}

		} else {
			this.currentEngine = null;
		}

		this.fileNameSuffixDiv.refresh();
		this.lowerMessageDiv.refreshForSelectedFactory(this.mainTableModel.getTable(), selectedFactory);
	}

	/**
	 * Facilitates re-using already created engines to avoid the selected node
	 * converter factories being reset upon selecting a different engine
	 * factory.
	 *
	 * @param factory
	 * @return An {@link ITableExportEngine} as created by the given factory.
	 */
	private ITableExportEngine<?> fetchOrCreateEngine(ITableExportEngineFactory<?> factory) {

		String factoryClassName = factory.getClass().getCanonicalName();
		ITableExportEngine<?> engine = this.engineByFactoryClassName.get(factoryClassName);

		if (engine == null) {
			engine = factory.create();
			this.engineByFactoryClassName.put(factoryClassName, engine);
		}

		return engine;
	}

	// ----

	private class FileNameSuffixDiv extends DomDiv implements IRefreshable {

		@Override
		public void refresh() {

			removeChildren();

			var value = inputEngineFactorySelect.getSelectedValue();
			if (value.isPresent()) {
				TableExportFileNameWithOmittableSuffix fileNameSuffix = fileNameSuffixCreator
					.createFileNameSuffix(
						value.get().getEngineConfiguration().getFileNameExtension(),
						inputAppendTimestamp.isChecked()? TableExportFileTimestampMode.MOCKUP : TableExportFileTimestampMode.NONE,
						inputEnableDeflateCompression.isChecked());

				appendChild(DomTextNode.create(fileNameSuffix.getFileName(false)));
			}
		}
	}

	private class HelpTextButtonOptionDeflateCompression extends DomHelpPopupButton {

		public HelpTextButtonOptionDeflateCompression() {

			super(//
				DomI18n.HELP,
				DomI18n.COMPRESS_FILE,
				DomI18n.IF_THIS_OPTION_IS_SELECTED_THE_EXPORTED_FILE_WILL_BE_COMPRESSED_IN_ZIP_FORMAT);
		}
	}

	private class HelpTextButtonOptionTimestamp extends DomHelpPopupButton {

		public HelpTextButtonOptionTimestamp() {

			super(//
				DomI18n.HELP,
				DomI18n.APPEND_TIMESTAMP,
				DomI18n.IF_THIS_OPTION_IS_SELECTED_THE_NAME_OF_THE_EXPORTED_FILE_WILL_CONTAIN_A_TIMESTAMP_INDICATING_THE_POINT_IN_TIME_AT_WHICH_THE_FILE_WAS_EXPORTED);
		}
	}

	private class InputBar extends DomBar {

		public InputBar(IDomNode inputNode, IDomNode decoration) {

			appendChild(inputNode);
			appendChild(decoration);
		}
	}
}
