package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfViewAction<R extends IEmfTableRow<R, ?>> implements IEmfManagementAction<R> {

	private final IEmfTable<R, ?, ?> table;

	public EmfViewAction(IEmfTable<R, ?, ?> table) {

		this.table = table;
	}

	@Override
	public IResource getIcon() {

		return EmfImages.ENTITY_VIEW.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.VIEW;
	}

	@Override
	public IDisplayString getDescription() {

		return EmfI18n.SHOW_TABLE_ROW_DETAILS_AND_ACTIONS;
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfRole<R> getAuthorizedRole() {

		return table.getAuthorizer().getViewRole();
	}

	@Override
	public void handleClick(R tableRow) {

		DomPopupManager//
			.getInstance()
			.getPopup(tableRow, EmfFormPopup.class, it -> new EmfFormPopup<>(it))
			.open();
	}
}
