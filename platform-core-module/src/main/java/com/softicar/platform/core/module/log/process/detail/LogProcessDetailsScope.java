package com.softicar.platform.core.module.log.process.detail;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.message.ILogMessageTableQuery;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.core.module.log.process.LogProcessViewPageDiv;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableWrappedTextColumnHandler;

public class LogProcessDetailsScope extends DomDiv {

	private final LogProcessViewPageDiv logViewPageDiv;

	public LogProcessDetailsScope(LogProcessViewPageDiv logViewPageDiv) {

		this.logViewPageDiv = logViewPageDiv;
	}

	public void refresh(AGLogProcess logProcess) {

		m_logProcess = logProcess;
		removeChildren();
		appendChild(new BackToOverviewButton());
		appendNewChild(DomElementTag.BR);
		appendChild(new BackToProcessOverviewButton());
		appendNewChild(DomElementTag.HR);
		appendNewChild(DomElementTag.H4).appendText(CoreI18n.LOG_MESSAGES);
		new EmfDataTableDivBuilder<>(ILogMessageTableQuery.FACTORY.createQuery().setLogProcess(logProcess))
			.setColumnHandler(ILogMessageTableQuery.LOG_TEXT_COLUMN, new EmfDataTableWrappedTextColumnHandler("\n"))
			.buildAndAppendTo(this);
	}

	private class BackToOverviewButton extends DomButton {

		public BackToOverviewButton() {

			setLabel(CoreI18n.BACK_TO_OVERVIEW);
			setIcon(DomElementsImages.PAGE_PREVIOUS.getResource());
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			logViewPageDiv.showOverviewScope();
		}
	}

	private class BackToProcessOverviewButton extends DomButton {

		public BackToProcessOverviewButton() {

			setLabel(CoreI18n.BACK_TO_ARG1_PROCESSES.toDisplay(m_logProcess.getClassName()));
			setIcon(DomElementsImages.PAGE_PREVIOUS.getResource());
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			logViewPageDiv.showClassDetailsScope();
		}
	}

	private AGLogProcess m_logProcess;
}
