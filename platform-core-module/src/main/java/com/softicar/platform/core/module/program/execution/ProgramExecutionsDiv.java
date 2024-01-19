package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.IProgramExecutionsQuery.IRow;
import com.softicar.platform.core.module.program.execution.status.ProgramExecutionStatusDisplay;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.duration.EmfDurationDisplay;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.handler.AbstractEmfDataTableColumnHandler;

class ProgramExecutionsDiv extends DomDiv {

	private final IEmfDataTableDiv<IRow> table;

	public ProgramExecutionsDiv() {

		table = new EmfDataTableDivBuilder<>(IProgramExecutionsQuery.FACTORY.createQuery())//
			.setColumnHandler(IProgramExecutionsQuery.STATUS_COLUMN, new StatusColumnHandler())
			.setColumnHandler(IProgramExecutionsQuery.RUNTIME_COLUMN, new RuntimeColumnHandler())
			.setColumnHandler(IProgramExecutionsQuery.OUTPUT_COLUMN, new OutputColumnHandler())
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

	private static class StatusColumnHandler extends AbstractEmfDataTableColumnHandler<Long> {

		@Override
		public void buildCell(IEmfDataTableCell<?, Long> cell, Long value) {

			AGProgramExecution programExecution = AGProgramExecution.get(value.intValue());
			cell.appendChild(new ProgramExecutionStatusDisplay(programExecution.getStatus()));
		}
	}

	private static class RuntimeColumnHandler extends AbstractEmfDataTableColumnHandler<Long> {

		@Override
		public void buildCell(IEmfDataTableCell<?, Long> cell, Long value) {

			AGProgramExecution programExecution = AGProgramExecution.get(value.intValue());
			cell.appendChild(new EmfDurationDisplay(programExecution.getRuntime()));
		}
	}

	private static class OutputColumnHandler extends AbstractEmfDataTableColumnHandler<Long> {

		@Override
		public void buildCell(IEmfDataTableCell<?, Long> cell, Long value) {

			AGProgramExecution programExecution = AGProgramExecution.get(value.intValue());
			cell.appendChild(new ProgramExecutionOutputDisplay(programExecution));
		}
	}
}
