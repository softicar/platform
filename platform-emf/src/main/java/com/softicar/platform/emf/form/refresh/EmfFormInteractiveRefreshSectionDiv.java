package com.softicar.platform.emf.form.refresh;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;
import com.softicar.platform.emf.form.section.header.IEmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormInteractiveRefreshSectionDiv<F extends IEmfTableRow<F, ?>> extends EmfFormSectionDiv<F> {

	public EmfFormInteractiveRefreshSectionDiv(IEmfFormBody<F> formBody) {

		super(formBody, createSectionHeader());
		addElement(() -> new EmfFormInteractiveRefreshSpan<>(formBody, false, INullaryVoidFunction.NO_OPERATION));
		setOpen(true);
	}

	private static IEmfFormSectionHeader createSectionHeader() {

		return new EmfFormSectionHeader()//
			.setIcon(EmfImages.ENTITY_OUTDATED.getResource())
			.setCaption(EmfI18n.OUTDATED_ENTRY);
	}
}
