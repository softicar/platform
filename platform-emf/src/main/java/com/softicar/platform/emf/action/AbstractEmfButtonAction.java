package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.action.marker.EmfActionMarker;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Supplier;

/**
 * Simple base class for actions that are triggered by a button.
 *
 * @author Oliver Richers
 */
public abstract class AbstractEmfButtonAction<R extends IEmfTableRow<R, ?>> implements IEmfPrimaryAction<R>, IEmfCommonAction<R> {

	@Override
	public final boolean isAvailable(R tableRow, IBasicUser user) {

		return IEmfPrimaryAction.super.isAvailable(tableRow, user);
	}

	@Override
	public void integrate(R tableRow, IEmfFormSectionDiv<R> actionContainer) {

		actionContainer.addElement(() -> createButton(actionContainer.getFormBody()));
	}

	private DomButton createButton(IEmfFormBody<R> formBody) {

		DomButton button = new DomButton()//
			.setIcon(getIcon())
			.setLabel(getTitle())
			.setClickCallback(() -> handleClick(formBody))
			.addMarker(new EmfActionMarker(this));
		getConfirmationMessageSupplier(formBody.getTableRow()).map(Supplier::get).ifPresent(button::setConfirmationMessage);
		return button;
	}
}
