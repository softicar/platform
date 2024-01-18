package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.IProgramExecutionsQuery.IRow;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;

class ProgramExecutionsDiv extends DomDiv {

	private final IEmfDataTableDiv<IRow> table;

	public ProgramExecutionsDiv() {

		table = new EmfDataTableDivBuilder<>(IProgramExecutionsQuery.FACTORY.createQuery())//
//			.setActionColumnHandler(this::buildActionCell)
//			.setColumnHandler(IProgramExecutionsQuery.FILE_COLUMN, new StoredFileColumnHandler())
//			.setColumnHandler(IProgramExecutionsQuery.JSON_COLUMN, new JsonColumnHandler())
			.build();

		appendChild(new DomActionBar(new RefreshButton()));
		appendChild(table);
	}

	private class RefreshButton extends DomButton {

		public RefreshButton() {

			setLabel(CoreI18n.REFRESH);
			setIcon(EmfImages.REFRESH.getResource());
			setClickCallback(table::refresh);
		}
	}

//	private static class JsonColumnHandler extends EmfDataTableRowBasedColumnHandler<IParashiftDocumentsQuery.IRow, String> {
//
//		@Override
//		public void buildCell(IEmfDataTableCell<IParashiftDocumentsQuery.IRow, String> cell, IParashiftDocumentsQuery.IRow row) {
//
//			DomActionBar actionBar = new DomActionBar();
//			actionBar.appendChild(new JsonDisplayPopupButtonDiv(row.getJson()));
//			cell.appendChild(actionBar);
//		}
//
//		@Override
//		public boolean isSortable(IEmfDataTableColumn<?, String> column) {
//
//			return false;
//		}
//	}
}
