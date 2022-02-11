package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.importing.analyze.EmfImportAnalyzeDiv;
import com.softicar.platform.emf.management.importing.engine.EmfImportEngine;
import com.softicar.platform.emf.management.importing.submit.EmfImportSubmitDiv;
import com.softicar.platform.emf.management.importing.upload.EmfImportUploadDiv;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfImportPopup<R extends IEmfTableRow<R, P>, P, S> extends DomPopup {

	private final IEmfTable<R, P, S> table;
	private final S scope;
	private final EmfImportEngine<R, P, S> engine;

	public EmfImportPopup(IEmfTable<R, P, S> table, S scope) {

		this.table = table;
		this.scope = scope;
		this.engine = new EmfImportEngine<>(table).setScope(scope);

		setCaption();
		setSubCaption();

		showUploadDiv();
	}

	public EmfImportEngine<R, P, S> getEngine() {

		return engine;
	}

	public void showUploadDiv() {

		removeChildren();
		appendChild(new EmfImportUploadDiv<>(this));
	}

	public void showAnalyzeDiv() {

		removeChildren();
		appendChild(new EmfImportAnalyzeDiv<>(this));
	}

	public void showSubmitDiv() {

		removeChildren();
		appendChild(new EmfImportSubmitDiv<>(this));
	}

	private void setCaption() {

		setCaption(EmfI18n.IMPORT.concat(": ").concat(table.getPluralTitle()));
	}

	private void setSubCaption() {

		CastUtils//
			.tryCast(scope, IDisplayable.class)
			.ifPresent(scope -> setSubCaption(scope.toDisplay()));
	}
}
