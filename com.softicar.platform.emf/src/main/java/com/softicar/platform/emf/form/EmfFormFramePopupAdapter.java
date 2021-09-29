package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormFramePopupAdapter<R extends IEmfTableRow<R, ?>> implements IEmfFormFrame<R> {

	private final DomPopup popup;

	public EmfFormFramePopupAdapter(DomPopup popup) {

		this.popup = popup;
	}

	@Override
	public void setTitle(IDisplayString title, IDisplayString subTitle) {

		popup.setCaption(title);
		popup.setSubCaption(subTitle);
	}

	@Override
	public void closeFrame() {

		popup.hide();
	}

	@Override
	public void focusFrame() {

		popup.focusFrameOrFirstInputElement();
	}
}
