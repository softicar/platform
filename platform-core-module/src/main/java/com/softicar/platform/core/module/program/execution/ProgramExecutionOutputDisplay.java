package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;

public class ProgramExecutionOutputDisplay extends DomDiv {

	public ProgramExecutionOutputDisplay(AGProgramExecution programExecution) {

		appendChild(new OutputButton(programExecution));
	}

	private class OutputButton extends DomButton {

		private final AGProgramExecution programExecution;

		public OutputButton(AGProgramExecution programExecution) {

			this.programExecution = programExecution;
			setLabel(CoreI18n.SHOW_OUTPUT);
			setClickCallback(this::showOutputPopup);
		}

		private void showOutputPopup() {

			DomPopupManager//
				.getInstance()
				.getPopup(programExecution, OutputPopup.class, OutputPopup::new)
				.open();
		}
	}

	private class OutputPopup extends DomPopup {

		private final AGProgramExecution programExecution;
		private final ProgramExecutionOutputArea outputElement;

		public OutputPopup(AGProgramExecution programExecution) {

			this.programExecution = programExecution;
			this.outputElement = new ProgramExecutionOutputArea();
			setCaption(CoreI18n.OUTPUT);
			setSubCaption(createSubCaption(programExecution));

			DomButton refreshButton = new DomButton()//
				.setLabel(CoreI18n.REFRESH)
				.setIcon(CoreImages.REFRESH.getResource())
				.setClickCallback(this::refresh);

			appendChild(new DomActionBar()).appendChild(refreshButton);
			appendNewChild(DomElementTag.HR);
			appendChild(this.outputElement);
			appendCloseButton();

			addDeferredInitializer(this::refresh);
		}

		private void refresh() {

			outputElement.setValue(getOutput(programExecution));
			outputElement.scrollToBottom();
		}

		private String getOutput(AGProgramExecution programExecution) {

			return ProgramExecutionRunnableRegistry//
				.getInstance()
				.getOutput(programExecution)
				.orElse(reloadOutput(programExecution));
		}

		private String reloadOutput(AGProgramExecution programExecution) {

			programExecution.reload();
			return programExecution.getOutput();
		}

		private IDisplayString createSubCaption(AGProgramExecution programExecution) {

			return IDisplayString
				.create(
					"%s - %s %s - %s: %s"
						.formatted(
							programExecution.getProgram().toDisplayWithoutId(),
							CoreI18n.EXECUTION,
							programExecution.getId(),
							CoreI18n.STARTED_AT,
							programExecution.getStartedAt()));
		}
	}

	private class ProgramExecutionOutputArea extends DomTextArea {

		public ProgramExecutionOutputArea() {

			setReadonly(true);
			setAttribute("wrap", "off");
			addCssClass(CoreCssClasses.PROGRAM_EXECUTION_OUTPUT_AREA);
		}
	}
}
