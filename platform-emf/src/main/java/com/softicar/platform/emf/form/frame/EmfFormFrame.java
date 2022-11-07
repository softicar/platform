package com.softicar.platform.emf.form.frame;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * An element to view and edit a single entity.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfFormFrame<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfFormFrame<R> {

	private final EmfFormFrameHeader header;
	private final IEmfForm<R> form;

	public EmfFormFrame(R tableRow) {

		this.header = new EmfFormFrameHeader();
		this.form = createForm(tableRow);
		setCssClass(EmfCssClasses.EMF_FORM_FRAME);
		appendChild(header);
		appendChild(form);
	}

	@Override
	public void setTitle(IDisplayString title, IDisplayString subTitle) {

		this.header.setCaption(title, subTitle);
	}

	@Override
	public void closeFrame() {

		// nothing to do
	}

	@Override
	public void focusFrame() {

		// nothing to do
	}

	private IEmfForm<R> createForm(R tableRow) {

		return tableRow//
			.table()
			.getFormFactory()
			.createForm(this, tableRow)
			.peekAndRefresh();
	}
}
