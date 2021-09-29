package com.softicar.platform.ajax.engine.resource.link;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.dom.elements.DomLink.Relationship;

public class AjaxResourceLink {

	private final IResourceUrl resourceUrl;
	private final Relationship relationship;
	private final MimeType mimeType;

	public AjaxResourceLink(IResourceUrl resourceUrl, Relationship relationship, MimeType mimeType) {

		this.resourceUrl = resourceUrl;
		this.relationship = relationship;
		this.mimeType = mimeType;
	}

	public IResourceUrl getResourceUrl() {

		return resourceUrl;
	}

	public String toHtml() {

		return "<link href='%s' rel='%s' type='%s'>".formatted(resourceUrl, relationship, mimeType);
	}
}
