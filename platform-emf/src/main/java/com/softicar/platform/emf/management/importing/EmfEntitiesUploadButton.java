package com.softicar.platform.emf.management.importing;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;

public class EmfEntitiesUploadButton extends DomButton {

	public EmfEntitiesUploadButton() {

		setIcon(EmfImages.UPLOAD.getResource());
		setLabel(EmfI18n.UPLOAD);
	}
}
