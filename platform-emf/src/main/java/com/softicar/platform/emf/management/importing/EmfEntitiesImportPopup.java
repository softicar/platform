package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.string.csv.CsvTokenizer;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.token.parser.EmfTokenMatrixParser;
import java.util.List;

public class EmfEntitiesImportPopup<R extends IEmfTableRow<R, P>, P, S> extends DomPopup {

	private final IEmfTable<R, P, S> entityTable;
	private final S scopeEntity;

	public EmfEntitiesImportPopup(IEmfTable<R, P, S> entityTable, S scopeEntity) {

		this.entityTable = entityTable;
		this.scopeEntity = scopeEntity;

		setCaption();
		setSubCaption();

		appendChild(new EmfEntitiesUploadForm(this::importFiles));
	}

	private void setCaption() {

		setCaption(EmfI18n.IMPORT.concat(": ").concat(entityTable.getPluralTitle()));
	}

	private void setSubCaption() {

		CastUtils//
			.tryCast(scopeEntity, IDisplayable.class)
			.ifPresent(s -> setSubCaption(s.toDisplay()));
	}

	private void importFiles(Iterable<IDomFileUpload> fileUploads) {

		fileUploads.forEach(this::importFile);
	}

	private void importFile(IDomFileUpload fileUpload) {

		String content = fileUpload.getContentAsString(Charsets.UTF8);
		List<List<String>> tokenMatrix = new CsvTokenizer().tokenize(content);
		List<R> rows = new EmfTokenMatrixParser<>(entityTable).parse(tokenMatrix);
		executeAlert(IDisplayString.format("Got %s rows!", rows.size()));
	}
}
