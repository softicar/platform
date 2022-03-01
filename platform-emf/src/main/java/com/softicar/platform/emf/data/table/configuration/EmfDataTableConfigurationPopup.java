package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomFieldset;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.text.DomListeningTextInput;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.IEmfDataTableController;

class EmfDataTableConfigurationPopup<R> extends DomPopup {

	private final IEmfDataTableController<R> controller;
	private final EmfDataTableConfigurationModel<R> model;
	private final EmfDataTableConfigurationTableDiv<R> configurationTable;
	private final PageSizeInputElement pageSizeInputElement;

	public EmfDataTableConfigurationPopup(IEmfDataTableController<R> controller) {

		this.controller = controller;
		this.model = new EmfDataTableConfigurationModel<>(controller).load();
		this.configurationTable = new EmfDataTableConfigurationTableDiv<>(model);
		this.pageSizeInputElement = new PageSizeInputElement();

		setCaption(EmfDataTableI18n.TABLE_CONFIGURATION);
		appendChild(new DomFieldset()).appendChild(pageSizeInputElement);
		appendChild(configurationTable);

		if (!controller.getDataTable().getIdentifier().isPresent()) {
			appendChild(new DomMessageDiv(DomMessageType.INFO, EmfI18n.THE_CONFIGURATION_OF_THIS_TABLE_IS_NOT_STORED_PERSISTENTLY));
		}

		appendActionNode(new ApplyButton());
		appendActionNode(new ResetButton());
		appendCancelButton().setMarker(EmfDataTableConfigurationMarker.CANCEL_BUTTON);
		addCssClass(EmfCssClasses.EMF_DATA_TABLE_CONFIGURATION_POPUP);
	}

	private void applyAndHide() {

		model.setPageSize(pageSizeInputElement.getPageSize());
		model.apply();
		controller.savePersistentTableConfiguration();
		hide();
	}

	private void resetToDefaults() {

		model.loadDefaults();
		configurationTable.refreshTable();
		pageSizeInputElement.setPageSize(model.getPageSize());
	}

	private class PageSizeInputElement extends DomBar {

		private final DomTextInput input;

		public PageSizeInputElement() {

			this.input = new DomListeningTextInput(() -> applyAndHide());
			this.input.setValue("" + controller.getPageSize());
			this.input.setMarker(EmfDataTableConfigurationMarker.PAGE_SIZE_INPUT);

			addCssClass(EmfCssClasses.EMF_DATA_TABLE_CONFIGURATION_PAGE_SIZE_INPUT);
			appendText(EmfDataTableI18n.ROWS_PER_PAGE.concatColon());
			appendChild(this.input);
			appendText(" / " + EmfDataTableI18n.ARG1_TOTAL.toDisplay(controller.getTotalRowCount()));
			appendChild(new DomActionBar(new ShowAllButton()));
		}

		public Integer getPageSize() {

			return IntegerParser.parseInteger(input.getTextOrNull());
		}

		public void setPageSize(int pageSize) {

			input.setValue("" + pageSize);
		}

		private class ShowAllButton extends DomButton {

			public ShowAllButton() {

				setIcon(DomElementsImages.TABLE_SHOW_ALL_ROWS.getResource());
				setLabel(DomI18n.ALL);
				setClickCallback(this::handleClick);
				setMarker(EmfDataTableConfigurationMarker.PAGE_SIZE_ALL_BUTTON);
			}

			private void handleClick() {

				input.setValue("" + Math.max(controller.getTotalRowCount(), controller.getDefaultPageSize()));
				input.focus();
			}
		}
	}

	private class ApplyButton extends DomButton {

		public ApplyButton() {

			setIcon(DomElementsImages.DIALOG_OKAY.getResource());
			setLabel(EmfDataTableI18n.APPLY);
			setMarker(EmfDataTableConfigurationMarker.APPLY_BUTTON);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			applyAndHide();
		}
	}

	private class ResetButton extends DomButton {

		public ResetButton() {

			setLabel(EmfDataTableI18n.RESET);
			setIcon(EmfImages.RESET.getResource());
			setMarker(EmfDataTableConfigurationMarker.RESET_BUTTON);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			resetToDefaults();
		}
	}
}
