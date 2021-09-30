package com.softicar.platform.dom.elements.wiki;

import com.softicar.platform.common.ui.wiki.element.block.WikiHeadline;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.DomSimpleElement;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.parent.IDomParentElement;

public class DomWikiHeadlines {

	public static IDomParentElement createHeadline(WikiHeadline headline) {

		DomSimpleElement element = new DomSimpleElement(getHeadlineTag(headline));
		element.addCssClass(DomElementsCssClasses.DOM_WIKI_HEADLINE);
		return element;
	}

	private static DomElementTag getHeadlineTag(WikiHeadline headline) {

		switch (headline.getLevel()) {
		case 6:
			return DomElementTag.H1;
		case 5:
			return DomElementTag.H2;
		case 4:
			return DomElementTag.H3;
		case 3:
			return DomElementTag.H4;
		case 2:
			return DomElementTag.H5;
		default:
			return DomElementTag.H6;
		}
	}
}
