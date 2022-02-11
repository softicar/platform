package com.softicar.platform.emf.management.importing.upload;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.management.importing.EmfImportPopup;
import com.softicar.platform.emf.management.importing.engine.EmfImportEngine;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EmfImportUploadDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final EmfImportPopup<R, P, S> popup;
	private final EmfImportEngine<R, P, S> engine;

	public EmfImportUploadDiv(EmfImportPopup<R, P, S> popup) {

		this.popup = popup;
		this.engine = popup.getEngine();

		appendChild(
			new DomWikiDivBuilder()//
				.beginBox(WikiBoxType.INFO)
				.addUnorderedListItem(EmfI18n.SELECT_A_CSV_FILE_TO_IMPORT)
				.addUnorderedListItem(EmfI18n.THE_TABLE_SHOWS_THE_COLUMNS_TO_BE_INCLUDED)
				.addUnorderedListItem(EmfI18n.COLUMN_VALUES_MUST_BE_SEPARATED_BY_COMMAS)
				.addUnorderedListItem(EmfI18n.INDIVIDUAL_COLUMN_VALUES_CAN_BE_ENCLOSED_BY_QUOTATION_MARKS)
				.addUnorderedListItem(
					EmfI18n.IN_THE_NEXT_STEP_THE_CONTENT_OF_THE_FILE_WILL_BE_DISPLAYED//
						.concatSentence(EmfI18n.NO_DATA_WILL_BE_SAVED_YET))
				.endBox(WikiBoxType.INFO)
				.build());

		appendChild(
			new EmfDataTableDivBuilder<>(new UploadTable())//
				.setEmptyTablePlaceholderFactory(UploadTriggerDiv::new)
				.build());
	}

	private class UploadTriggerDiv extends DomDiv {

		public UploadTriggerDiv() {

			var uploadButton = new EmfImportUploadButton();
			var uploadForm = new EmfImportUploadForm(this::readCsvUploads).setupEventDelegation(uploadButton);

			appendChild(uploadForm);
			appendChild(uploadButton);
		}

		private void readCsvUploads(Iterable<IDomFileUpload> uploads) {

			uploads.forEach(upload -> engine.addCsvRows(upload.getContentAsString(Charsets.UTF8)));

			if (engine.getTextualRows().isEmpty()) {
				throw new SofticarUserException(EmfI18n.NO_DATA_UPLOADED);
			} else {
				popup.showParseDiv();
			}
		}
	}

	private class UploadTable extends AbstractInMemoryDataTable<List<String>> {

		public UploadTable() {

			engine.getFieldsToImport().forEach(this::addColumn);
		}

		private void addColumn(IDbField<R, ?> field) {

			newColumn(String.class)//
				.setGetter(row -> "")
				.setTitle(engine.getFieldTitle(field))
				.addColumn();
		}

		@Override
		protected Collection<List<String>> getTableRows() {

			return Collections.emptyList();
		}
	}
}
