package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.IProgramExecutionsQuery.IRow;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

class ProgramExecutionsDiv extends DomDiv {

	private final IEmfDataTableDiv<IRow> table;

	public ProgramExecutionsDiv() {

		table = new EmfDataTableDivBuilder<>(IProgramExecutionsQuery.FACTORY.createQuery())//
			.setColumnHandler(IProgramExecutionsQuery.STATUS_COLUMN, new StatusColumnHandler())
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

	private static class StatusColumnHandler extends EmfDataTableValueBasedColumnHandler<Long> {

		@Override
		public void buildCell(IEmfDataTableCell<?, Long> cell, Long value) {

			AGProgramExecution programExecution = AGProgramExecution.get(value.intValue());
			cell.appendText(programExecution.getStatus().toDisplay());
		}

		@Override
		public boolean isSortable(IEmfDataTableColumn<?, Long> column) {

			return super.isSortable(column);
		}
	}
}
