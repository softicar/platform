package com.softicar.platform.ajax.engine.resource.link;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.dom.document.DomHead;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.DomLink;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Keeps track of all {@link IResourceUrl} objects added as {@link DomLink} to
 * the {@link DomHead}.
 *
 * @author Oliver Richers
 */
public class AjaxResourceLinkRegistry {

	private final IAjaxDocument document;
	private final Map<IResourceUrl, AjaxResourceLink> resourceLinks;
	private boolean linkCreationEnabled;

	/**
	 * Constructs a new {@link AjaxResourceLinkRegistry} for the given
	 * {@link IDomDocument}.
	 *
	 * @param document
	 *            the {@link IDomDocument} (never <i>null</i>)
	 */
	public AjaxResourceLinkRegistry(IAjaxDocument document) {

		this.document = Objects.requireNonNull(document);
		this.resourceLinks = new TreeMap<>();
		this.linkCreationEnabled = true;
	}

	public AjaxResourceLinkRegistry setDomLinkCreationEnabled(boolean enabled) {

		this.linkCreationEnabled = enabled;
		return this;
	}

	public Collection<AjaxResourceLink> getResourceLinks() {

		return resourceLinks.values();
	}

	/**
	 * Registers the given {@link IResourceUrl} as {@link DomLink} to the
	 * {@link DomHead}.
	 * <p>
	 * If there is already a {@link DomLink} for the same {@link IResourceUrl}
	 * appended, this method does nothing.
	 * <p>
	 * If {@link #setDomLinkCreationEnabled(boolean)} was called with
	 * <i>false</i>, this method registers the {@link IResourceUrl} but does not
	 * create or append any {@link DomLink}.
	 */
	public void registerLink(IResourceUrl resourceUrl, Relationship relationship, MimeType mimeType) {

		if (!resourceLinks.containsKey(resourceUrl)) {
			if (linkCreationEnabled) {
				DomLink link = new DomLink()//
					.setHref(resourceUrl.toString())
					.setRel(relationship)
					.setType(mimeType);
				document.getHead().appendChild(link);
			}
			resourceLinks.put(resourceUrl, new AjaxResourceLink(resourceUrl, relationship, mimeType));
		}
	}
}
