package com.softicar.platform.core.module.file.stored.preview;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.dom.styles.CssOverflow;
import com.softicar.platform.dom.styles.CssTextAlign;
import java.util.List;

public class LimitedWidthZoomableImageDiv extends DomDiv implements IRefreshable {

	private final DomBar navigationBar;
	private final DomDiv imageDiv;
	private final DomDiv rotationDiv;
	private final DomButton nextImageButton;
	private final DomButton previousImageButton;
	private final int allPages;
	private final List<LimitedWidthZoomableImage> previewImages;
	private int currentPage = 0;
	private LimitedWidthZoomableImage currentImage;

	public LimitedWidthZoomableImageDiv(List<LimitedWidthZoomableImage> previewImages) {

		this.previewImages = previewImages;
		this.allPages = previewImages.size();
		this.navigationBar = new DomBar();
		this.imageDiv = new DomDiv();
		this.rotationDiv = new RotationDiv();
		this.nextImageButton = new DomButton().setClickCallback(this::nextImage);
		this.previousImageButton = new DomButton().setClickCallback(this::previousImage);
		// TODO extract style to CSS
		setStyle(CssTextAlign.CENTER);
		refresh();
	}

	public LimitedWidthZoomableImageDiv setInPlaceZoom(ICssLength width) {

		// TODO extract style to CSS
		imageDiv.setStyle(CssStyle.WIDTH, width);
		imageDiv.setStyle(CssStyle.HEIGHT, "85vh");
		imageDiv.setStyle(CssOverflow.SCROLL);
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
			nextImageButton.setEnabled(false);
			nextImageButton.setIcon(DomElementsImages.PAGE_NEXT_DISABLED.getResource());
		} else {
			nextImageButton.setEnabled(true);
			nextImageButton.setIcon(DomElementsImages.PAGE_NEXT.getResource());
		}
		if (currentPage == 0) {
			previousImageButton.setEnabled(false);
			previousImageButton.setIcon(DomElementsImages.PAGE_PREVIOUS_DISABLED.getResource());
		} else {
			previousImageButton.setEnabled(true);
			previousImageButton.setIcon(DomElementsImages.PAGE_PREVIOUS.getResource());
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

		LimitedWidthZoomableImage nextImage = previewImages.get(currentPage);
		nextImage.setLimitWidth(currentImage.isLimitWidth());
		nextImage.refresh();
	}

	private class RotationDiv extends DomDiv {

		private int currentRotation = 0;

		public RotationDiv() {

			appendChild(
				new DomButton()//
					.setIcon(CoreImages.ROTATE.getResource())
					.setClickCallback(this::rotate));
		}

		private void rotate() {

			currentRotation += 180;
			// TODO extract style to CSS
			currentImage.setStyle(CssStyle.TRANSFORM, "rotate(" + currentRotation + "deg)");
		}
	}
}
