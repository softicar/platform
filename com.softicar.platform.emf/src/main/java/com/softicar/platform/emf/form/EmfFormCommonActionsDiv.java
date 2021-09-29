package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.marker.EmfCommonActionMarker;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * This UI element is a vertical list of buttons for common entity actions.
 * <p>
 * This element is used in {@link IEmfFormBody} to show all available actions of
 * type {@link IEmfCommonAction}.
 *
 * @author Oliver Richers
 */
class EmfFormCommonActionsDiv<R extends IEmfTableRow<R, ?>> extends DomActionBar {

	private final IEmfFormBody<R> formBody;
	private final R tableRow;

	public EmfFormCommonActionsDiv(IEmfFormBody<R> formBody) {

		this.formBody = formBody;
		this.tableRow = formBody.getTableRow();

		addCssClass(EmfCssClasses.EMF_FORM_COMMON_ACTIONS_DIV);
		setMarker(EmfFormMarker.COMMON_ACTIONS_DIV);
		integrateActions();
	}

	protected void refresh() {

		removeChildren();
		integrateActions();
	}

	private void integrateActions() {

		IBasicUser user = CurrentBasicUser.get();
		for (IEmfCommonAction<R> action: tableRow.table().getCommonActions(tableRow)) {
			if (action.isAvailable(tableRow, user)) {
				prependActionButton(action);
			}
		}
	}

	private void prependActionButton(IEmfCommonAction<R> action) {

		prependChild(
			new DomButton()//
				.setClickCallback(() -> action.handleClick(formBody))
				.setIcon(action.getIcon())
				.setTitle(action.getTitle().concat("..."))
				.setMarker(new EmfCommonActionMarker(action)));
	}
}
