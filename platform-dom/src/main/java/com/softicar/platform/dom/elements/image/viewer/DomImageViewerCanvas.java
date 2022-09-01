package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomWheelEventHandler;

class DomImageViewerCanvas extends DomDiv implements IDomWheelEventHandler {

	private final DomImageViewer viewer;
	private final DomImageViewerImageHolder imageHolder;

	public DomImageViewerCanvas(DomImageViewer viewer) {

		this.viewer = viewer;
		this.imageHolder = new DomImageViewerImageHolder(viewer);

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_CANVAS);
		addMarker(DomTestMarker.IMAGE_VIEWER_CANVAS);
		appendChild(imageHolder);
	}

	@Override
	public void handleWheel(IDomEvent event) {

		if (event.isAltKey()) {
			if (event.getDeltaY() > 0) {
				viewer.zoomIn();
			} else {
				viewer.zoomOut();
			}
		}
	}

	public void refresh() {

		if (viewer.getPageCount() > 0) {
			imageHolder.showImage(viewer.getPageImage());
			imageHolder.applyTransformations();
		}
	}

	public void applyTransformations() {

		imageHolder.applyTransformations();
	}
}
