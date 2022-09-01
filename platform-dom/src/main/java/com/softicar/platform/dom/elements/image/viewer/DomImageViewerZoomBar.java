package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;

class DomImageViewerZoomBar extends DomBar {

	private final DomButton zoomOutButton;
	private final DomButton zoomInButton;
	private final DomImageViewerZoomSelect zoomSelect;

	public DomImageViewerZoomBar(DomImageViewer viewer) {

		this.zoomOutButton = new DomButton()//
			.addMarker(DomTestMarker.IMAGE_VIEWER_ZOOM_OUT_BUTTON)
			.setIcon(DomImages.ZOOM_OUT.getResource())
			.setClickCallback(() -> viewer.zoomOut());
		this.zoomInButton = new DomButton()//
			.addMarker(DomTestMarker.IMAGE_VIEWER_ZOOM_IN_BUTTON)
			.setIcon(DomImages.ZOOM_IN.getResource())
			.setClickCallback(() -> viewer.zoomIn());
		this.zoomSelect = new DomImageViewerZoomSelect(viewer);

		appendChild(zoomOutButton);
		appendChild(zoomInButton);
		appendChild(zoomSelect);
	}

	public void refresh() {

		zoomSelect.refresh();
	}
}
