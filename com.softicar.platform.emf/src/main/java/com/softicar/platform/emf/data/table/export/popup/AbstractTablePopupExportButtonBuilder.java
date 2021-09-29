package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModelListFactory;
import java.util.List;
import java.util.function.Supplier;

/**
 * Partial implementation of {@link ITableExportPopupButtonBuilder} comprising
 * table setters with optional support to deny table re-binding. Also provides
 * button/label related setters.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractTablePopupExportButtonBuilder implements ITableExportPopupButtonBuilder {

	public abstract DomButton createExportButton();

	// ----

	protected TableExportTableModelListFactory tableSupplierContainer;
	protected IDisplayString label = DomI18n.EXPORT_TABLE;
	protected boolean showLabel = true;

	public AbstractTablePopupExportButtonBuilder() {

		this.tableSupplierContainer = new TableExportTableModelListFactory();
	}

	@Override
	public ITableExportPopupButtonBuilder addMainTable(DomTable table) {

		addMainTable(() -> table);
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder addMainTable(Supplier<DomTable> tableSupplier) {

		tableSupplierContainer.addMainTable(tableSupplier);
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder addSupportTable(DomTable table) {

		addSupportTable(() -> table);
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder addSupportTable(Supplier<DomTable> tableSupplier) {

		tableSupplierContainer.addSupportTable(tableSupplier);
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder addSupportTables(List<DomTable> tableList) {

		for (DomTable table: tableList) {
			addSupportTable(() -> table);
		}

		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder setLabel(IDisplayString label) {

		this.label = label;
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder setShowLabel(boolean showLabel) {

		this.showLabel = showLabel;
		return this;
	}

	@Override
	public final DomButton build() {

		validate();

		return createExportButton();
	}

	private void validate() {

		if (tableSupplierContainer.getTableSuppliers().isEmpty()) {
			throw new SofticarUserException(DomI18n.EXPORT_CANNOT_BE_PERFORMED_NO_TABLE_SPECIFIED);
		}
	}
}
