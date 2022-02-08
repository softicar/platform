package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfEntitiesImportPopup<R extends IEmfTableRow<R, P>, P, S> extends DomPopup {

	private final IEmfTable<R, P, S> entityTable;
	private final S scopeEntity;

	public EmfEntitiesImportPopup(IEmfTable<R, P, S> entityTable, S scopeEntity) {

		this.entityTable = entityTable;
		this.scopeEntity = scopeEntity;

		setCaption();
		setSubCaption();
	}

	private void setCaption() {

		setCaption(entityTable.getTitle());
	}

	private void setSubCaption() {

		CastUtils//
			.tryCast(scopeEntity, IDisplayable.class)
			.ifPresent(s -> setSubCaption(s.toDisplay()));
	}
}
