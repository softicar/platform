package com.softicar.platform.core.module.log.process;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.handler.AbstractEmfDataTableColumnHandler;

public class LogProcessOverview extends DomDiv {

	private final LogProcessViewPageDiv logViewPageDiv;
	private IEmfDataTableDiv<ILogProcessTableQuery.IRow> table;

	public LogProcessOverview(LogProcessViewPageDiv logViewPageDiv) {

		this.logViewPageDiv = logViewPageDiv;
	}

	public void refresh(AGLogProcess logProcess) {

		removeChildren();
		appendChild(new BackToOverviewButton());
		appendNewChild(DomElementTag.HR);
		appendNewChild(DomElementTag.H4).appendText(CoreI18n.LOG_DETAILS);
		table = new EmfDataTableDivBuilder<>(ILogProcessTableQuery.FACTORY.createQuery().setClassName(logProcess.getClassName()))
			.setColumnHandler(ILogProcessTableQuery.ACTION_COLUMN, new ActionColumnHandler())
			.buildAndAppendTo(this);
	}

	private class BackToOverviewButton extends DomButton {

		public BackToOverviewButton() {

			setLabel(CoreI18n.BACK_TO_OVERVIEW);
			setIcon(DomImages.PAGE_PREVIOUS.getResource());
			setClickCallback(() -> logViewPageDiv.showOverviewScope());
		}
	}

	private class ActionColumnHandler extends AbstractEmfDataTableColumnHandler<AGLogProcess> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGLogProcess> cell, AGLogProcess value) {

			cell.setStyle(CssTextAlign.CENTER);

			cell
				.appendChild(
					new DomButton()//
						.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION)
						.setIcon(DomImages.ENTRY_REMOVE.getResource())
						.setClickCallback(() -> {
							value.delete();
							table.refresh();
						})
						.setTitle(CoreI18n.CLICK_TO_DELETE_THIS_PROCESS));

			cell
				.appendChild(
					new DomButton()//
						.setClickCallback(() -> logViewPageDiv.showProcessDetailsScope(value))
						.setIcon(EmfImages.FIND.getResource())
						.setTitle(CoreI18n.CLICK_TO_SHOW_THE_PROCESS_LOG_MESSAGES));
		}
	}
}
