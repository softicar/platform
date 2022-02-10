package com.softicar.platform.emf.management.importing.analyze;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.management.importing.EmfImportPopup;
import com.softicar.platform.emf.management.importing.engine.EmfImportEngine;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;

public class EmfImportAnalyseDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;

	public EmfImportAnalyseDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();

		appendChild(new DomActionBar(new BackButton(), new AnalyzeButton()));
		appendChild(
			new EmfDataTableDivBuilder<>(new ParseTable())//
				.build());
	}

	private class BackButton extends DomButton {

		public BackButton() {

			setIcon(EmfImages.WIZARD_PREVIOUS.getResource());
			setLabel(EmfI18n.BACK);
			setClickCallback(this::goBack);
		}

		private void goBack() {

			engine.clear();
			popup.showUploadDiv();
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

	private class ParseTable extends AbstractInMemoryDataTable<List<String>> {

		public ParseTable() {

			var index = 0;
			for (IDbField<R, ?> field: engine.getFieldsToImport()) {
				addColumn(field, index);
				index++;
			}
		}

		private void addColumn(IDbField<R, ?> field, int index) {

			newColumn(String.class)//
				.setGetter(row -> row.get(index))
				.setTitle(engine.getFieldTitle(field))
				.addColumn();
		}

		@Override
		protected Collection<List<String>> getTableRows() {

			return engine.getTextualRows();
		}
	}
}
