package com.softicar.platform.emf.form;

import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfFormSaveOrCancelActions<R extends IEmfTableRow<R, ?>> extends EmfFormSectionDiv<R> {

	public EmfFormSaveOrCancelActions(IEmfFormBody<R> formBody, IEmfAttributesDiv<R> attributesDiv) {

		super(formBody, new EmfFormSectionHeader().setIcon(EmfImages.ENTITY_ACTIONS.getResource()).setCaption(EmfI18n.ACTIONS));

		addElement(() -> new EmfFormSaveOrCancelActionsInput<>(formBody, attributesDiv));
		setOpen(true);
	}
}
