package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomWheelEventHandler;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssOverflowX;
import java.util.Set;

class DomImageViewerCanvas extends DomDiv implements IDomWheelEventHandler {

	private final DomImageViewer viewer;
	private final DomImageViewerImageHolder imageHolder;

	public DomImageViewerCanvas(DomImageViewer viewer) {

		this.viewer = viewer;
		this.imageHolder = new DomImageViewerImageHolder(viewer);

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_CANVAS);
		addMarker(DomTestMarker.IMAGE_VIEWER_CANVAS);
		appendChild(imageHolder);

		getDomEngine().setPreventDefaultOnWheel(this, Set.of(DomModifier.CONTROL), true);
	}

	@Override
	public void handleWheel(IDomEvent event) {

		if (event.isCtrlKey()) {
			if (event.getDeltaY() > 0) {
				viewer.zoomOut();
			} else {
				viewer.zoomIn();
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

		// We use relative positioning for the child element, and we center it
		// using the 50%-left-transform trick. To avoid an unnecessary horizontal
		// scroll bar, we need to disable horizontal scrolling below 100% zoom.
		if (viewer.getZoomLevel().getPercentage() < 100) {
			setStyle(CssOverflowX.HIDDEN);
		} else {
			unsetStyle(CssStyle.OVERFLOW_X);
		}

		imageHolder.applyTransformations();
	}
}
