package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomImage;

class DomImageViewerImage extends DomImage {

	public DomImageViewerImage(IResource resource) {

		super(resource);

		addMarker(DomTestMarker.IMAGE_VIEWER_IMAGE);
		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_IMAGE);
	}
}
