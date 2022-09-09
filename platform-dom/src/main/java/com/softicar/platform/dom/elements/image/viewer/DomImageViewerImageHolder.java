package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.style.CssStyle;

class DomImageViewerImageHolder extends DomDiv {

	private final DomImageViewer viewer;

	public DomImageViewerImageHolder(DomImageViewer viewer, DomImageViewerCanvas canvas) {

		this.viewer = viewer;

		addMarker(DomTestMarker.IMAGE_VIEWER_IMAGE_HOLDER);
		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_IMAGE_HOLDER);

		getDomEngine().makeDraggable(this, this, canvas);
	}

	public void showImage(IResource resource) {

		removeChildren();

		var image = new DomImageViewerImage(resource);
		appendChild(image);

		if (viewer.isWidthDefined()) {
			getDomEngine().setHeightAndWidthOnLoad(image, viewer);
		}
	}

	public void applyTransformations() {

		var left = "0";
		var rotation = "0";
		var translationPercentage = 0;

		// center image when zoomed out
		if (viewer.getZoomLevel().getPercentage() < 100) {
			left = "50%";
			translationPercentage = -50;
		}

		// rotate by 180 degrees
		if (viewer.isRotated()) {
			rotation = "180deg";
			translationPercentage = -translationPercentage;
		}

		setStyle(CssStyle.LEFT, left);
		setStyle(CssStyle.TRANSFORM, "rotate(%s) translate(%s%%, 0)".formatted(rotation, translationPercentage));
		setStyle(CssStyle.WIDTH, new CssPercent(viewer.getZoomLevel().getPercentage()));
	}
}
