package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.dom.document.DomHead;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;

/**
 * This class represents an HTML link element.
 * <p>
 * {@link DomLink} elements may only be appended to the {@link DomHead}.
 *
 * @author Oliver Richers
 */
public class DomLink extends DomElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.LINK;
	}

	public DomLink setCrossOrigin(String crossorigin) {

		setAttribute("crossorigin", crossorigin);
		return this;
	}

	public DomLink setHref(String url) {

		setAttribute("href", url);
		return this;
	}

	public DomLink setHrefLang(String languageCode) {

		setAttribute("hreflang", languageCode);
		return this;
	}

	public DomLink setMedia(String mediaQuery) {

		setAttribute("media", mediaQuery);
		return this;
	}

	public DomLink setRel(String relationship) {

		setAttribute("rel", relationship);
		return this;
	}

	public DomLink setRel(Relationship relationship) {

		return setRel(relationship.toString());
	}

	public DomLink setType(MimeType type) {

		return setType(type.toString());
	}

	public DomLink setType(String type) {

		setAttribute("type", type);
		return this;
	}

	public static enum Relationship {

		ALTERNATE("alternate"),
		AUTHOR("author"),
		DNS_PREFETCH("dns-prefetch"),
		HELP("help"),
		ICON("icon"),
		LICENSE("license"),
		NEXT("next"),
		PINGBACK("pingback"),
		PRECONNECT("preconnect"),
		PREFETCH("prefetch"),
		PRELOAD("preload"),
		PRERENDER("prerender"),
		PREV("prev"),
		SEARCH("search"),
		STYLESHEET("stylesheet");

		private final String identifier;

		private Relationship(String identifier) {

			this.identifier = identifier;
		}

		@Override
		public String toString() {

			return identifier;
		}
	}
}
