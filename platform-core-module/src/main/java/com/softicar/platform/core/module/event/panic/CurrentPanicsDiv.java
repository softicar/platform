package com.softicar.platform.core.module.event.panic;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.event.panic.ICurrentPanicsQuery.IRow;
import com.softicar.platform.core.module.log.level.AGLogLevelEnum;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

public class CurrentPanicsDiv extends DomDiv {

	private final IEmfDataTableDiv<IRow> tableDiv;

	public CurrentPanicsDiv() {

		appendChild(new DomActionBar(new DropPanicsButton()));
		tableDiv = appendChild(
			new EmfDataTableDivBuilder<>(ICurrentPanicsQuery.FACTORY.createQuery().setLevel(AGLogLevelEnum.PANIC.getRecord()))
				.setColumnHandler(ICurrentPanicsQuery.LOG_TEXT_COLUMN, new LogTextColumnHandler())
				.build());
	}

	private class DropPanicsButton extends DomButton {

		public DropPanicsButton() {

			setIcon(DomImages.ENTRY_REMOVE.getResource());
			setLabel(CoreI18n.DROP_PANIC_ENTRIES);
			setClickCallback(this::dropPanics);
			setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION);
		}

		private void dropPanics() {

			AGLogMessage.TABLE
				.createUpdate()
				.set(AGLogMessage.NOTIFICATION_TIME, DayTime.now())
				.where(AGLogMessage.NOTIFICATION_TIME.isNull())
				.where(AGLogMessage.LEVEL.isEqual(AGLogLevelEnum.PANIC.getRecord()))
				.execute();
			tableDiv.refresh();
		}
	}

	private class LogTextColumnHandler extends EmfDataTableValueBasedColumnHandler<String> {

		@Override
		public void buildCell(IEmfDataTableCell<?, String> cell, String string) {

			cell.appendChild(new DomTextArea(string).setSize(4, 100));
		}
	}
}
