package com.softicar.platform.emf.management.importing.variable.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.management.importing.EmfImportBackButton;
import com.softicar.platform.emf.management.importing.EmfImportPopup;
import com.softicar.platform.emf.management.importing.engine.EmfImportEngine;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EmfImportVariablesInputDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;
	private final Map<String, DomTextInput> variableInputMap = new TreeMap<>();

	public EmfImportVariablesInputDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();

		appendChild(
			new DomActionBar(//
				new EmfImportBackButton(() -> popup.showUploadedDataDiv()),
				new ReplaceVariablesButton()));
		appendChild(
			new DomWikiDivBuilder()//
				.beginBox(WikiBoxType.INFO)
				.addUnorderedListItem(EmfI18n.ENTER_THE_VARIABLE_VALUES_FOR_THE_CURRENT_IMPORT)
				.addUnorderedListItem(
					EmfI18n.IN_THE_NEXT_STEP_YOU_CAN_CONTROL_THE_REPLACEMENT_OF_THE_VARIABLES_BY_THE_ENTERED_VARIABLE_VALUES
						.concatSentence(EmfI18n.NO_DATA_WILL_BE_SAVED_YET))
				.endBox(WikiBoxType.INFO)
				.build());
		addVariableInputs(appendChild(new DomLabelGrid()));
	}

	private void addVariableInputs(DomLabelGrid grid) {

		for (String variable: engine.getVariables()) {
			var input = new DomTextInput();
			grid.add(IDisplayString.create(variable), input);
			variableInputMap.put(variable, input);
		}
	}

	private Map<String, String> createVariableValueMap() {

		Map<String, String> variableValueMap = new HashMap<>();
		for (Map.Entry<String, DomTextInput> entry: variableInputMap.entrySet()) {
			variableValueMap.put(entry.getKey(), entry.getValue().getInputText());
		}
		return variableValueMap;
	}

	private class ReplaceVariablesButton extends DomButton {

		public ReplaceVariablesButton() {

			setIcon(EmfImages.WIZARD_NEXT.getResource());
			setLabel(EmfI18n.REPLACE_VARIABLES);
			setClickCallback(this::analyzeRows);
		}

		private void analyzeRows() {

			engine.replaceVariables(createVariableValueMap());
			popup.showDataWithReplacementsDiv();
		}
	}
}
