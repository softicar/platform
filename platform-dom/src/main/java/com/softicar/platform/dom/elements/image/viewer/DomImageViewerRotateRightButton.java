package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;

class DomImageViewerRotateRightButton extends DomButton {

	public DomImageViewerRotateRightButton(DomImageViewer viewer) {

		addMarker(DomTestMarker.IMAGE_VIEWER_ROTATE_RIGHT_BUTTON);
		setIcon(DomImages.ROTATE_RIGHT.getResource());
		setTitle(DomI18n.ROTATE_CLOCKWISE);
		setClickCallback(() -> viewer.rotateRight());
	}
}
