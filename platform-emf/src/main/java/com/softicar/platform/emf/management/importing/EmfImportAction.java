package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfImportAction<S> implements IEmfScopeAction<S> {

	private final IEmfTable<?, ?, S> entityTable;

	public EmfImportAction(IEmfTable<?, ?, S> entityTable) {

		this.entityTable = entityTable;
	}

	@Override
	public IEmfPredicate<S> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfPermission<S> getRequiredPermission() {

		return entityTable.getAuthorizer().getCreationPermission();
	}

	@Override
	public IResource getIcon() {

		return EmfImages.ENTITY_IMPORT.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.IMPORT;
	}

	@Override
	public void handleClick(S scope) {

		DomPopupManager//
			.getInstance()
			.getPopup(scope, EmfImportPopup.class, businessPartner -> new EmfImportPopup<>(entityTable, scope))
			.open();
	}
}
