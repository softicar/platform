package com.softicar.platform.emf.management.importing.variable.replace;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.management.importing.EmfImportBackButton;
import com.softicar.platform.emf.management.importing.EmfImportPopup;
import com.softicar.platform.emf.management.importing.engine.EmfImportCsvFileTable;
import com.softicar.platform.emf.management.importing.engine.EmfImportEngine;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfImportViewDataWithReplacementsDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;

	public EmfImportViewDataWithReplacementsDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();

		appendChild(
			new DomActionBar(//
				new EmfImportBackButton(this::goBack),
				new AnalyzeButton()));
		appendChild(
			new EmfDataTableDivBuilder<>(new EmfImportCsvFileTable<>(engine, engine.getTextualRowsWithReplacements()))//
				.build());
	}

	private void goBack() {

		if (engine.containsVariables()) {
			popup.showVariablesInputDiv();
		} else {
			popup.showUploadedDataDiv();
		}
	}

	private class AnalyzeButton extends DomButton {

		public AnalyzeButton() {

			setIcon(EmfImages.WIZARD_NEXT.getResource());
			setLabel(EmfI18n.ANALYZE);
			setClickCallback(this::analyzeRows);
		}

		private void analyzeRows() {

			engine.parseRows();
			popup.showSubmitDiv();
		}
	}
}
