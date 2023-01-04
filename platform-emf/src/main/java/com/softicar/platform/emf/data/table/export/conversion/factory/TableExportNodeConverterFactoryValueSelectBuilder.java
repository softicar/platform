package com.softicar.platform.emf.data.table.export.conversion.factory;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.help.DomHelpPopupButton;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelect;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelectBuilder;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.conversion.factory.selection.TableExportNodeConverterFactorySelectionModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Content type dependent builder for node converter factory select divs.
 *
 * @author Alexander Schmidt
 */
public class TableExportNodeConverterFactoryValueSelectBuilder extends DomSimpleValueSelectBuilder<TableExportNodeConverterFactoryWrapper> {

	private final int targetColumn;
	private final TableExportNodeConverterFactorySelectionModel nodeConverterFactorySelectionModel;
	private final DomParentElement converterFactoryHelpElementParent;

	private TableExportNodeConverterFactoryWrapper preselectedFactoryWrapper = null;
	private TableExportNodeConverterFactoryWrapper defaultFactoryWrapper = null;

	public TableExportNodeConverterFactoryValueSelectBuilder(TableExportNodeConverterFactoryConfiguration nodeConverterFactoryConfiguration,
			TableExportNodeConverterFactorySelectionModel nodeConverterFactorySelectionModel, int targetColumn,
			DomParentElement converterFactoryHelpElementParent) {

		this.targetColumn = targetColumn;
		this.nodeConverterFactorySelectionModel = nodeConverterFactorySelectionModel;
		this.converterFactoryHelpElementParent = converterFactoryHelpElementParent;

		List<TableExportNodeConverterFactoryWrapper> factoryWrappers = new ArrayList<>();

		ITableExportNodeConverterFactory defaultFactory = nodeConverterFactoryConfiguration.getDefaultFactory();
		List<ITableExportNodeConverterFactory> factories = nodeConverterFactoryConfiguration.getAvailableFactories();
		ITableExportNodeConverterFactory preselectedFactory = nodeConverterFactorySelectionModel.getSelectedConverterFactoriesByColumn().get(this.targetColumn);

		for (int i = 0; i < factories.size(); i++) {
			ITableExportNodeConverterFactory factory = factories.get(i);

			TableExportNodeConverterFactoryWrapper factoryWrapper = new TableExportNodeConverterFactoryWrapper(factory, i);
			factoryWrappers.add(factoryWrapper);

			if (factory.equals(defaultFactory)) {
				this.defaultFactoryWrapper = factoryWrapper;
			}

			if (factory.equals(preselectedFactory)) {
				this.preselectedFactoryWrapper = factoryWrapper;
			}
		}

		setValues(factoryWrappers);
		setNilOptionAvailable(false);
		setValueDisplayStringFunction(IEntity::toDisplayWithoutId);
	}

	@Override
	public DomSimpleValueSelect<TableExportNodeConverterFactoryWrapper> build() {

		DomSimpleValueSelect<TableExportNodeConverterFactoryWrapper> select = super.build();

		ModelRefresher modelRefresher = new ModelRefresher(select);
		HelpCellRefresher helpCellRefresher = new HelpCellRefresher(select);

		select.setCallbackOnChange(() -> {
			modelRefresher.refresh();
			helpCellRefresher.refresh();
		});

		if (this.preselectedFactoryWrapper != null) {
			select.selectValue(this.preselectedFactoryWrapper);
		} else if (this.defaultFactoryWrapper != null) {
			select.selectValue(this.defaultFactoryWrapper);
		}

		modelRefresher.refresh();
		helpCellRefresher.refresh();

		return select;
	}

	private class ModelRefresher {

		private final DomSimpleValueSelect<TableExportNodeConverterFactoryWrapper> select;

		public ModelRefresher(DomSimpleValueSelect<TableExportNodeConverterFactoryWrapper> select) {

			this.select = select;
		}

		public void refresh() {

			var factoryWrapper = this.select.getSelectedValue();
			if (factoryWrapper.isPresent()) {
				ITableExportNodeConverterFactory converterFactory = factoryWrapper.get().getConverterFactory();
				nodeConverterFactorySelectionModel.selectConverterFactory(targetColumn, converterFactory);
			} else {
				nodeConverterFactorySelectionModel.unselectConverterFactory(targetColumn);
			}
		}
	}

	private class HelpCellRefresher {

		private final DomSimpleValueSelect<TableExportNodeConverterFactoryWrapper> select;

		public HelpCellRefresher(DomSimpleValueSelect<TableExportNodeConverterFactoryWrapper> select) {

			this.select = select;
		}

		public void refresh() {

			converterFactoryHelpElementParent.removeChildren();

			IDisplayString converterDescription = null;
			IDisplayString converterDisplayString = null;

			var selectedValue = select.getSelectedValue();
			if (selectedValue.isPresent()) {
				ITableExportNodeConverterFactory converterFactory = selectedValue.get().getConverterFactory();

				if (converterFactory != null) {
					converterDescription = converterFactory.getConverterDescription();
					converterDisplayString = converterFactory.getConverterDisplayString();
				}
			}

			if (converterDescription != null) {
				DomButton helpButton =
						new DomHelpPopupButton(DomI18n.HELP, DomI18n.CONVERSION.concatFormat(": \"%s\"", converterDisplayString), converterDescription);

				converterFactoryHelpElementParent.appendChild(helpButton);
			}
		}
	}
}
