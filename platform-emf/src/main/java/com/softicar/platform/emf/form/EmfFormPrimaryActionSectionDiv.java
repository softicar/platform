package com.softicar.platform.emf.form;

import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;
import com.softicar.platform.emf.form.section.header.IEmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.List;

public class EmfFormPrimaryActionSectionDiv<R extends IEmfTableRow<R, ?>> extends EmfFormSectionDiv<R> {

	private static final IEmfFormSectionHeader SECTION_HEADER = new EmfFormSectionHeader()//
		.setIcon(EmfImages.ENTITY_ACTIONS.getResource())
		.setCaption(EmfI18n.ACTIONS);

	public EmfFormPrimaryActionSectionDiv(IEmfFormBody<R> formBody, List<IEmfPrimaryAction<R>> availableActions) {

		super(formBody, SECTION_HEADER);
		setCssClass(EmfCssClasses.EMF_FORM_PRIMARY_ACTIONS_SECTION_DIV);
		availableActions.forEach(it -> it.integrate(formBody.getTableRow(), this));
	}
}
