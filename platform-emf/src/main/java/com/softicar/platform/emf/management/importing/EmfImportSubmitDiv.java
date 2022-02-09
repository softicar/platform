package com.softicar.platform.emf.management.importing;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfImportSubmitDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;

	public EmfImportSubmitDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();

		appendChild(new DomActionBar(new ImportButton()));
		appendChild(new EmfDataTableDivBuilder<>(new EmfImportParsedRowsTable<>(engine)).build());
	}

	private class ImportButton extends DomButton {

		public ImportButton() {

			setIcon(EmfImages.ENTITY_IMPORT.getResource());
			setLabel(EmfI18n.IMPORT);
			setClickCallback(this::importRows);
		}

		private void importRows() {

			engine.insertRows();
			getDomDocument().getRefreshBus().setAllChanged();
			popup.hide();
		}
	}
}
