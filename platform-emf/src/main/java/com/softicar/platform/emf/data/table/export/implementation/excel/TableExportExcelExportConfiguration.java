package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import java.util.Objects;

public class TableExportExcelExportConfiguration {

	private TableExportExcelExportFormat format;
	private IDisplayString defaultSheetTitle;
	private boolean sheetPerTable;

	public TableExportExcelExportConfiguration() {

		this.format = TableExportExcelExportFormat.XLSX;
		this.defaultSheetTitle = DomI18n.SHEET;
		this.sheetPerTable = false;
	}

	public TableExportExcelExportFormat getFormat() {

		return format;
	}

	public IDisplayString getDefaultSheetTitle() {

		return defaultSheetTitle;
	}

	public boolean isSheetPerTable() {

		return sheetPerTable;
	}

	public TableExportExcelExportConfiguration setFormat(TableExportExcelExportFormat format) {

		this.format = Objects.requireNonNull(format);
		return this;
	}

	public TableExportExcelExportConfiguration setDefaultSheetTitle(IDisplayString title) {

		this.defaultSheetTitle = Objects.requireNonNull(title);
		return this;
	}

	public TableExportExcelExportConfiguration setSheetPerTable(boolean enabled) {

		this.sheetPerTable = enabled;
		return this;
	}
}
