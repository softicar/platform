package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * Represents an HTML script element.
 * <p>
 * As in HTML, you have two possibilities to define the source code of the
 * script. You can either append text nodes to this element, which is equivalent
 * to literal script blocks. Or you can supply an {@link IResource} to the
 * constructor of this element, which is then referenced by the <i>src</i>
 * attribute of the script element.
 *
 * @author Oliver Richers
 */
public class DomScript extends DomParentElement {

	private final IResource resource;

	public DomScript() {

		this.resource = null;
	}

	/**
	 * Creates a new element referencing the given resource.
	 * <p>
	 * Same as {@link #DomScript(IResource, boolean)}, with asynchronous loading
	 * disabled.
	 *
	 * @param resource
	 *            the resource to reference
	 */
	public DomScript(IResource resource) {

		this(resource, false);
	}

	/**
	 * Creates a new element referencing the given resource.
	 * <p>
	 * This method automatically registers the given resource in the
	 * {@link IDomEngine} and sets the <i>src</i> attribute of this element
	 * accordingly. It sets the type of this element to the mime-type of the
	 * resource and enables or disables asynchronous loading as requested.
	 *
	 * @param resource
	 *            the resource to reference
	 */
	public DomScript(IResource resource, boolean async) {

		this.resource = resource;

		// note: async must be set before setting the URL!
		IResourceUrl resourceUrl = getDomEngine().getResourceUrl(resource);
		setAsync(async);
		setType(resource.getMimeType());
		setSrc(resourceUrl.toString());
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.SCRIPT;
	}

	public IResource getResource() {

		return resource;
	}

	public DomScript setSrc(String src) {

		setAttribute("src", src);
		return this;
	}

	public DomScript setType(IMimeType mimeType) {

		setAttribute("type", mimeType.getIdentifier());
		return this;
	}

	public DomScript setAsync(boolean async) {

		setAttribute("async", async);
		return this;
	}

	public DomScript setDefer(boolean defer) {

		setAttribute("defer", defer);
		return this;
	}
}
