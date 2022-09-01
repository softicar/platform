package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;

class DomImageViewerToolBar extends DomBar {

	private final DomImageViewerPageNavigationBar navigationBar;
	private final DomImageViewerZoomBar zoomBar;

	public DomImageViewerToolBar(DomImageViewer viewer) {

		this.navigationBar = new DomImageViewerPageNavigationBar(viewer);
		this.zoomBar = new DomImageViewerZoomBar(viewer);

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_TOOL_BAR);
		appendChild(navigationBar);
		appendChild(zoomBar);
		appendChild(new DomImageViewerRotationButton(viewer));
	}

	public void refresh() {

		zoomBar.refresh();
		navigationBar.refresh();
	}
}
