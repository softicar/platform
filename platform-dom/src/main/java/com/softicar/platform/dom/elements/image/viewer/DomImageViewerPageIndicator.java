package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomSpan;

class DomImageViewerPageIndicator extends DomSpan {

	private final DomImageViewer viewer;

	public DomImageViewerPageIndicator(DomImageViewer viewer) {

		this.viewer = viewer;

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_PAGE_INDICATOR);
		addMarker(DomTestMarker.IMAGE_VIEWER_PAGE_INDICATOR);
	}

	public void refresh() {

		removeChildren();
		appendText(IDisplayString.format("%s / %s", viewer.getPageIndex() + 1, viewer.getPageCount()));
	}
}
