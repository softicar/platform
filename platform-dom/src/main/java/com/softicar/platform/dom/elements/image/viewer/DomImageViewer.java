package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.style.ICssLength;
import java.util.List;
import java.util.Optional;

public class DomImageViewer extends DomDiv {

	private final List<IResource> images;
	private final ICssLength width;
	private final NavigationBar navigationBar;
	private final ImageDiv imageDiv;
	private final RotationDiv rotationDiv;
	private int currentIndex;

	public DomImageViewer(List<IResource> images, ICssLength width) {

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
		private final DomButton previousImageButton;

		public NavigationBar() {

			this.previousImageButton = new DomButton()//
				.setIcon(DomElementsImages.PAGE_PREVIOUS.getResource())
				.setClickCallback(() -> previousImage());
			this.nextImageButton = new DomButton()//
				.setIcon(DomElementsImages.PAGE_NEXT.getResource())
				.setClickCallback(() -> nextImage());
		}

		private void refresh() {

			removeChildren();

			appendChild(previousImageButton);
			appendText(IDisplayString.format("%s / %s", currentIndex + 1, images.size()));
			appendChild(nextImageButton);

			previousImageButton.setEnabled(currentIndex > 0);
			nextImageButton.setEnabled(currentIndex < images.size() - 1);
		}
	}

	private class ImageDiv extends DomDiv {

		private final DomImageViewerImage currentImage;

		public ImageDiv() {

			this.currentImage = null;
		}

		public void refresh() {

			if (!images.isEmpty()) {
				var limited = Optional//
					.ofNullable(currentImage)
					.map(DomImageViewerImage::isLimitWidth)
					.orElse(true);

				removeChildren();
				appendChild(new DomImageViewerImage(images.get(currentIndex), width).setLimitWidth(limited));
			}
		}

		public void setRotated(boolean rotated) {

			if (rotated) {
				currentImage.addCssClass(DomCssPseudoClasses.ROTATED);
			} else {
				currentImage.removeCssClass(DomCssPseudoClasses.ROTATED);
			}
		}
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

			rotated = !rotated;
			imageDiv.setRotated(rotated);
		}
	}
}
