package com.softicar.platform.core.module.file.stored.overview;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.emf.EmfI18n;
import java.util.List;

class StoredFileOverviewDataTable extends AbstractInMemoryDataTable<StoredFileOverviewRow> {

	private final List<StoredFileOverviewRow> rows;
	private final IDataTableColumn<StoredFileOverviewRow, Long> fileSizeColumn;
	private final IDataTableColumn<StoredFileOverviewRow, ItemId> fileIdColumn;

	public StoredFileOverviewDataTable(List<StoredFileOverviewRow> rows) {

		this.rows = rows;
		newColumn(String.class)//
			.setGetter(StoredFileOverviewRow::getFileName)
			.setTitle(EmfI18n.NAME)
			.addColumn();
		this.fileSizeColumn = newColumn(Long.class)//
			.setGetter(StoredFileOverviewRow::getFileSize)
			.setTitle(EmfI18n.SIZE)
			.addColumn();
		this.fileIdColumn = newColumn(ItemId.class)//
			.setGetter(StoredFileOverviewRow::getId)
			.setTitle(EmfI18n.ID)
			.addColumn();
		newColumn(String.class)//
			.setGetter(StoredFileOverviewRow::getFileHashString)
			.setTitle(EmfI18n.SHA_1_HASH)
			.addColumn();
	}

	@Override
	public DataTableIdentifier getIdentifier() {

		return new DataTableIdentifier("7114faf0-737f-4223-8498-dbc9f52d6d5a");
	}

	@Override
	protected Iterable<StoredFileOverviewRow> getTableRows() {

		return rows;
	}

	public IDataTableColumn<StoredFileOverviewRow, ItemId> getFileIdColumn() {

		return fileIdColumn;
	}

	public IDataTableColumn<StoredFileOverviewRow, Long> getFileSizeColumn() {

		return fileSizeColumn;
	}
}
