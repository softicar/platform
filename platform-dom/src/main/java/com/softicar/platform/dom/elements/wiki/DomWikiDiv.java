package com.softicar.platform.dom.elements.wiki;

import com.softicar.platform.dom.elements.DomDiv;

public class DomWikiDiv extends DomDiv {

	public DomWikiDiv(String wikiText) {

		new DomWikiDivBuilder(this).addText(wikiText).build();
	}
}
