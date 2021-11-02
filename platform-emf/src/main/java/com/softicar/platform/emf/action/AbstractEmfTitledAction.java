package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.action.marker.EmfActionMarker;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfTitledAction<R extends IEmfTableRow<R, ?>> implements IEmfPrimaryAction<R> {

	protected abstract IDomElement createNewElement(IEmfFormBody<R> formBody);

	@Override
	public final boolean isAvailable(R tableRow, IBasicUser user) {

		return IEmfPrimaryAction.super.isAvailable(tableRow, user);
	}

	@Override
	public void integrate(R tableRow, IEmfFormSectionDiv<R> actionContainer) {

		actionContainer
			.addElement(
				() -> new DomButton()//
					.setIcon(getIcon())
					.setLabel(getTitle().concat("..."))
					.setClickCallback(() -> showPrompt(actionContainer.getFormBody()))
					.setMarker(new EmfActionMarker(this)));
	}

	protected void showPrompt(IEmfFormBody<R> formBody) {

		EmfFormSectionHeader sectionHeader = new EmfFormSectionHeader().setIcon(getIcon()).setCaption(getTitle());
		EmfFormSectionDiv<R> actionSection = new EmfFormSectionDiv<>(formBody, sectionHeader);
		actionSection.addElement(() -> createNewElement(formBody));
		actionSection.setOpen(true);
		formBody.showSectionContainer(actionSection);
	}
}
