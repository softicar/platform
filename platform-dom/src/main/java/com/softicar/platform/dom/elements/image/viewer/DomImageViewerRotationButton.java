package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;

class DomImageViewerRotationButton extends DomButton {

	public DomImageViewerRotationButton(DomImageViewer viewer) {

		addMarker(DomTestMarker.IMAGE_VIEWER_ROTATE_BUTTON);
		setIcon(DomImages.ROTATE.getResource());
		setClickCallback(() -> viewer.rotate());
	}
}