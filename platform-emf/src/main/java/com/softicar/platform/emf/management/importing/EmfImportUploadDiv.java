package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.List;

public class EmfImportUploadDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;
	private final IEmfDataTableDiv<List<String>> textualRowsTableDiv;

	public EmfImportUploadDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();
		this.textualRowsTableDiv = new EmfDataTableDivBuilder<>(new EmfImportTextualRowsTable<>(engine)).build();

		var uploadButton = new EmfImportUploadButton();
		var uploadForm = new EmfImportUploadForm(this::readCsvUploads).setupEventDelegation(uploadButton);

		appendChild(uploadForm);
		appendChild(new DomActionBar(uploadButton, new ParseButton()));
		appendChild(textualRowsTableDiv);
	}

	private void readCsvUploads(Iterable<IDomFileUpload> uploads) {

		uploads.forEach(upload -> engine.addCsvRows(upload.getContentAsString(Charsets.UTF8)));
		textualRowsTableDiv.refresh();
	}

	private class ParseButton extends DomButton {

		public ParseButton() {

			setIcon(EmfImages.ENTITY_VIEW.getResource());
			setLabel(EmfI18n.VALIDATE);
			setClickCallback(this::parseRows);
		}

		private void parseRows() {

			engine.parseRows();
			popup.showSubmitDiv();
		}
	}
}
