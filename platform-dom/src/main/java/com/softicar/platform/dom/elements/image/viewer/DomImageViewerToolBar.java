package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.image.viewer.tag.DomImageViewerTagToggleButton;

class DomImageViewerToolBar extends DomBar {

	private final DomImageViewerPageNavigationBar navigationBar;
	private final DomImageViewerZoomBar zoomBar;

	public DomImageViewerToolBar(DomImageViewer viewer) {

		this.navigationBar = new DomImageViewerPageNavigationBar(viewer);
		this.zoomBar = new DomImageViewerZoomBar(viewer);

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_TOOL_BAR);
		appendChild(navigationBar);
		appendChild(new DomImageViewerToolBarSpacer());
		appendChild(zoomBar);
		appendChild(new DomImageViewerToolBarSpacer());
		appendChild(new DomImageViewerTagToggleButton(viewer));
		appendChild(new DomImageViewerRotateLeftButton(viewer));
		appendChild(new DomImageViewerRotateRightButton(viewer));
	}

	public void refresh() {

		zoomBar.refresh();
		navigationBar.refresh();
	}

	private static class DomImageViewerToolBarSpacer extends DomDiv {

		public DomImageViewerToolBarSpacer() {

			addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_TOOL_BAR_SPACER);
		}
	}
}
