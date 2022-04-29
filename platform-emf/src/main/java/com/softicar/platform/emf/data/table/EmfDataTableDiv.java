package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.column.settings.IEmfDataTableColumnSettings;
import com.softicar.platform.emf.data.table.configuration.EmfDataTableConfigurationButtonBuilder;
import com.softicar.platform.emf.data.table.export.button.EmfDataTableExportButtonBuilder;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.row.selection.EmfTableRowSelectionControlElement;
import java.util.Collection;
import java.util.List;

/**
 * This is a {@link DomDiv} containing a {@link EmfDataTable} and its
 * navigations.
 * <p>
 * Because a {@link EmfDataTable} is rather useless without proper navigation
 * controls, this {@link DomDiv} aggregates the table and its navigation
 * controls into a single element.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
class EmfDataTableDiv<R> extends DomDiv implements IEmfDataTableDiv<R> {

	private final EmfDataTable<R> table;
	private final EmfTableRowSelectionControlElement rowSelectionDiv;

	protected EmfDataTableDiv(IEmfDataTableConfig<R> config) {

		this.table = new EmfDataTable<>(config, this::getRowSelectionDiv);

		if (!config.isHideNavigationAtTop()) {
			appendChild(createNavigation(table, config.isHideNavigationActionButtions()));
		}

		appendChild(table);

		if (config.isRowSelectionEnabled()) {
			this.rowSelectionDiv = new EmfTableRowSelectionControlElement(new EmfDataTableRowSelectionProxy<>(table), table);
			if (config.getRowSelectionActionButtons() != null) {
				rowSelectionDiv.appendChild(new DomPreformattedLabel(" "));
				for (DomButton button: config.getRowSelectionActionButtons()) {
					rowSelectionDiv.appendChild(button);
				}
			}
			appendChild(rowSelectionDiv);
		} else {
			this.rowSelectionDiv = null;
		}

		if (!config.isHideNavigationAtBottom()) {
			appendChild(createNavigation(table, config.isHideNavigationActionButtions()));
		}

		config.getTableDivMarkers().forEach(this::addMarker);
		config.getTableMarkers().forEach(table::addMarker);

		setCssClass(EmfCssClasses.EMF_DATA_TABLE_DIV);
	}

	@Override
	public void refresh() {

		table.refresh();
	}

	private EmfTableRowSelectionControlElement getRowSelectionDiv() {

		return rowSelectionDiv;
	}

	private IDomElement createNavigation(EmfDataTable<R> table, boolean hideNavigationActionButtons) {

		if (hideNavigationActionButtons) {
			return table.createNavigation();
		} else {
			return table
				.createNavigation(//
					new EmfDataTableConfigurationButtonBuilder<>(table.getController()),
					new EmfDataTableExportButtonBuilder(table));
		}
	}

	// -------------------- rows -------------------- //

	@Override
	public int getTotalRowCount() {

		return table.getTotalRowCount();
	}

	@Override
	public List<IEmfDataTableRow<R>> getDisplayedTableRows() {

		return table.getDisplayedTableRows();
	}

	// -------------------- row selection -------------------- //

	@Override
	public boolean isRowSelectionEnabled() {

		return table.getController().isRowSelectionEnabled();
	}

	@Override
	public EmfDataTableRowSelectionMode getRowSelectionMode() {

		return table.getController().getRowSelectionMode();
	}

	@Override
	public Collection<R> getSelectedRows() {

		return table.getController().getSelectedRows();
	}

	@Override
	public void unselectAllRowsOfTable() {

		table.getController().unselectAllRowsOfTable();
	}

	// -------------------- column properties -------------------- //

	@Override
	public IEmfDataTableColumnSettings getColumnSettings(IDataTableColumn<R, ?> column) {

		return table.getController().getColumnSettings(column);
	}

	@Override
	public boolean isSortable(IDataTableColumn<R, ?> column) {

		return table.getController().isSortable(column);
	}

	@Override
	public boolean isFilterable(IDataTableColumn<R, ?> column) {

		return table.getController().isFilterable(column);
	}

	// -------------------- paging -------------------- //

	@Override
	public int getCurrentPage() {

		return table.getCurrentPage();
	}

	@Override
	public void setCurrentPage(int pageIndex) {

		table.setCurrentPage(pageIndex);
	}

	@Override
	public void showLastPage() {

		table.setCurrentPage(table.getPageCount() - 1);
	}

	@Override
	public int getPageCount() {

		return table.getPageCount();
	}

	@Override
	public int getPageSize() {

		return table.getPageSize();
	}

	// -------------------- export -------------------- //

	@Override
	public void export(ITableExportEngine<?> engine) {

		engine.export(table);
	}
}
