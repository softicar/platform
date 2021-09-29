package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.attribute.field.string.EmfMultilineStringDisplay;

public class ProgramExecutionOutputDisplay extends DomDiv {

	public ProgramExecutionOutputDisplay(AGProgramExecution programExecution) {

		if (programExecution.isTerminated()) {
			appendChild(new OutputButton(programExecution.getOutput()));
		} else {
			appendText(CoreI18n.NOT_TERMINATED_YET.encloseInParentheses());
		}
	}

	private class OutputButton extends DomButton {

		private final String output;

		public OutputButton(String output) {

			this.output = output;
			setLabel(CoreI18n.SHOW_OUTPUT);
			setClickCallback(this::showOutputPopup);
		}

		private void showOutputPopup() {

			DomPopupManager//
				.getInstance()
				.getPopup(output, OutputPopup.class, OutputPopup::new)
				.show();
		}
	}

	private class OutputPopup extends DomPopup {

		public OutputPopup(String output) {

			setCaption(CoreI18n.OUTPUT);
			appendChild(new EmfMultilineStringDisplay(output));
			appendCloseButton();
		}
	}
}
