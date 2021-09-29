package com.softicar.platform.core.module.file.stored.overview;

import com.softicar.platform.core.module.file.size.FileSize;
import com.softicar.platform.core.module.file.size.FileSizeFormatter;
import com.softicar.platform.core.module.file.stored.download.StoredFileViewOrDownloadButton;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

class StoredFileOverviewDataTableDivBuilder extends EmfDataTableDivBuilder<StoredFileOverviewRow> {

	public StoredFileOverviewDataTableDivBuilder(StoredFileOverviewDataTable dataTable) {

		super(dataTable);
		setActionColumnHandler(new ActionColumnHandler());
		setColumnHandler(dataTable.getFileSizeColumn(), new FileSizeColumnHandler());
	}

	private class ActionColumnHandler implements IEmfDataTableActionColumnHandler<StoredFileOverviewRow> {

		@Override
		public void buildCell(IEmfDataTableActionCell<StoredFileOverviewRow> cell, StoredFileOverviewRow row) {

			StoredFileViewOrDownloadButton button = new StoredFileViewOrDownloadButton(row.getFile());
			button.showFileNameAsTitle();

			cell.appendChild(button);
		}
	}

	private class FileSizeColumnHandler extends EmfDataTableValueBasedColumnHandler<Long> {

		@Override
		public void buildCell(IEmfDataTableCell<?, Long> cell, Long value) {

			if (value != null) {
				FileSize size = new FileSize(value);
				cell.appendChild(new FileSizeFormatter().formatToHumanReadableBase(size));
			}
		}
	}
}
