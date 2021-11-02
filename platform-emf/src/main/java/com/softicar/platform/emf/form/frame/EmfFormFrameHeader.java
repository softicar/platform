package com.softicar.platform.emf.form.frame;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;

public class EmfFormFrameHeader extends DomDiv {

	public EmfFormFrameHeader() {

		setCssClass(EmfCssClasses.EMF_FORM_FRAME_HEADER);
	}

	public EmfFormFrameHeader setCaption(IDisplayString entityTitle, IDisplayString operationTitle) {

		removeChildren();
		appendText(entityTitle.concat(" - ").concat(operationTitle).toString());
		return this;
	}
}
