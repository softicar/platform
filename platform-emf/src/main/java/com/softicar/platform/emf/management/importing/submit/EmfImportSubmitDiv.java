package com.softicar.platform.emf.management.importing.submit;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.management.importing.EmfImportBackButton;
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

		appendChild(
			new DomActionBar(//
				new EmfImportBackButton(this::goBack),
				new SaveButton()));
		appendChild(new EmfDataTableDivBuilder<>(new RowsTable()).build());
	}

	private void goBack() {

		if (engine.containsVariables()) {
			popup.showDataWithReplacementsDiv();
		} else {
			popup.showUploadedDataDiv();
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
			popup.close();
		}
	}

	private class RowsTable extends AbstractInMemoryDataTable<R> {

		public RowsTable() {

			for (IDbField<R, ?> field: engine.getTable().getAllFields()) {
				addFieldColumn(field);
			}
		}

		@Override
		public DataTableIdentifier getIdentifier() {

			return DataTableIdentifier.empty();
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
