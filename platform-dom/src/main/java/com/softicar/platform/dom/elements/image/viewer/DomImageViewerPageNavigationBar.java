package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;

class DomImageViewerPageNavigationBar extends DomBar {

	private final DomImageViewer viewer;
	private final DomButton nextImageButton;
	private final DomButton previousImageButton;
	private final DomImageViewerPageIndicator pageIndicator;

	public DomImageViewerPageNavigationBar(DomImageViewer viewer) {

		this.viewer = viewer;
		this.previousImageButton = new DomButton()//
			.addMarker(DomTestMarker.IMAGE_VIEWER_PREVIOUS_PAGE_BUTTON)
			.setIcon(DomImages.PAGE_PREVIOUS.getResource())
			.setClickCallback(() -> viewer.showPreviousPage());
		this.nextImageButton = new DomButton()//
			.addMarker(DomTestMarker.IMAGE_VIEWER_NEXT_PAGE_BUTTON)
			.setIcon(DomImages.PAGE_NEXT.getResource())
			.setClickCallback(() -> viewer.showNextPage());
		this.pageIndicator = new DomImageViewerPageIndicator(viewer);

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_NAVIGATION_BAR);
		appendChild(previousImageButton);
		appendChild(pageIndicator);
		appendChild(nextImageButton);
	}

	void refresh() {

		previousImageButton.setEnabled(viewer.getPageIndex() > 0);
		pageIndicator.refresh();
		nextImageButton.setEnabled(viewer.getPageIndex() < viewer.getPageCount() - 1);
	}
}
