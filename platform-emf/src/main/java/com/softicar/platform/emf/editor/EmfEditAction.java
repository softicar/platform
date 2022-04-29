package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfEditAction<R extends IEmfTableRow<R, ?>> implements IEmfCommonAction<R>, IEmfManagementAction<R> {

	private final IEmfTable<R, ?, ?> table;

	public EmfEditAction(IEmfTable<R, ?, ?> table) {

		this.table = table;
	}

	@Override
	public IResource getIcon() {

		return EmfImages.ENTITY_EDIT.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.EDIT;
	}

	@Override
	public IDisplayString getDescription() {

		return EmfI18n.ENABLES_THE_EDIT_MODE_FOR_THE_GIVEN_ENTRY;
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return table//
			.getEmfTableConfiguration()
			.getEditPredicate();
	}

	@Override
	public IEmfRole<R> getAuthorizedRole() {

		return table.getAuthorizer().getEditRole();
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		formBody.enterEditMode();
	}

	@Override
	public void handleClick(R tableRow) {

		DomPopupManager//
			.getInstance()
			.getPopup(tableRow, EmfFormPopup.class, EmfFormPopup::new)
			.setDirectEditing(true)
			.open();
	}
}
