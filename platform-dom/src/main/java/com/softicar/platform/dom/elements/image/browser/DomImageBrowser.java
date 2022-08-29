package com.softicar.platform.dom.elements.image.browser;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import java.util.List;

public class DomImageBrowser extends DomDiv implements IRefreshable {

	private final DomBar navigationBar;
	private final DomDiv imageDiv;
	private final DomDiv rotationDiv;
	private final DomButton nextImageButton;
	private final DomButton previousImageButton;
	private final int allPages;
	private final List<DomImageBrowserImage> previewImages;
	private int currentPage = 0;
	private DomImageBrowserImage currentImage;

	public DomImageBrowser(List<DomImageBrowserImage> previewImages) {

		this.previewImages = previewImages;
		this.allPages = previewImages.size();
		this.navigationBar = new DomBar();
		this.imageDiv = new DomDiv();
		this.rotationDiv = new RotationDiv();
		this.nextImageButton = new DomButton()//
			.setIcon(DomElementsImages.PAGE_NEXT.getResource())
			.setClickCallback(this::nextImage);
		this.previousImageButton = new DomButton()//
			.setIcon(DomElementsImages.PAGE_PREVIOUS.getResource())
			.setClickCallback(this::previousImage);
		addCssClass(DomCssClasses.DOM_IMAGE_BROWSER);
		refresh();
	}

	public DomImageBrowser setInPlaceZoom(ICssLength width) {

		// TODO extract style to CSS
		addCssClass(DomCssPseudoClasses.DRAGGABLE);
		imageDiv.setStyle(CssStyle.WIDTH, width);
		return this;
	}

	@Override
	public void refresh() {

		removeChildren();
		checkCurrentPageIndex();
		buildNavigationDiv();
		buildImageDiv();
		appendChild(navigationBar);
		appendChild(imageDiv);
		appendChild(rotationDiv);
	}

	private void checkCurrentPageIndex() {

		if (currentPage + 1 == allPages) {
			nextImageButton.setDisabled(true);
		} else {
			nextImageButton.setDisabled(false);
		}
		if (currentPage == 0) {
			previousImageButton.setDisabled(true);
		} else {
			previousImageButton.setDisabled(false);
		}
	}

	private void buildNavigationDiv() {

		navigationBar.removeChildren();
		navigationBar.appendChild(previousImageButton);
		navigationBar.appendText(IDisplayString.format("%s / %s", currentPage + 1, allPages));
		navigationBar.appendChild(nextImageButton);
	}

	private void buildImageDiv() {

		imageDiv.removeChildren();
		currentImage = previewImages.get(currentPage);
		imageDiv.appendChild(currentImage);
	}

	private void nextImage() {

		currentPage++;
		persistZoomStateOfImage();
		refresh();
	}

	private void previousImage() {

		currentPage--;
		persistZoomStateOfImage();
		refresh();
	}

	private void persistZoomStateOfImage() {

		DomImageBrowserImage nextImage = previewImages.get(currentPage);
		nextImage.setLimitWidth(currentImage.isLimitWidth());
		nextImage.refresh();
	}

	private class RotationDiv extends DomDiv {

		private boolean rotated = false;

		public RotationDiv() {

			appendChild(
				new DomButton()//
					.setIcon(DomImages.ROTATE.getResource())
					.setClickCallback(this::rotate));
		}

		private void rotate() {

			if (rotated) {
				currentImage.removeCssClass(DomCssPseudoClasses.ROTATED);
			} else {
				currentImage.addCssClass(DomCssPseudoClasses.ROTATED);
			}
			rotated = !rotated;
		}
	}
}
