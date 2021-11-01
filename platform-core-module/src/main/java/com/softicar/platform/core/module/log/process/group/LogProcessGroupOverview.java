package com.softicar.platform.core.module.log.process.group;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.core.module.log.process.LogProcessViewPageDiv;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.AbstractEmfDataTableColumnHandler;

public class LogProcessGroupOverview extends DomDiv {

	private final LogProcessViewPageDiv logViewPageDiv;

	public LogProcessGroupOverview(LogProcessViewPageDiv logViewPageDiv) {

		this.logViewPageDiv = logViewPageDiv;
		new EmfDataTableDivBuilder<>(ILogProcessGroupTableQuery.FACTORY.createQuery())
			.setColumnHandler(ILogProcessGroupTableQuery.ACTION_COLUMN, new ActionColumnHandler())
			.buildAndAppendTo(this);
	}

	private class ActionColumnHandler extends AbstractEmfDataTableColumnHandler<AGLogProcess> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGLogProcess> cell, AGLogProcess value) {

			// TODO extract style to CSS
			cell.setStyle(CssTextAlign.CENTER);
			cell
				.appendChild(
					new DomButton()//
						.setClickCallback(() -> logViewPageDiv.showClassDetailsScopeWithRefresh(value))
						.setIcon(EmfImages.FIND.getResource())
						.setTitle(CoreI18n.CLICK_TO_SHOW_THE_PROGRAM_LOGS));

		}
	}
}
