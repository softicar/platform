package com.softicar.platform.common.ui.wiki.element.inline;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiUrlToken;

public class WikiUrl extends AbstractWikiElement {

	private final String url;
	private final String label;

	public WikiUrl(WikiUrlToken urlToken) {

		this.url = urlToken.getUrl();
		this.label = urlToken.getLabel();
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return false;
	}

	public String getUrl() {

		return url;
	}

	public String getLabel() {

		return label;
	}
}
