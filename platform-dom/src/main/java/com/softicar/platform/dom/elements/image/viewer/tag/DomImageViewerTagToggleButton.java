package com.softicar.platform.dom.elements.image.viewer.tag;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;

public class DomImageViewerTagToggleButton extends DomButton {

	public DomImageViewerTagToggleButton(DomImageViewer viewer) {

		addMarker(DomTestMarker.IMAGE_VIEWER_TAGS_TOGGLE_BUTTON);
		setIcon(DomImages.TAG.getResource());
		setTitle(DomI18n.TOGGLE_TAGS);
		setClickCallback(() -> viewer.toggleTags());
	}
}
