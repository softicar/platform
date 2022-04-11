package com.softicar.platform.emf.management.importing.upload;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.management.importing.EmfImportBackButton;
import com.softicar.platform.emf.management.importing.EmfImportPopup;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumn;
import com.softicar.platform.emf.management.importing.engine.EmfImportEngine;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;

public class EmfImportViewUploadedDataDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;

	public EmfImportViewUploadedDataDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();

		appendChild(
			new DomActionBar(//
				new EmfImportBackButton(() -> popup.showUploadDiv()),
				new GoOnButton()));
		appendChild(
			new EmfDataTableDivBuilder<>(new UploadedDataTable())//
				.build());
	}

	private class GoOnButton extends DomButton {

		public GoOnButton() {

			setIcon(EmfImages.WIZARD_NEXT.getResource());
			if (engine.containsVariables()) {
				setLabel(EmfI18n.ENTER_VARIABLE_VALUES);
			} else {
				setLabel(EmfI18n.ANALYZE);
			}
			setClickCallback(this::goOn);
		}

		private void goOn() {

			if (engine.containsVariables()) {
				popup.showVariablesInputDiv();
			} else {
				engine.parseRows();
				popup.showSubmitDiv();
			}
		}
	}

	private class UploadedDataTable extends AbstractInMemoryDataTable<List<String>> {

		public UploadedDataTable() {

			var index = 0;
			for (EmfImportColumn<R, ?> column: engine.getCvsFileColumnsToImport()) {
				addColumn(column, index);
				index++;
			}
		}

		@Override
		protected Collection<List<String>> getTableRows() {

			return engine.getTextualRows();
		}

		private void addColumn(EmfImportColumn<R, ?> column, int index) {

			newColumn(String.class)//
				.setGetter(row -> row.get(index))
				.setTitle(column.getTitle())
				.addColumn();
		}
	}
}
