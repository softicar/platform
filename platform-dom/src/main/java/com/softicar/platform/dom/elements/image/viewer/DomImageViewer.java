package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import java.util.List;
import java.util.Optional;

public class DomImageViewer extends DomDiv {

	private final List<? extends IResource> images;
	private final ICssLength width;
	private final NavigationBar navigationBar;
	private final ImageDiv imageDiv;
	private final RotationDiv rotationDiv;
	private int currentIndex;

	public DomImageViewer(List<? extends IResource> images, ICssLength width) {

		this.images = images;
		this.width = width;
		this.navigationBar = new NavigationBar();
		this.imageDiv = new ImageDiv();
		this.rotationDiv = new RotationDiv();
		this.currentIndex = 0;

		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER);
		appendChild(navigationBar);
		appendChild(imageDiv);
		appendChild(rotationDiv);

		refresh();
	}

	private void nextImage() {

		currentIndex++;
		refresh();
	}

	private void previousImage() {

		currentIndex--;
		refresh();
	}

	private void refresh() {

		navigationBar.refresh();
		imageDiv.refresh();
	}

	private class NavigationBar extends DomBar {

		private final DomButton nextImageButton;
		private final PageIndicator pageIndicator;
		private final DomButton previousImageButton;

		public NavigationBar() {

			this.previousImageButton = new DomButton()//
				.addMarker(DomTestMarker.IMAGE_VIEWER_PREVIOUS_PAGE_BUTTON)
				.setIcon(DomImages.PAGE_PREVIOUS.getResource())
				.setClickCallback(() -> previousImage());
			this.nextImageButton = new DomButton()//
				.addMarker(DomTestMarker.IMAGE_VIEWER_NEXT_PAGE_BUTTON)
				.setIcon(DomImages.PAGE_NEXT.getResource())
				.setClickCallback(() -> nextImage());
			this.pageIndicator = new PageIndicator();

			appendChild(previousImageButton);
			appendChild(pageIndicator);
			appendChild(nextImageButton);
		}

		private void refresh() {

			previousImageButton.setEnabled(currentIndex > 0);
			pageIndicator.refresh();
			nextImageButton.setEnabled(currentIndex < images.size() - 1);
		}
	}

	private class PageIndicator extends DomSpan {

		public PageIndicator() {

			addMarker(DomTestMarker.IMAGE_VIEWER_PAGE_INDICATOR);
		}

		public void refresh() {

			removeChildren();
			appendText(IDisplayString.format("%s / %s", currentIndex + 1, images.size()));
		}
	}

	private class ImageDiv extends DomDiv {

		private DomImageViewerImage currentImage;

		public ImageDiv() {

			this.currentImage = null;

			addMarker(DomTestMarker.IMAGE_VIEWER_IMAGE_DIV);
		}

		public void refresh() {

			if (!images.isEmpty()) {
				var limited = Optional//
					.ofNullable(currentImage)
					.map(DomImageViewerImage::isLimitWidth)
					.orElse(true);
				this.currentImage = new DomImageViewerImage(images.get(currentIndex), width, limited);

				removeChildren();
				appendChild(currentImage);
			}
		}

		public void setRotated(boolean rotated) {

			if (rotated) {
				setStyle(CssStyle.TRANSFORM, "rotate(180deg)");
			} else {
				unsetStyle(CssStyle.TRANSFORM);
			}
		}
	}

	private class RotationDiv extends DomDiv {

		private boolean rotated = false;

		public RotationDiv() {

			appendChild(
				new DomButton()//
					.addMarker(DomTestMarker.IMAGE_VIEWER_ROTATE_BUTTON)
					.setIcon(DomImages.ROTATE.getResource())
					.setClickCallback(this::rotate));
		}

		private void rotate() {

			rotated = !rotated;
			imageDiv.setRotated(rotated);
		}
	}
}
