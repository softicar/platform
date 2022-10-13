package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;

class DomImageViewerRotateLeftButton extends DomButton {

	public DomImageViewerRotateLeftButton(DomImageViewer viewer) {

		addMarker(DomTestMarker.IMAGE_VIEWER_ROTATE_LEFT_BUTTON);
		setIcon(DomImages.ROTATE_LEFT.getResource());
		setTitle(DomI18n.ROTATE_COUNTER_CLOCKWISE);
		setClickCallback(() -> viewer.rotateLeft());
	}
}
