package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.string.csv.CsvTokenizer;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.EmfTokenMatrixParser;
import java.util.List;

public class EmfImportPopup<R extends IEmfTableRow<R, P>, P, S> extends DomPopup {

	private final IEmfTable<R, P, S> table;
	private final S scope;
	private final EmfImportEngine<R, P, S> engine;
	private final EmfImportRowsPreviewTable<R, P, S> previewDataTable;
	private final IEmfDataTableDiv<R> previewTableDiv;

	public EmfImportPopup(IEmfTable<R, P, S> table, S scope) {

		this.table = table;
		this.scope = scope;
		this.engine = new EmfImportEngine<>(table).setScope(scope);
		this.previewDataTable = new EmfImportRowsPreviewTable<>(engine);
		this.previewTableDiv = new EmfDataTableDivBuilder<>(previewDataTable).build();

		setCaption();
		setSubCaption();

		var uploadButton = new EmfImportDataUploadButton();
		var uploadForm = new EmfImportDataUploadForm(EmfImportPopup.this::parseFiles).setupEventDelegation(uploadButton);

		appendChild(uploadForm);
		appendChild(new DomActionBar(uploadButton, new ImportButton()));
		appendChild(previewTableDiv);
	}

	private void setCaption() {

		setCaption(EmfI18n.IMPORT.concat(": ").concat(table.getPluralTitle()));
	}

	private void setSubCaption() {

		CastUtils//
			.tryCast(scope, IDisplayable.class)
			.ifPresent(s -> setSubCaption(s.toDisplay()));
	}

	private void parseFiles(Iterable<IDomFileUpload> fileUploads) {

		fileUploads.forEach(this::parseFile);
		previewTableDiv.refresh();
	}

	private void parseFile(IDomFileUpload fileUpload) {

		String content = fileUpload.getContentAsString(Charsets.UTF8);
		List<List<String>> tokenMatrix = new CsvTokenizer().tokenize(content);
		List<R> rows = new EmfTokenMatrixParser<>(table).parse(tokenMatrix);
		previewDataTable.addRows(rows);
	}

	private class ImportButton extends DomButton {

		public ImportButton() {

			setIcon(EmfImages.ENTITY_IMPORT.getResource());
			setLabel(EmfI18n.IMPORT);
			setClickCallback(this::importRows);
		}

		private void importRows() {

			if (previewDataTable.getTableRows().isEmpty()) {
				throw new SofticarUserException(EmfI18n.NOTHING_TO_IMPORT);
			} else {
				new EmfImportRowsInserter<>(table).insertAll(previewDataTable.getTableRows());
				getDomDocument().getRefreshBus().setAllChanged();
				hide();
			}
		}
	}
}
