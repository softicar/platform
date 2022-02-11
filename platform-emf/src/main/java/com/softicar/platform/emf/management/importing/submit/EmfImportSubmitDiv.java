package com.softicar.platform.emf.management.importing.submit;

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

public class EmfImportSubmitDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;

	public EmfImportSubmitDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();

		appendChild(new DomActionBar(new BackButton(), new SaveButton()));
		appendChild(new EmfDataTableDivBuilder<>(new RowsTable()).build());
	}

	private class BackButton extends DomButton {

		public BackButton() {

			setIcon(EmfImages.WIZARD_PREVIOUS.getResource());
			setLabel(EmfI18n.BACK);
			setClickCallback(() -> popup.showAnalyzeDiv());
		}
	}

	private class SaveButton extends DomButton {

		public SaveButton() {

			setIcon(EmfImages.WIZARD_NEXT.getResource());
			setLabel(EmfI18n.SAVE_AND_CLOSE);
			setClickCallback(this::saveRows);
		}

		private void saveRows() {

			engine.insertRows();
			getDomDocument().getRefreshBus().setAllChanged();
			popup.hide();
		}
	}

	private class RowsTable extends AbstractInMemoryDataTable<R> {

		public RowsTable() {

			for (IDbField<R, ?> field: engine.getTable().getAllFields()) {
				addFieldColumn(field);
			}
		}

		@Override
		protected Collection<R> getTableRows() {

			return engine.getParsedRows();
		}

		private <V> void addFieldColumn(IDbField<R, V> field) {

			newColumn(field.getValueType().getValueClass())//
				.setGetter(field::getValue)
				.setTitle(engine.getFieldTitle(field))
				.addColumn();
		}
	}
}
