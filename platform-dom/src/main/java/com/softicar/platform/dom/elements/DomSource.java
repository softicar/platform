package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;

public class DomSource extends DomElement {

	public DomSource() {

		// nothing to do
	}

	public DomSource(IResource resource) {

		IResourceUrl resourceUrl = getDomEngine().getResourceUrl(resource);
		setSrc(resourceUrl.toString());
		setType(resource.getMimeType());
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.SOURCE;
	}

	public void setMedia(String media) {

		setAttribute("media", media);
	}

	public void setSrc(String url) {

		setAttribute("src", url);
	}

	public void setType(IMimeType mimeType) {

		setAttribute("type", mimeType.getIdentifier());
	}
}
